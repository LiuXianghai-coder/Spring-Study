package org.xhliu.sharding;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootApplication(exclude = {SpringApplicationAdminJmxAutoConfiguration.class})
@MapperScan(value = {"org.xhliu.sharding.mapper"}, lazyInitialization = "true")
public class ShardingApplicationTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingApplicationTestApplication.class, args);
    }
}
