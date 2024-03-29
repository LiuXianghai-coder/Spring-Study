package org.xhliu.springtransaction;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xhliu.springtransaction.entity.CourseInfo;
import org.xhliu.springtransaction.mapper.CourseInfoMapper;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
    public void bizHandler() throws InterruptedException {
        DataSourceTransactionExecutor executor = new DataSourceTransactionExecutor(txManager);
        executor.addTask(this::updateCourseType);
        executor.addTask(this::updateCourseTime);
        executor.addTask(this::updateCourseName);
        executor.execute();
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
