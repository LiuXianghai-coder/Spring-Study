package org.xhliu.springtransaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.xhliu.springtransaction.datasource.DataSourceType;
import org.xhliu.springtransaction.datasource.DynamicDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author lxh
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://127.0.0.1:3306/lxh_db")
                .username("root")
                .password("18373796017;Liu")
                .type(DruidDataSource.class)
                .build();
    }

    @Bean(name = "psqlDataSource")
    public DataSource psqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://127.0.0.1:5432/lxh_db")
                .username("postgres")
                .password("12345678")
                .type(DruidDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("mysqlDataSource") DataSource mysqlDataSource,
                                        @Qualifier("psqlDataSource") DataSource psqlDataSource) {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = ImmutableMap.builder()
                .put(DataSourceType.MYSQL, mysqlDataSource)
                .put(DataSourceType.POSTGRESQL, psqlDataSource)
                .build();
        dataSource.setTargetDataSources(dataSourceMap);
        dataSource.setDefaultTargetDataSource(mysqlDataSource);
        return dataSource;
    }
}
