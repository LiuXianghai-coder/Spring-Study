package org.xhliu.springtransaction.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *@author lxh
 */
public abstract class ReflectTool {

    /**
     * 通过调用一个对象的无参构造函数，创建一个对应的实例对象
     *
     * @param clazz 待构造的对象类型
     * @return 经过调用无参构造函数后得到的实例对象
     * @param <T> 相关的参数类型
     * @throws RuntimeException 当构造出现异常时抛出
     */
    public static <T> T createInstance(Class<T> clazz) {
        try {
            Constructor<T> unArgConstructor = clazz.getDeclaredConstructor();
            return unArgConstructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException
                 | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
