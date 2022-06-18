package com.example.demo.controller;

import com.example.demo.domain.od.common.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxh
 * @date 2022/6/18-下午8:17
 */
@RestController
@RequestMapping(path = "/animal")
public class AnimalController {
    private final Logger log = LoggerFactory.getLogger(AnimalController.class);

    @PostMapping("/saveAnimal")
    public HttpStatus saveAnimal(List<Animal> animalList) {
        log.info("get animals {}", animalList.toString());

        return HttpStatus.OK;
    }
}
