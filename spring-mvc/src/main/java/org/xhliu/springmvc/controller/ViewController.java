package org.xhliu.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/view")
public class ViewController {
    @GetMapping(path = "")
    public String hello(Model model) {
        model.addAttribute("name", "Xianghai Liu");
        throw new RuntimeException("非法的参数");
//        return "hello";
    }
}
