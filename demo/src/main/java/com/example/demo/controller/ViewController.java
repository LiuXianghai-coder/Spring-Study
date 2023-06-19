package com.example.demo.controller;

import com.example.demo.common.DomData;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping(path = "/views")
@CrossOrigin(origins = "*")
public class ViewController {

    private final static Logger log = LoggerFactory.getLogger(ViewController.class);

    @GetMapping(path = "/data")
    public String view(Model model) {
        StringBuilder sb = new StringBuilder();
        try (InputStream in = Resources.getResourceAsStream("data.html")) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("content", sb.toString());
        return "index";
    }

    @PostMapping(path = "/dom")
    @ResponseBody
    public HttpStatus dom(@RequestBody DomData data) {
        log.info(data.getData());
        return HttpStatus.OK;
    }
}
