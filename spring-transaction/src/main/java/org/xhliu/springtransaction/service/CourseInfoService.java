package org.xhliu.springtransaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhliu.springtransaction.annotation.DataSource;
import org.xhliu.springtransaction.datasource.DataSourceHolder;
import org.xhliu.springtransaction.datasource.DataSourceType;
import org.xhliu.springtransaction.entity.CourseInfo;
import org.xhliu.springtransaction.mapper.CourseInfoMapper;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author lxh
 */
@Service
@Transactional
public class CourseInfoService {

    private final static Logger log = LoggerFactory.getLogger(CourseInfoService.class);

    @Resource
    private CourseInfoMapper courseInfoMapper;

    @Resource
    private UserInfoService useInfoService;

    @DataSource
    public void updateCourseInfo(CourseInfo courseInfo) {
        DataSourceHolder.setCurDataSource(DataSourceType.MYSQL);
        log.trace("save course info {}", courseInfo);

        courseInfo = courseInfoMapper.selectById(1);
        log.info("query course info = {}", courseInfo);

        courseInfo.setUpdateTime(LocalDateTime.now());
        courseInfoMapper.updateById(courseInfo);
        log.info("updated course info.....");

        courseInfoMapper.insert(CourseInfo.newRow());
        log.info("inserted row ={}", courseInfoMapper.selectById("8"));
        courseInfoMapper.deleteById("8");

        useInfoService.updateUserInfo();
        log.info("finished update user info......");
    }
}
