package com.example.demo.component;

import com.example.demo.plugin.BackupInfoPlugin;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author lxh
 */
@Component
@ConfigurationPropertiesBinding
public class StringToPluginConvert
        implements Converter<String, BackupInfoPlugin> {

    @Resource
    private ApplicationContext context;

    @Override
    public BackupInfoPlugin convert(String source) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Class<?> clazz = loader.loadClass(source);
            Constructor<?> constructor = clazz.getConstructor();
            return (BackupInfoPlugin) constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
                 | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
