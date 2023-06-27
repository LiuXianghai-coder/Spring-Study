package org.xhliu.springdata.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final static Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(path = "/test")
    public @ResponseBody String test() {
        return "OK";
    }
}
