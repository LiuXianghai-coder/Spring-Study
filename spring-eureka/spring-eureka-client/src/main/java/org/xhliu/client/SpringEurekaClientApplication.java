package org.xhliu.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringEurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEurekaClientApplication.class, args);
    }

}
