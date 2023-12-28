package org.xhliu.springtransaction.annotation;

import org.xhliu.springtransaction.datasource.DataSourceType;

import java.lang.annotation.*;

/**
 * 用于定义处理上下文的所需要持有的数据源类型
 *
 * @see org.xhliu.springtransaction.aspect.DataSourceAspect
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DataSource {

    DataSourceType value() default DataSourceType.MYSQL;
}
