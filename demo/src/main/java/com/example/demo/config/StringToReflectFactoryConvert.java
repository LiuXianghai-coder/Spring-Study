package com.example.demo.config;

import org.apache.ibatis.reflection.ReflectorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * @author xhliu
 */
@Configuration
public class StringToReflectFactoryConvert
        implements Converter<String, ReflectorFactory> {

    @Resource
    private ApplicationContext context;

    public ReflectorFactory convert(@Nonnull String beanName) {
        return (ReflectorFactory) context.getBean(beanName);
    }
}
