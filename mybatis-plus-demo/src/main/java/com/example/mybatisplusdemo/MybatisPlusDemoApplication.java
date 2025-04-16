package com.example.mybatisplusdemo;

import com.example.mybatisplusdemo.service.UserInfoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class MybatisPlusDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisPlusDemoApplication.class, args);
        UserInfoService userInfoService = context.getBean(UserInfoService.class);
        userInfoService.updateUserCourseInfo(2, "psql_backup_id", 1, LocalDateTime.now());
        System.out.println(userInfoService.findUserInfo(2));
    }

}
