package com.example.demo.aspect;

import com.example.demo.aspect.advisor.ExceptionHandlerAdvisor;
import com.example.demo.aspect.advisor.MapperMethodAdvisor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration(proxyBeanMethods = false)
public class MapperAspect {

    @Bean
    public Advisor mapperMethodAdvisor() {
        return new MapperMethodAdvisor();
    }

    @Bean
    public Advisor exceptionHandlerAdvisor() {
        return new ExceptionHandlerAdvisor();
    }
}
