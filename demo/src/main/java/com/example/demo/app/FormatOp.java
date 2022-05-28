package com.example.demo.app;

import java.lang.annotation.*;

/**
 * @author xhliu
 * @create 2022-05-27-15:39
 **/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FormatOp {
}
