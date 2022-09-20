package com.example.demo.entity;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author lxh
 */
@SpringBootTest
public class EntityTest {
    @Test
    public void syntheticFieldTest() {
        Field[] fields = SyntheticFieldDemo.NestedClass
                .class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("Field Name: " + field.getName() + "\tisSynthetic: " + field.isSynthetic());
        }
    }

    @Test
    public void syntheticMethodTest() {
        Method[] methods = SyntheticMethodDemo.NestedClass.class
                .getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method + ", isSynthetic: " + method.isSynthetic());
        }
    }

    @Test
    public void bridgeMethodTest() {
        Method[] methods = BridgeMethodDemo.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName() +
                    ", isSynthetic: " + method.isSynthetic() +
                    ", isBridge: " + method.isBridge());
        }
    }

    @Test
    public void syntheticConstructorTest() {
        Constructor<?>[] constructors = SyntheticConstructorDemo.NestedClass
                .class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("cons: " + constructor.getName() + ", isSynthetic: " + constructor.isSynthetic());
        }

    }
}
