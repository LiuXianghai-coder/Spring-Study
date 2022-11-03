package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            Object bean = context.getBean(name);
            if (!(bean instanceof BeanPostProcessor)) continue;
            System.out.println(name + "\t" + bean.getClass());
        }
    }
}
