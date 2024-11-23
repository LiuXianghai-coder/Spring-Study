package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@Service
@Transactional
public class UserInfoService {

    private UserInfoMapper userInfoMapper;

    public UserInfo findUserInfo(Long userId) {
        return userInfoMapper.selectByPrimaryKey(String.valueOf(userId));
    }

    public UserInfoMapper getUserInfoMapper() {
        return userInfoMapper;
    }

    @Resource
    public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }
}
