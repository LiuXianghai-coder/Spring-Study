package com.example.demo.reflect;

import com.example.demo.entity.TaskInfo;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.reflection.invoker.MethodInvoker;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author xhliu
 */
public class TaskInfoReflector
        extends Reflector {

    private final static Map<String, Method> map = new TreeMap<>();

    static {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(TaskInfo.class);
        for (Method m : methods) {
            map.put(m.getName(), m);
        }
    }

    public TaskInfoReflector(Class<?> clazz) {
        super(clazz);
    }

    public Invoker getGetInvoker(String propName) {
        for (Map.Entry<String, Method> entry : map.entrySet()) {
            String name = entry.getKey();
            Method method = entry.getValue();
            int ps = method.getParameterCount();
            if (ps > 0) continue;
            if (propName.equals(name) && (String.class == method.getReturnType()
                    || method.getReturnType().isPrimitive())) {
                return new MethodInvoker(method);
            }
        }
        return super.getGetInvoker(propName);
    }
}
