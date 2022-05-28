package com.example.demo;

import com.example.demo.controller.JustController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.FileNotFoundException;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args) throws FileNotFoundException {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        JustController bean = context.getBean(JustController.class);
        System.out.println(bean);
    }
}
