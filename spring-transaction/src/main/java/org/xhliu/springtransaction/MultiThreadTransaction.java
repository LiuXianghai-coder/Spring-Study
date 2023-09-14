package org.xhliu.springtransaction;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.xhliu.springtransaction.entity.CourseInfo;
import org.xhliu.springtransaction.mapper.CourseInfoMapper;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *@author lxh
 */
@Component
public class MultiThreadTransaction {

    @Resource
    private CourseInfoMapper courseInfoMapper;

    @Resource
    private PlatformTransactionManager txManager;

    @Resource
    private DataSourceTransactionManager dataSourceTxManager;

    public void bizHandler() throws InterruptedException, ExecutionException {
        int sz = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(sz * 2, sz * 2,
                10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>()
        );
        TransactionSynchronizationManager.setActualTransactionActive(true);
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        List<Callable<TransactionStatus>> tasks = new ArrayList<>();
        tasks.add(() -> updateCourseTime(txDefinition));
        tasks.add(() -> updateCourseType(txDefinition));
        tasks.add(() -> updateCourseName(txDefinition));

        List<Future<TransactionStatus>> futureList = executor.invokeAll(tasks);
        executor.shutdown();
        boolean termination = executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        List<TransactionStatus> statusList = new ArrayList<>();
        boolean exFlag = false;
        for (Future<TransactionStatus> future : futureList) {
            TransactionStatus status = future.get();
            if (status.isRollbackOnly()) exFlag = true;
            statusList.add(status);
        }
        if (exFlag) {
            for (TransactionStatus status : statusList) {
                txManager.rollback(status);
            }
            return;
        }
        for (TransactionStatus status : statusList) {
            txManager.commit(status);
        }
    }

    public TransactionStatus updateCourseTime(TransactionDefinition txDefinition) {
        TransactionStatus status = txManager.getTransaction(txDefinition);
        try {
            CourseInfo courseInfo = courseInfoMapper.selectById(1L);
            courseInfo.setUpdateTime(LocalDateTime.now());
            courseInfoMapper.updateById(courseInfo);
        } catch (Throwable t) {
            status.setRollbackOnly();
        }
        return status;
    }

    public TransactionStatus updateCourseType(TransactionDefinition txDefinition) {
        TransactionStatus status = txManager.getTransaction(txDefinition);
        try {
            CourseInfo courseInfo = courseInfoMapper.selectById(2L);
            courseInfo.setUpdateTime(LocalDateTime.now());
            courseInfo.setCourseType("2");
            courseInfoMapper.updateById(courseInfo);
        } catch (Throwable t) {
            status.setRollbackOnly();
        }
        return status;
    }

    public TransactionStatus updateCourseName(TransactionDefinition txDefinition) {
        TransactionStatus status = txManager.getTransaction(txDefinition);
        try {
            CourseInfo courseInfo = courseInfoMapper.selectById(3L);
            courseInfo.setUpdateTime(LocalDateTime.now());
            courseInfo.setCourseType("2");
            courseInfo.setCourseName("大学英语");
            courseInfoMapper.updateById(courseInfo);
//            throw new RuntimeException("出现了一个异常");
        } catch (Throwable t) {
            status.setRollbackOnly();
        }
        return status;
    }
}
