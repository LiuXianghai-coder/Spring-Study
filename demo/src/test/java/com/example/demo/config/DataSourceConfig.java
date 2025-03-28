package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.common.DynamicDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *@author lxh
 */
@Configuration
public class DataSourceConfig {

    static class TestDynamicDataSource extends DynamicDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            return "h2";
        }
    }

    public DataSource h2DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:dcbapp")
                .username("sa")
                .type(DruidDataSource.class)
                .driverClassName("org.h2.Driver")
                .password("")
                .build();
    }

    @Bean("dynamicDataSource")
    @Profile("test")
    public DynamicDataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        DataSource h2DataSource = h2DataSource();
        dataSourceMap.put("h2", h2DataSource);
        DynamicDataSource dynamicDataSource = new TestDynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }
}
