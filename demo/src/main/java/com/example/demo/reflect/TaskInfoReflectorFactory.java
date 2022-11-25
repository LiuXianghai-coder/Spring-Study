package com.example.demo.reflect;

import com.example.demo.entity.TaskInfo;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.Reflector;

/**
 * @author xhliu
 */
public class TaskInfoReflectorFactory
        extends DefaultReflectorFactory {

    public Reflector findForClass(Class<?> type) {
        if (!(TaskInfo.class.isAssignableFrom(type))) {
            return super.findForClass(type);
        }
        return new TaskInfoReflector(type);
    }
}
