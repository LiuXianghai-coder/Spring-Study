package org.xhliu.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xhliu
 * @create 2022-02-24-15:03
 **/
@RestController
public class ConsumerController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(path = "/consumer")
    public List<?> index() {
        return restTemplate.getForEntity(
                "http://xhliu-producer-1/produce",
                List.class
        ).getBody();
    }
}
