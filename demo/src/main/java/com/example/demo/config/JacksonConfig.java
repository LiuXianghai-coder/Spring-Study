package com.example.demo.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lxh
 * @date 2022/6/18-下午8:36
 */
@Configuration
public class JacksonConfig {
    @Bean
    public Module timeModule() {
        return new JavaTimeModule();
    }
}
