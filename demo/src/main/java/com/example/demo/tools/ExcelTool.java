package com.example.demo.tools;

import com.example.demo.app.CellProps;
import com.example.demo.app.RowProps;
import com.example.demo.entity.ExcelData;
import com.example.demo.interfaces.CellTypeConvert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xhliu
 */
@Component
public class ExcelTool  {

    private final static Map<Class<?>, List<Field>> cache = new ConcurrentHashMap<>();

    public static List<Field> getAllFields(Class<?> clazz) {
        return getAllFields(clazz, new ArrayList<>());
    }

    private static List<Field> getAllFields(Class<?> clazz, List<Field> list) {
        if (clazz == null || clazz == Object.class) return list;
        if (cache.containsKey(clazz)) {
            return cache.get(clazz);
        }
        Field[] fields = clazz.getDeclaredFields();
        list.addAll(Arrays.asList(fields));
        List<Field> allFields = getAllFields(clazz.getSuperclass(), list);
        cache.put(clazz, allFields);
        return allFields;
    }

    @Resource
    private ApplicationContext context;

    public List<?> read(Class<?> clazz, InputStream in) {
        try (Workbook workbook = new XSSFWorkbook(in)) {
            Iterator<Sheet> iterator = workbook.sheetIterator();
            List<Field> fields = getAllFields(clazz);
            List<Object> list = new ArrayList<>();
            while (iterator.hasNext()) {
                Sheet sheet = iterator.next();
                for (Row row : sheet) {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    Object obj = constructor.newInstance();
                    for (int i = 0; i < fields.size(); i++) {
                        Cell cell = row.getCell(i);
                        Field field = fields.get(i);
                        fillValue(field, cell, obj);
                    }
                    list.add(obj);
                }
            }
            for (Object o : list) {
                System.out.println(o);
            }
            return list;
        } catch (IOException | NoSuchMethodException | InstantiationException
                 | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillValue(Field field, Cell cell, Object target)
            throws IllegalAccessException {
        field.setAccessible(true);
        CellProps props = field.getAnnotation(CellProps.class);
        if (props == null) return;
        if (!props.convert().isEmpty()) {
            @SuppressWarnings("unchecked")
            CellTypeConvert<Cell, ?> convert = (CellTypeConvert<Cell, ?>) context.getBean(props.convert());
            if (convert.canConvert(cell.getCellType(), field.getType())) {
                field.set(target, convert.convert(cell));
            }
            return;
        }
        CellTypeConvert<Cell, ?> convert = context.getBean(props.convertClass());
        if (convert.canConvert(cell.getCellType(), field.getType())) {
            field.set(target, convert.convert(cell));
        }
    }

    public ByteArrayInputStream convertToByteArrayStream(ExcelData<?> excelData) {
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            List<?> data = excelData.data();
            if (data == null || data.isEmpty()) {
                return new ByteArrayInputStream(out.toByteArray());
            }
            Class<?> clazz = data.get(0).getClass();
            String sheetName = clazz.getSimpleName();
            Sheet sheet = workbook.createSheet(sheetName);
            List<Field> fields = getAllFields(clazz);
            fields.sort(ExcelTool::cellPropsCompare);
            // 生成头部信息（列名，头部单元格样式等）
            makeHeader(sheet, fields);

            for (int i = 0; i < data.size(); ++i) {
                Row row = sheet.createRow(i);
                fillRowStyle(clazz, row);
                Object t = data.get(i + 1);
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);
                    field.setAccessible(true);
                    Object value = field.get(t);
                    Cell cell = row.createCell(j, CellType.STRING);
                    cell.setCellValue(value.toString());
                    fillDataCellStyle(field, cell);
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillRowStyle(Class<?> clazz, Row row) {
        if (clazz == null || row == null) return;
        RowProps rowProps = clazz.getDeclaredAnnotation(RowProps.class);
        if (rowProps == null) return;
        if (!rowProps.rowStyle().isEmpty()) {
            row.setRowStyle((CellStyle) context.getBean(rowProps.rowStyle()));
            return;
        }
        row.setRowStyle(context.getBean(rowProps.rowStyleClass()));
    }

    /**
     * 填充数据单元格样式
     */
    private void fillDataCellStyle(Field field, Cell cell) {
        CellProps props = field.getDeclaredAnnotation(CellProps.class);
        if (props == null) return;
        if (!props.cellStyle().isEmpty()) {
            cell.setCellStyle((CellStyle) context.getBean(props.cellStyle()));
            return;
        }
        cell.setCellStyle(context.getBean(props.cellStyleClass()));
    }

    /**
     * 填充首行（列名）样式
     */
    private void fillHeaderCellStyle(Field field, Cell cell) {
        CellProps props = field.getDeclaredAnnotation(CellProps.class);
        if (props == null) return;
        if (!props.headerCellStyle().isEmpty()) {
            cell.setCellStyle((CellStyle) context.getBean(props.headerCellStyle()));
            return;
        }
        cell.setCellStyle(context.getBean(props.headerCellStyleClass()));
    }

    private static int cellPropsCompare(Field a, Field b) {
        CellProps cp1 = a.getDeclaredAnnotation(CellProps.class);
        CellProps cp2 = b.getDeclaredAnnotation(CellProps.class);
        if (cp1 == null && cp2 == null) return 0;
        if (cp1 == null || cp2 == null) {
            return cp1 == null ? 1 : -1;
        }
        return cp1.order() - cp2.order();
    }

    private void makeHeader(Sheet sheet, List<Field> fields) {
        Row header = sheet.createRow(0);
        for (int i = 0; i < fields.size(); i++) {
            Cell cell = header.createCell(i);
            Field field = fields.get(i);
            CellProps props = field.getDeclaredAnnotation(CellProps.class);
            cell.setCellValue(field.getName());
            fillHeaderCellStyle(field, cell);
        }
    }
}
