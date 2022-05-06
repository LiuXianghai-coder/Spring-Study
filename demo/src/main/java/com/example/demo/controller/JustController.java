package com.example.demo.controller;

import com.example.demo.entity.DiffTool;
import com.example.demo.entity.Person;
import com.example.demo.entity.Solution;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.*;
import java.util.Map;

/**
 * @author xhliu2
 * @create 2021-10-14 16:24
 **/
@RequestMapping(path = "/api")
@RestController
public class JustController {
    @GetMapping(path = "/test")
    public Mono<Person> test() {
        return Mono.fromSupplier(Person::defaultPerson);
    }

    @GetMapping(path = "/old")
    public Mono<Object> old() throws FileNotFoundException {
        File rawFile = new File("src/test/resources/one.json");
        Gson gson = new GsonBuilder().create();
        Object oldObj = gson.fromJson(new FileReader(rawFile), Object.class);

        return Mono.just(oldObj);
    }

    @GetMapping(path = "/diff")
    public Mono<Object> diff() {
        File rawFile = new File("src/test/resources/one.json");
        File newFile = new File("src/test/resources/two.json");
        try (
                Reader oldReader = new FileReader(rawFile);
                Reader newReader = new FileReader(newFile)
        ) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Object oldObj = gson.fromJson(oldReader, Object.class);
            Object newObj = gson.fromJson(newReader, Object.class);

            return Mono.just(DiffTool.compare(oldObj, newObj));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
