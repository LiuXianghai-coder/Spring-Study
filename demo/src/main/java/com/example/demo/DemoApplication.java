package com.example.demo;

import com.example.demo.entity.BigJson;
import com.example.demo.entity.RateInfo;
import com.example.demo.entity.SaleInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.BigJsonMapper;
import com.example.demo.mapper.RateInfoMapper;
import com.example.demo.mapper.SaleInfoMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

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
        List<SaleInfo> saleInfoList = mapper.sampleInfo();
        saleInfoList.forEach(v -> v.setAmount(10110));

        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            SaleInfoMapper infoMapper = sqlSession.getMapper(SaleInfoMapper.class);
            for (SaleInfo saleInfo : saleInfoList) {
                infoMapper.update(saleInfo);
            }
            sqlSession.flushStatements();
            sqlSession.commit();
        }
    }
}
