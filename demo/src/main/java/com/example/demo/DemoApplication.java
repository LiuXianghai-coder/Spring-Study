package com.example.demo;

import com.example.demo.mapper.UserInfoMapper;
import com.google.common.base.Stopwatch;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@tk.mybatis.spring.annotation.MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SqlSession sqlSession = context.getBean(SqlSession.class);
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 1000; ++i) {
            sqlSession.getMapper(UserInfoMapper.class);
        }
        System.out.println("Take time " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }
}
