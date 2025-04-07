package com.example.demo;

import cn.hutool.core.lang.Snowflake;
import com.example.demo.entity.OaStatistic;
import com.example.demo.mapper.OaStatisticMapper;
import com.google.common.collect.ImmutableMap;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"}, lazyInitialization = "true")
public class DemoApplication {

    private final static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
    }
}
