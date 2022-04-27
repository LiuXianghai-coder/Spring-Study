package com.example.demo.controller;

import com.example.demo.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;

/**
 * @author xhliu2
 * @create 2021-10-14 16:24
 **/
@RequestMapping(path = "/api")
@RestController
public class JustController {
//    @GetMapping(path = "/test")
//    public Mono<Person> test() {
//        return Mono.fromSupplier(Person::defaultPerson);
//    }
}
