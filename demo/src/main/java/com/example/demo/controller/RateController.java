package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xhliu
 * @create 2022-07-21-9:26
 **/
@RestController
@RequestMapping(path = "/rate")
public class RateController {

    @GetMapping(path = "/test")
    public HttpStatus test() {
        return HttpStatus.OK;
    }
}
