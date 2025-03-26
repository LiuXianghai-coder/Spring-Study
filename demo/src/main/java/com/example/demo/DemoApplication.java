package com.example.demo;

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

        OaStatisticMapper statisticMapper = context.getBean(OaStatisticMapper.class);
        PlatformTransactionManager txManager = context.getBean(PlatformTransactionManager.class);
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        try {
            LocalDateTime firstQuarter = LocalDateTime.of(LocalDate.of(2025, 1, 1), LocalTime.MIDNIGHT);
            LocalDateTime secondQuarter = LocalDateTime.of(LocalDate.of(2025, 4, 1), LocalTime.MIDNIGHT);
            LocalDateTime thirdQuarter = LocalDateTime.of(LocalDate.of(2025, 7, 1), LocalTime.MIDNIGHT);

            Map<Integer, LocalDateTime> mod2Quarter = ImmutableMap.of(0, firstQuarter,
                    1, secondQuarter, 2, thirdQuarter);

            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < 1000; i++) {
                OaStatistic statistic = new OaStatistic();
                statistic.setStatisticContent("0x3f3f");
                statistic.setCreatedId("xhliu");
                statistic.setCreatedTime(mod2Quarter.get(random.nextInt(0, 100) % 3).plusDays(random.nextInt(0, 88)));
                statisticMapper.insertStatisticContent(statistic);
            }
            txManager.commit(status);
            System.exit(0);
        } catch (Throwable t) {
            log.error("", t);
            txManager.rollback(status);
        }
    }
}
