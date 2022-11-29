package com.example.demo.app;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.lang.annotation.*;

/**
 * 和 Excel 行相关的属性标识
 *
 * @author lxh
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RowProps {
    /**
     * 当前类对应的单元行的风格样式类型
     */
    Class<? extends CellStyle> rowStyleClass() default XSSFCellStyle.class;

    /**
     * {@link #rowStyleClass()} 在 IOC 容器中的唯一访问 key
     */
    String rowStyle() default "";
}
