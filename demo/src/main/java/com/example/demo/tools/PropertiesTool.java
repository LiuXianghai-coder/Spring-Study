package com.example.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;

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
                int modifiers = tf.getModifiers();

                // 过滤静态字段
                if ((modifiers & Modifier.STATIC) != 0) continue;

                try {
                    tf.setAccessible(true);
                    Object val = tf.get(src);

                    Class<?> fc = tf.getType();
                    if (!isConstType(fc)) {
                        if (fc.isArray()) {
                            tf.set(target, cloneArray(val));
                            continue;
                        }

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

    private static boolean isConstType(Class<?> clazz) {
        if (clazz.isPrimitive()) return true;

        // LocalDate 系列不可变对象的处理
        if (ChronoLocalDateTime.class.isAssignableFrom(clazz)) return true;
        if (ChronoLocalDate.class.isAssignableFrom(clazz)) return true;

        // 数值型的类型是不可变的，可以在多个对象之间安全地进行共享
        if (Number.class.isAssignableFrom(clazz)) return true;

        // String 在这里被视为基本数据类型，因为它是不可变的，可以安全地在多个对象之间共享
        return clazz == String.class;
    }

    private enum Clazz {
        BYTE(byte.class),
        BOOLEAN(boolean.class),
        SHORT(short.class),
        CHAR(char.class),
        FLOAT(float.class),
        INT(int.class),
        LONG(long.class),
        DOUBLE(double.class);

        public final Class<?> clazz;

        Clazz(Class<?> clazz) {
            this.clazz = clazz;
        }
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

        Class<?> compClazz = obj.getClass().getComponentType();
        if (compClazz.isPrimitive()) {
            Clazz type = Clazz.valueOf(Clazz.class, compClazz.getSimpleName().toUpperCase());

            Object res = null;
            switch (type) {
                case BYTE: res = ((byte[]) obj).clone(); break;
                case BOOLEAN: res = ((boolean[]) obj).clone(); break;
                case SHORT: res = ((short[]) obj).clone(); break;
                case CHAR: res = ((char[]) obj).clone(); break;
                case FLOAT: res = ((float[]) obj).clone(); break;
                case INT: res = ((int[]) obj).clone(); break;
                case LONG: res = ((long[]) obj).clone(); break;
                case DOUBLE: res = ((double[]) obj).clone(); break;
            }

            return res;
        }

        int len = Array.getLength(obj);
        if (len <= 0) return null;

        Class<?> clazz = Array.get(obj, 0).getClass();
        Object ans = Array.newInstance(clazz, len);
        for (int i = 0; i < len; i++) {
            Object val = Array.get(obj, i); // 当前属性中，数组中指定位置的对象

            // 基本类型直接复制即可
            if (isConstType(clazz)) {
                Array.set(ans, i, val);
                continue;
            }

            // 多维数组的处理
            if (clazz.isArray()) {
                Array.set(ans, i, cloneArray(val));
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
