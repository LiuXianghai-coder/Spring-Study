package com.example.mybatisplusdemo;

import com.example.mybatisplusdemo.entity.UserInfo;
import com.example.mybatisplusdemo.mapper.UserInfoMapper;
import com.example.mybatisplusdemo.service.UserInfoService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "com.example.mybatisplusdemo.mapper")
public class MybatisPlusDemoApplication {

    private final static Logger log = LoggerFactory.getLogger(MybatisPlusDemoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisPlusDemoApplication.class, args);
        UserInfoMapper userInfoMapper = context.getBean(UserInfoMapper.class);
        UserInfo param = new UserInfo();
        param.setUserId(2);
        param.setUserName("xhliu");
        param.setUserGender("male");
        UserInfo userInfo = userInfoMapper.selectUserInfo(param);
        log.info("userInfo: {}", userInfo);
    }

}
