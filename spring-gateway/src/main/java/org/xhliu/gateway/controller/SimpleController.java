package org.xhliu.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xhliu
 * @time 2022-03-07-下午9:33
 */
@RestController
public class SimpleController {
    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);

    @GetMapping(path = "/say")
    public String say() {
        log.info("[spring-cloud-gateway-service] say Hello");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "[spring-cloud-gateway-service] say Hello\n";
    }

    @GetMapping("/retryRoute")
    public String error() throws Throwable {
        System.out.println("------------------HelloController.retryRoute!------------------");
        throw new RuntimeException();
    }
}
