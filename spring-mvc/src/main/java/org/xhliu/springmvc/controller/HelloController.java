package org.xhliu.springmvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
public class HelloController {
    @GetMapping(path = "")
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "/exception/{val}")
    public String exceptionExample(@PathVariable Long val) {
        if (val == 0L) {
            throw new IllegalArgumentException("val 不能为 0L");
        }

        return "OK";
    }
}
