package com.example.mybatisplusdemo.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.example.mybatisplusdemo.entity.UserInfo;
import com.example.mybatisplusdemo.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 *@author lxh
 */
@Service
@DS("master")
@DSTransactional
public class UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private CourseInfoService courseInfoService;

    public int updateUserCourseInfo(Integer userId, String backUpId,
                                    Integer courseId, LocalDateTime updatedTime) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        userInfo.setBackupId(backUpId);
        courseInfoService.updateCourseInfo(courseId, updatedTime);
        return userInfoMapper.updateById(userInfo);
    }

    public UserInfo findUserInfo(Integer userId) {
        return userInfoMapper.selectById(userId);
    }

}
