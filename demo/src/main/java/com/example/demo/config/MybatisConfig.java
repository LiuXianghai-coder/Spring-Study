package com.example.demo.config;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.common.DynamicDataSource;
import com.example.demo.plugin.*;
import com.example.demo.reflect.TaskInfoReflectorFactory;
import com.example.demo.transaction.DynamicTransactionFactory;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.boot.autoconfigure.SqlSessionFactoryBeanCustomizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tk.mybatis.mapper.autoconfigure.ConfigurationCustomizer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxh
 */
@Configuration
public class MybatisConfig {

    private DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://127.0.0.1:3306/lxh_db?allowMultiQueries=true")
                .username("root")
                .type(DruidDataSource.class)
                .password("18373796017;Liu")
                .build();
    }

    private DataSource postgresqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://127.0.0.1:5432/lxh_db")
                .username("postgres")
                .password("12345678")
                .type(DruidDataSource.class)
                .build();
    }

    @Bean(name = "dynamicDataSource")
    @Profile({"default", "prod", "dev"})
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        DataSource mysqlDataSource = mysqlDataSource();
        ((DruidAbstractDataSource) mysqlDataSource).setDefaultAutoCommit(false);
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

    @Bean(name = "splitQuarterPlugin")
    public Interceptor splitQuarterPlugin() {
        return new OaStatisticSplitPlugin();
    }

    @Bean(name = "pageHelpPlugin")
    public Interceptor pageHelpPlugin() {
        return new PageInterceptor();
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
}
