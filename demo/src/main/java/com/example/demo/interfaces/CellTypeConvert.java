package com.example.demo.interfaces;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 这个接口表是从指定的单元格中读取数据，然后转换为指定对象属性的类型
 *
 * @param <S> 单元格对象类型
 * @param <T> 实际转换的目标属性对象类型
 * @author lxh
 */
public interface CellTypeConvert<S extends Cell, T> {
    /**
     * 具体的实现类是否支持将单元格中的数据类型转换成目标类型
     * @param cellType  单元格中的数据类型
     * @param clazz 属性字段的类型
     * @return  如果能够转换，返回 {@code true}, 否则返回 {@code false}
     */
    boolean canConvert(CellType cellType, Class<?> clazz);

    /**
     * 如果能够执行对应的类型转换，即 {@link #canConvert(CellType, Class)} 返回 {@code true} 的
     * 前提条件下，这个方法的子类实现应当将单元格中的数据转换为实际的目标类型对象
     * @param cell  单元格数据对象
     * @return  经过转换后得到的目标对象
     */
    T convert(S cell);
}
