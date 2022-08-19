package com.example.demo;

import com.example.demo.service.strategy.RateCalStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        RateCalStrategy calStrategy = context.getBean(RateCalStrategy.class);
        calStrategy.handler();
    }
}
