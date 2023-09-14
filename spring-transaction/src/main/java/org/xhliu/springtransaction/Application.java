package org.xhliu.springtransaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *@author lxh
 */
@SpringBootApplication
@MapperScan(basePackages = {"org.xhliu.springtransaction.mapper"})
public class Application {

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        MultiThreadTransaction transaction = context.getBean(MultiThreadTransaction.class);
        transaction.bizHandler();
    }
}
