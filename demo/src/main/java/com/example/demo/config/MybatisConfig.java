package com.example.demo.config;

import com.example.demo.plugin.BackUpInfoReadPlugin;
import com.example.demo.plugin.BackupInfoPlugin;
import com.example.demo.reflect.TaskInfoReflectorFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxh
 */
@Configuration
public class MybatisConfig {

    @Bean(name = "backupInfoPlugin")
    public Interceptor backupInfoPlugin() {
        return new BackupInfoPlugin();
    }

    @Bean(name = "backUpInfoReadPlugin")
    public Interceptor backUpInfoReadPlugin() {
        return new BackUpInfoReadPlugin();
    }

    @Bean(name = "taskInfoReflectorFactory")
    public TaskInfoReflectorFactory taskInfoReflectorFactory() {
        return new TaskInfoReflectorFactory();
    }
}
