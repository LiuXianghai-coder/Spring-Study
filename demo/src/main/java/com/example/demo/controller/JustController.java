package com.example.demo.controller;

import com.example.demo.app.FormatOp;
import com.example.demo.tools.DiffTool;
import com.example.demo.entity.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.*;
import java.util.Arrays;

/**
 * @author xhliu2
 * @create 2021-10-14 16:24
 **/
@RequestMapping(path = "/api")
@RestController
public class JustController {
    private final static Logger log = LoggerFactory.getLogger(JustController.class);

    @GetMapping(path = "/test")
    public Mono<Person> test() {
        return Mono.fromSupplier(Person::defaultPerson);
    }

    @FormatOp
    @GetMapping(path = "/old")
    public Mono<Object> old() throws FileNotFoundException {
        File rawFile = new File("src/test/resources/one.json");
        Gson gson = new GsonBuilder().create();
        Object oldObj = gson.fromJson(new FileReader(rawFile), Object.class);

        print();

        return Mono.just(oldObj);
    }

    public void print() {
        log.info("start print......");
    }

    @GetMapping(path = "/diff")
    public Mono<Object> diff() {
        File rawFile = new File("src/test/resources/raw.json");
        File newFile = new File("src/test/resources/new.json");
        try (
                Reader oldReader = new FileReader(rawFile);
                Reader newReader = new FileReader(newFile)
        ) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Object oldObj = gson.fromJson(oldReader, Object.class);
            Object newObj = gson.fromJson(newReader, Object.class);

            DiffTool tool = DiffTool.DiffToolBuilder.aDiffTool()
                    .withDeep(true)
                    .withUseCache(true)
                    .withIdList(Arrays.asList("data.proj.data.id", "data.proj.data.period.id"))
                    .build();

            return Mono.just(tool.compare(oldObj, newObj));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
