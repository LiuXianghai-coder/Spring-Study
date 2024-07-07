package com.example.demo.jasypt;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyFilter;
import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

// 设置过滤器 Bean 名称，使得能够被加载到 Jasypt 的配置中
@Component("jasyptPropertiesFilter")
public class PropertiesFilter
        implements EncryptablePropertyFilter {

    private final Set<String> excludeSet = new HashSet<>();

    @Autowired
    public PropertiesFilter(ConfigurableEnvironment env) {
        // 复用 Jasypt 的过滤器配置属性
        JasyptEncryptorConfigurationProperties props = JasyptEncryptorConfigurationProperties.bindConfigProps(env);
        JasyptEncryptorConfigurationProperties.PropertyConfigurationProperties.FilterConfigurationProperties filterProps = props.getProperty().getFilter();
        excludeSet.addAll(filterProps.getExcludeNames());
    }

    @Override
    public boolean shouldInclude(PropertySource<?> source, String name) {
        // 如果是开发环境，则不需要对字段进行加密处理
        if (source.getName().endsWith("dev.yml")) {
            return true;
        }
        // 如果前缀与配置的匹配，则不进行加密处理
        for (String excludeName : excludeSet) {
            if (excludeName.startsWith(name)) {
                return false;
            }
        }
        return true;
    }
}
