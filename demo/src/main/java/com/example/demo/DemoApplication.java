package com.example.demo;

import com.example.demo.common.ReadAuth;
import com.example.demo.component.ObserveComponent;
import com.example.demo.service.strategy.RateCalStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        ReadAuth readAuth = AnnotationUtils.findAnnotation(ObserveComponent.class, ReadAuth.class);
        assert readAuth != null;
        System.out.println(readAuth.name());
        System.out.println(readAuth.target());
    }
}
