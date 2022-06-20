package com.example.demo;

import com.example.demo.config.GsonConfig;
import com.example.demo.config.GsonConfig.NormalDateSerializerAdapter;
import com.example.demo.config.GsonConfig.YearDateSerializerAdapter;
import com.example.demo.config.JsonConfig;
import com.example.demo.controller.JustController;
import com.example.demo.domain.common.entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.demo.entity.Order;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args) throws FileNotFoundException, JsonProcessingException {
//        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new NormalDateSerializerAdapter())
                .create();

        ObjectMapper mapper = new ObjectMapper();

//        Person person = mapper.readValue("{\"name\":\"xhliu\",\"createdTime\":\"2022-06-19 13:16:23\"}", Person.class);
        Person person = new Person();
        person.setName("xhliu");
        person.setCreatedTime(LocalDateTime.now());

        System.out.println(mapper.writeValueAsString(person));
    }
}
