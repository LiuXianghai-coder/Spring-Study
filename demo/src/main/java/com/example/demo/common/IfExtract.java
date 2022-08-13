package com.example.demo.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lxh
 * @date 2022/8/9-下午10:48
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface IfExtract {
    String name() default "ABC";
}
