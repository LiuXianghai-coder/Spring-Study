package org.xhliu.springwebflux.webflux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/hello")
public class HelloController {
    @GetMapping(path = "")
    public String hello(Model model) {
        model.addAttribute("name", "xhliu2");
        return "hello";
    }

    @GetMapping(path = "/test")
    public @ResponseBody
    Mono<String> test() {
        return Mono.just("xhliu2");
    }
}
