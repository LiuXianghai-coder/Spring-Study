package com.example.springsecurity.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by IntelliJ IDEA.
 * User: liuxianghai
 * Date: 2021/1/11
 * Time: 下午10:55
 */
@Configuration
public class BasicConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("home").setViewName("home");
        registry.addViewController("/better").setViewName("better");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/wonderful").setViewName("wonderful");
    }
}