package com.example.demo.app;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author xhliu
 * @create 2022-05-27-15:54
 **/
@Aspect
public class LogAspect {
    @Before("com.example.demo.app.FormatAspect.point()")
    public void log() {
//        System.out.println("Hello World");
    }
}
