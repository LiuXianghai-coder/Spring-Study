package com.example.demo;

import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.UserInfoMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SqlSessionFactory factory = context.getBean(SqlSessionFactory.class);
        UserInfoMapper mapper = context.getBean(UserInfoMapper.class);
        UserInfo info = new UserInfo();
        info.setId(1L);
        info.setName("xhliu");
        System.out.println(mapper.selectByType(info));
    }
}
