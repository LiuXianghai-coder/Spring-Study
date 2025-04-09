package org.xhliu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.xhliu.registry.LazyMapperRegistry;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@Import({LazyMapperRegistry.class})
@MapperScan(basePackages = {"org.xhliu.mapper"})
public class SlowApplication {

    private final static Logger log = LoggerFactory.getLogger(SlowApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SlowApplication.class, args);
    }
}