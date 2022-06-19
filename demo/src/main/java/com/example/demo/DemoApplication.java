package com.example.demo;

import com.example.demo.config.JsonConfig;
import com.example.demo.controller.JustController;
import com.example.demo.entity.Order;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args) throws FileNotFoundException {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        JsonConfig jsonConfig = context.getBean(JsonConfig.class);
        Gson gson = jsonConfig.gson();

        Order order = new Order();
        order.setId(1);
        order.setOrderCreatedDate(LocalDate.now());
        order.setOrderCreatedDateTime(LocalDateTime.now());

        System.out.println(gson.toJson(order));

        String json = gson.toJson(order);

        Order order1 = gson.fromJson(json, Order.class);
        System.out.println(order1);
        System.out.println(gson.toJson(order1));
    }
}
