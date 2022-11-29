package com.example.demo.app;

import com.example.demo.interfaces.CellTypeConvert;
import com.example.demo.interfaces.DefaultCellTypeConvert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.lang.annotation.*;

/**
 * 用于标记类中单元格相关属性的注解
 *
 * @author lxh
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CellProps {
    /**
     * 从 Cell 中读取数据，并将其转换为对应属性类型对象的转换类
     * @see CellTypeConvert
     */
    Class<? extends CellTypeConvert<Cell, ?>> convertClass() default DefaultCellTypeConvert.class;

    /**
     * {@link #convertClass()} 在 IOC 容器中的唯一访问 key
     */
    String convert() default "";

    /**
     * excel 首行（列名）单元格的样式类型
     * @return  对应生成的 Excel 首行（列名）的样式类型
     */
    Class<? extends CellStyle> headerCellStyleClass() default XSSFCellStyle.class;

    /**
     * 当使用 IOC 容器时，可以通过指定的名称来获取对应的风格对象，具体名称规范由具体 IOC 容器决定 <br />
     * 当这个属性指定为默认值时，将会从 {@link #headerCellStyleClass()} 中通过类型的方式从 IOC 容器中获取对应的风格样式对象
     * @return  在 IOC 容器中首行 excel 单元格样式的风格对象名
     */
    String headerCellStyle() default "";

    /**
     * 除了首行单元格外，其余实际数据载体的单元格的风格样式类型
     * @see #headerCellStyleClass()
     * @return  数据单元格的样式类型
     */
    Class<? extends CellStyle> cellStyleClass() default XSSFCellStyle.class;

    /**
     * 数据载体的单元格样式在 IOC 容器中的获取方式
     * @see #headerCellStyle()
     * @return  在 IOC 中标识对应样式对象的 key
     */
    String cellStyle() default "";

    /**
     * 对于单元格来将，需要将对应的列能够按照指定的顺序进行排列，这个方法的作用就是表示每个属性的顺序值，
     * 最终单元格将会按照这里定义的大小值从小到大进行排序
     * @return  单元列的顺序值
     */
    int order() default 0;
}
