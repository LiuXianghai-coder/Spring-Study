package com.example.demo;

import com.example.demo.common.ReadAuth;
import com.example.demo.component.ObserveComponent;
import com.example.demo.mapper.RateInfoMapper;
import com.example.demo.service.strategy.RateCalStrategy;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SqlSession session = context.getBean(SqlSession.class);
        RateInfoMapper mapper = session.getMapper(RateInfoMapper.class);
        System.out.println(mapper.selectById(1L));
    }
}
