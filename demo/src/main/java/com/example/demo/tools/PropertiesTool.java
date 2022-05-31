package com.example.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.type.PrimitiveType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PropertiesTool {
    private final static Logger log = LoggerFactory.getLogger(PropertiesTool.class);

    public static void copyProperties(Object src, Object target) {
        copyProperties(src, src.getClass(), target, target.getClass());
    }

    private static void copyProperties(
            Object src, Class<?> srcClass,
            Object target, Class<?> tarClass
    ) {
        if (src == null || target == null) return;
        if (srcClass == null || tarClass == null) return;

        Field[] sfs = srcClass.getDeclaredFields();
        Field[] tfs = tarClass.getDeclaredFields();
        for (Field sf : sfs) {
            for (Field tf : tfs) {
                if (!sf.equals(tf)) continue;
                try {
                    tf.setAccessible(true);
                    Object val = tf.get(src);

                    Class<?> fc = tf.getType();
                    if (!isBasicType(fc)) {
                        if (fc.isArray()) tf.set(target, cloneArray(val));

                        // 对于对象类型的深度属性复制，需要相关的对象具有对应的无参构造函数
                        Object obj = fc.getDeclaredConstructor().newInstance();
                        copyProperties(val, obj);
                        tf.set(target, obj);
                        continue;
                    }

                    tf.set(target, val); // 基本类型的特殊处理
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        copyProperties(src, srcClass.getSuperclass(), target, tarClass.getSuperclass());
    }

    private static boolean isBasicType(Class<?> clazz) {
        if (clazz.isPrimitive()) return true;
        if (ChronoLocalDateTime.class.isAssignableFrom(clazz)) return true;

        // 数值型的类型是不可变的，可以在多个对象之间安全地进行共享
        if (Number.class.isAssignableFrom(clazz)) return true;

        // String 在这里被视为基本数据类型，因为它是不可变的，可以安全地在多个对象之间共享
        return clazz == String.class;
    }

    /**
     * 实现数组对象的深度拷贝，数组中元素类型，应当至少存在一个无参构造函数，
     * 即使这个构造函数是私有的同样可以满足要求
     *
     * @param obj  待拷贝的数组对象
     * @return  拷贝之后的数组对象
     */
    private static Object cloneArray(Object obj) {
        if (!obj.getClass().isArray()) {
            throw new IllegalArgumentException("不能将非数组类型的对象转换为对应的 List");
        }

        List<Object> list = new ArrayList<>();

        int len = Array.getLength(obj);
        if (len <= 0) return list.toArray();

        Class<?> clazz = Array.get(obj, 0).getClass();
        Object ans = Array.newInstance(clazz, len);

        for (int i = 0; i < len; i++) {
            list.add(Array.get(obj, i));

            Object val = Array.get(obj, i); // 当前属性中，数组中指定位置的对象

            // 基本类型直接复制即可
            if (isBasicType(clazz)) {
                Array.set(ans, i, val);
                continue;
            }

            try {
                /*
                 * 针对数组中元素类型为一般对象，那么首先通过反射的方式实例化该位置的数组元素，
                 * 然后将该位置的原对象属性复制到这个对象中，从而实现数组对象的深度拷贝
                 */
                Object o = clazz.getDeclaredConstructor().newInstance();
                copyProperties(val, o);
                Array.set(ans, i, o);
            } catch (Throwable t) {
                log.info("类型 {} 不存在相关的无参构造函数，无法完成数组对象的深度拷贝", clazz);
                t.printStackTrace();
            }
        }

        return ans;
    }
}
