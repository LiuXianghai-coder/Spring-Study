package com.example.demo.common;

import java.lang.annotation.*;

/**
 * @author xhliu
 **/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ReadAuth {
    String name() default "";

    String target() default "";
}
