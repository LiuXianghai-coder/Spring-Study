package com.example.demo.interfaces;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;

/**
 * 鉴于数据的读取，这里的前提条件是客户端没有定义相关的属性类型转换类，这里定义的默认
 * 转换类将会将所有的单元格内容转换为字符串类型，这是一中比较折中的方案
 * @author lxh
 */
@Service(value = "defaultCellTypeConvert")
public class DefaultCellTypeConvert
        implements CellTypeConvert<Cell, Object> {

    @Override
    public boolean canConvert(CellType cellType, Class<?> clazz) {
        return true;
    }

    @Override
    public Object convert(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return  "";
            default:
                return cell.getRichStringCellValue().getString();
        }
    }
}
