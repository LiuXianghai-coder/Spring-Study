package com.example.demo;

import com.example.demo.entity.SaleInfo;
import com.example.demo.mapper.SaleInfoMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Collections;
import java.util.List;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@tk.mybatis.spring.annotation.MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {

    private final static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SaleInfoMapper mapper = context.getBean(SaleInfoMapper.class);
        Example example = Example.builder(SaleInfo.class)
                .andWhere(Sqls.custom().andBetween("id", 1L, 500))
                .build();
        List<SaleInfo> data = mapper.selectByExample(example);
        mapper.mysqlUpdateAll(data);
    }
}
