package com.example.demo.config;

import com.example.demo.plugin.BackupInfoPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxh
 */
@Configuration
public class PluginConfig {

    @Bean
    public BackupInfoPlugin backupInfoPlugin() {
        return new BackupInfoPlugin();
    }
}
