package com.example.demo.entity;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * @author xhliu
 **/
public class ReflectionExample {
    public void query(String prodId) {
        System.out.println("query(prodId) invoked");
    }

    public void query(String prodId, String projId) {
        System.out.println("query(prodId, projId) invoked");
    }

    public void query(String prodId, Integer val) {
        System.out.println("query(prodId, val) invoked");
    }

    public void query(String prodId, String projId, String periodId) {
        System.out.println("query(prodId, projId, periodId) invoked");
    }

    public void query(String... args) {}

    public static void main(String[] args) throws NoSuchMethodException {
        ReflectionExample obj = new ReflectionExample();
        Class<?> clazz = ReflectionExample.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if ((method.getModifiers() & Modifier.STATIC) != 0) continue;
            Parameter[] ps = method.getParameters();
            System.out.print(method.getName() + " (");
            int n = ps.length;
            for (int i = 0; i < n; i++) {
                System.out.print(ps[i].getType().getSimpleName() + " " + ps[i].getName());
                if (i == n - 1) continue;
                System.out.print(", ");
            }
            System.out.println(")");
        }
        Class<?>[] param = {String.class, Integer.class};
        clazz.getDeclaredMethod("query", param);
    }
}
