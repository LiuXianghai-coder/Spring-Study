package org.xhliu.springflowable.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author xhliu
 * @time 2022-02-23-下午10:01
 */
public class Test {
    public static void main(String[] args) throws Throwable {
        Class<?> child = Class.forName("org.xhliu.springflowable.reflect.Child");
        Constructor<?> constructor = child.getDeclaredConstructor();
        Object instance = constructor.newInstance();

        Field[] fields = child.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(instance, 2);
        }

        Method method = child.getMethod("sayHello");
        method.invoke(instance);

        System.out.println(instance);

        byte a = 127, b = 127;
        b += a;
        System.out.println(b);
        System.out.println((3*0.1) == 0.3);
    }
}
