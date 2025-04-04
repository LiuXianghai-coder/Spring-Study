package org.xhliu.springredission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.xhliu")
public class SpringRedissionApplicationTests {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedissionApplicationTests.class, args);
    }

}
