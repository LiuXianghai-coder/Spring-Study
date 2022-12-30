package com.example.demo.config;

import org.apache.ibatis.reflection.ReflectorFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;

/**
 * @author xhliu
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationPropertiesBinding
public class StringToReflectFactoryConvert
        implements Converter<String, ReflectorFactory> {

    public ReflectorFactory convert(@Nonnull String source) {
        try {
            Class<?> clazz = Class.forName(source);
            if (!ReflectorFactory.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("非法的反射工厂类型: " + clazz.getName());
            }
            Constructor<?> constructor = clazz.getConstructor();
            return (ReflectorFactory) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
