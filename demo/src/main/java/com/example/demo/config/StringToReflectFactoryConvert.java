package com.example.demo.config;

import com.example.demo.reflect.TaskInfoReflectorFactory;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

/**
 * @author xhliu
 */
@Configuration
@ConfigurationPropertiesBinding
public class StringToReflectFactoryConvert
        implements Converter<String, ReflectorFactory> {

    @Resource
    private ApplicationContext context;

    public ReflectorFactory convert(@Nonnull String beanName) {
        return new TaskInfoReflectorFactory();
    }
}
