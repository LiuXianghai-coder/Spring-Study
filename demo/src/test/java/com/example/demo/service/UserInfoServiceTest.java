package com.example.demo.service;

import com.example.demo.TestApplication;
import com.example.demo.entity.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestApplication.class)
public class UserInfoServiceTest {

    @Resource
    private UserInfoService userInfoService;

    @Test
    public void findUserInfoTest() {
        UserInfo userInfo = userInfoService.findUserInfo(2L);
        Assert.assertEquals("xhliu2", userInfo.getName());
    }
}
