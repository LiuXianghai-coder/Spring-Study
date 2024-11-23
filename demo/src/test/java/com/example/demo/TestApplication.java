package com.example.demo;

import com.example.demo.common.DynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *@author lxh
 */
@SpringBootApplication(exclude = {SpringApplicationAdminJmxAutoConfiguration.class})
@MapperScan(value = {"com.example.demo.mapper"}, lazyInitialization = "true")
public class TestApplication {

    static class TestDynamicDataSource extends DynamicDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            return "h2";
        }
    }

    @Bean("dynamicDataSource")
    @Profile("test")
    public DynamicDataSource dynamicDataSource(DataSource dataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("h2", dataSource);
        DynamicDataSource dynamicDataSource = new TestDynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
