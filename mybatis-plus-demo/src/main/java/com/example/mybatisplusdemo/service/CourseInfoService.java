package com.example.mybatisplusdemo.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.example.mybatisplusdemo.entity.CourseInfo;
import com.example.mybatisplusdemo.mapper.CourseInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 *@author lxh
 */
@Service
@DS("slave")
@DSTransactional
public class CourseInfoService {

    @Resource
    private CourseInfoMapper courseInfoMapper;

    public int updateCourseInfo(Integer courseId, LocalDateTime updatedTime) {
        CourseInfo courseInfo = courseInfoMapper.selectById(courseId);
        courseInfo.setUpdateTime(updatedTime);
        return courseInfoMapper.updateById(courseInfo);
    }

    public CourseInfo findCourseInfo(Integer courseId) {
        return courseInfoMapper.selectById(courseId);
    }
}
