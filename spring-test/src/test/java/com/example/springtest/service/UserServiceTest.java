package com.example.springtest.service;

import com.example.springtest.SpringTestApplication;
import com.example.springtest.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest(classes = SpringTestApplication.class)
class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Resource
    UserService userService;

    @Resource
    ApplicationContext applicationContext;

    @Test
    void findUserInfo() throws InterruptedException {
        UserInfo rawUserInfo = userService.findUserInfo(2);
        UserInfo secondUserInfo = userService.findUserInfo(2);
        log.info("rawUserInfo={}", rawUserInfo);
        log.info("secondUserInfo={}", secondUserInfo);

        assertSame(rawUserInfo, secondUserInfo);

        Thread.sleep(5000);
        UserInfo thirdUserInfo = userService.findUserInfo(2);
        log.info("thirdUserInfo={}", thirdUserInfo);
        assertNotSame(rawUserInfo, thirdUserInfo);
    }
}