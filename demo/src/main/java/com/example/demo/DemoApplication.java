package com.example.demo;

import com.example.demo.entity.ProdInfo;
import com.example.demo.mapper.ProdInfoMapper;
import com.example.demo.mapper.RateInfoMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SqlSession session = context.getBean(SqlSession.class);
        ProdInfoMapper mapper = session.getMapper(ProdInfoMapper.class);
        ProdInfo info = new ProdInfo();
        info.setProdName("Computer");
        info.initFiled();
        mapper.insertSelective(info);
    }
}
