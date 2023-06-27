package org.xhliu.springdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.xhliu.springdata.controller.TestController;

import static org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving.AUTODETECT;

@SpringBootApplication
@EnableLoadTimeWeaving(aspectjWeaving = AUTODETECT)
public class SpringDataApplication {

    private final static Logger log = LoggerFactory.getLogger(SpringDataApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringDataApplication.class, args);
        TestController controller = context.getBean(TestController.class);
        log.info("proxy class {}", controller.getClass());
        log.info("info {}", controller.test());
    }

}
