package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.plugin.BackUpInfoReadPlugin;
import com.example.demo.plugin.BackupInfoPlugin;
import com.example.demo.plugin.TransactionConfigurationCustomizer;
import com.example.demo.plugin.TransactionSqlSessionFactoryBeanCustomizer;
import com.example.demo.reflect.TaskInfoReflectorFactory;
import com.example.demo.transaction.DataSourceHolder;
import com.example.demo.transaction.DynamicTransactionFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.boot.autoconfigure.SqlSessionFactoryBeanCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import tk.mybatis.mapper.autoconfigure.ConfigurationCustomizer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lxh
 */
@EnableConfigurationProperties
@Configuration
public class MybatisConfig {

    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://127.0.0.1:3306/lxh_db")
                .username("root")
                .type(DruidDataSource.class)
                .password("12345678")
                .build();
    }

    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://127.0.0.1:5432/lxh_db")
                .username("postgres")
                .password("17358870357yi")
                .type(DruidDataSource.class)
                .build();
    }

    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        DataSource mysqlDataSource = mysqlDataSource();
        dataSourceMap.put("mysql", mysqlDataSource);
        dataSourceMap.put("postgresql", postgresqlDataSource());
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(dataSourceMap);
        dataSource.setDefaultTargetDataSource(mysqlDataSource);
        return dataSource;
    }

    @Bean(name = "backupInfoPlugin")
    public Interceptor backupInfoPlugin() {
        return new BackupInfoPlugin();
    }

    @Bean(name = "backUpInfoReadPlugin")
    public Interceptor backUpInfoReadPlugin() {
        return new BackUpInfoReadPlugin();
    }

    @Bean(name = "taskInfoReflectorFactory")
    public TaskInfoReflectorFactory taskInfoReflectorFactory() {
        return new TaskInfoReflectorFactory();
    }

    @Bean(name = "dynamicTransactionFactory")
    public TransactionFactory dynamicTransactionFactory() {
        return new DynamicTransactionFactory();
    }

    @Bean(name = "dynamicTransactionFactoryBeanCustomizer")
    public SqlSessionFactoryBeanCustomizer
    dynamicTransactionFactoryBeanCustomizer(@Qualifier("dynamicTransactionFactory") TransactionFactory txFactory) {
        return new TransactionSqlSessionFactoryBeanCustomizer(txFactory);
    }

    @Bean
    public ConfigurationCustomizer
    configurationCustomizer(@Qualifier("dynamicTransactionFactory") TransactionFactory txFactory,
                            DataSource dataSource) {
        return new TransactionConfigurationCustomizer(txFactory, dataSource);
    }

    public static class DynamicDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            String curDataSource = DataSourceHolder.getCurDataSource();
            return Objects.requireNonNullElse(curDataSource, "mysql");
        }
    }
}
