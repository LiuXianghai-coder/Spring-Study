package com.example.springtest.service;

import com.example.springtest.entity.UserInfo;
import com.example.springtest.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@Service
@EnableCaching
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserInfoMapper userInfoMapper;

    @Cacheable(cacheNames = {"user_service_user_info"})
    public UserInfo findUserInfo(Integer userId) {
        return userInfoMapper.findById(userId);
    }
}
