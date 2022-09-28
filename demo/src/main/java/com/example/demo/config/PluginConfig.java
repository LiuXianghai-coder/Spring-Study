package com.example.demo.config;

import com.example.demo.plugin.BackUpInfoReadPlugin;
import com.example.demo.plugin.BackupInfoPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxh
 */
@Configuration
public class PluginConfig {

    @Bean(name = "backupInfoPlugin")
    public Interceptor backupInfoPlugin() {
        return new BackupInfoPlugin();
    }

    @Bean(name = "backUpInfoReadPlugin")
    public Interceptor backUpInfoReadPlugin() {
        return new BackUpInfoReadPlugin();
    }
}
