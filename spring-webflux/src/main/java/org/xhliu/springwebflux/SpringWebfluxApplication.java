package org.xhliu.springwebflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories
@SpringBootApplication
public class SpringWebfluxApplication {

    private final static Logger log = LoggerFactory.getLogger(SpringWebfluxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxApplication.class, args);
    }
}
