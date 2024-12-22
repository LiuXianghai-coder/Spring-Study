package org.xhliu.mybatis.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author xhliu
 * @time 2022-03-03-下午10:36
 */
@Configuration
@MapperScan("org.xhliu.mybatis")
public class MybatisConfig {
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        dataSource.setMaximumPoolSize(10);
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/lxh_db");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }
}
