package org.xhliu.nacosserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NacosServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NacosServerApplication.class, args);
        ConfigurableApplicationContext contextBean = context.getBean(ConfigurableApplicationContext.class);
        System.out.println("contextBean=" + contextBean);
    }

}
