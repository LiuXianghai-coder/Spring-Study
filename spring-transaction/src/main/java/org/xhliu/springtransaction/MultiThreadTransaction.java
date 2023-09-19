package org.xhliu.springtransaction;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.xhliu.springtransaction.entity.CourseInfo;
import org.xhliu.springtransaction.mapper.CourseInfoMapper;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *@author lxh
 */
@Component
public class MultiThreadTransaction {

    @Resource
    private CourseInfoMapper courseInfoMapper;

    @Resource
    private DataSourceTransactionManager txManager;

    @Transactional
    public void bizHandler() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>()
        );
        DataSourceTransactionExecutor executor = new DataSourceTransactionExecutor(
                threadPoolExecutor,
                txManager,
                new DefaultTransactionDefinition()
        );
        executor.addTask(this::updateCourseType);
        executor.addTask(this::updateCourseTime);
        executor.addTask(this::updateCourseName);
        executor.asyncExecute();
    }

    public void updateCourseTime() {
        CourseInfo courseInfo = courseInfoMapper.selectById(1L);
        courseInfo.setUpdateTime(LocalDateTime.now());
        courseInfoMapper.updateById(courseInfo);
        sleepTimes();
    }

    public void updateCourseType() {
        CourseInfo courseInfo = courseInfoMapper.selectById(2L);
        courseInfo.setUpdateTime(LocalDateTime.now());
        courseInfo.setCourseType("2");
        courseInfoMapper.updateById(courseInfo);
        sleepTimes();
    }

    public void updateCourseName() {
        CourseInfo courseInfo = courseInfoMapper.selectById(3L);
        courseInfo.setUpdateTime(LocalDateTime.now());
        courseInfo.setCourseType("2");
        courseInfo.setCourseName("大学英语");
        courseInfoMapper.updateById(courseInfo);
        sleepTimes();
        throw new RuntimeException("出现了一个异常");
    }

    static void sleepTimes() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
