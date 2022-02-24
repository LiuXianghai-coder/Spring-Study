package org.xhliu.springflowable.reflect;

/**
 * @author xhliu
 * @time 2022-02-23-下午9:59
 */
public class Parent {
    static {
        System.out.println("parent static code block");
    }

    {
        System.out.println("parent simple code block");
    }
}
