package com.example.integration;

import com.example.integration.processor.StreamProcessor;
import com.example.integration.service.BizService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableBinding(StreamProcessor.class)
public class SpringIntegrationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntegrationApplication.class, args);
        BizService bizService = context.getBean(BizService.class);
        // send batch msg
        for (int i = 0; i < 100; i++) {
            bizService.sendMessage(cn.hutool.core.util.RandomUtil.randomString(3 * 1024 * 1024));
        }
    }

}
