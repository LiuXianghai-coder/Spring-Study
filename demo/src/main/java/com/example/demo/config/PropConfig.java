package com.example.demo.config;

import com.example.demo.plugin.BackupInfoPlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxh
 */
@Configuration
@ConfigurationProperties(prefix = "user")
public class PropConfig {
    private String name;
    private int age;
    private BackupInfoPlugin plugin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BackupInfoPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(BackupInfoPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String toString() {
        return "PropConfig{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", plugin=" + plugin +
                '}';
    }
}
