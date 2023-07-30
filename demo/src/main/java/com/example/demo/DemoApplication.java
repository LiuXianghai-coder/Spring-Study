package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@tk.mybatis.spring.annotation.MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        Object bean1 = context.getBean("fileTool");
        Object bean2 = context.getBean("fileTool");
        System.out.println(bean1);
        System.out.println(bean2);
    }
}
