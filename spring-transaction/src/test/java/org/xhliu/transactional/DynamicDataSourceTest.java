package org.xhliu.transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.xhliu.springtransaction.Application;
import org.xhliu.springtransaction.entity.CourseInfo;
import org.xhliu.springtransaction.service.CourseInfoService;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lxh
 */
@SpringBootTest(classes = Application.class)
public class DynamicDataSourceTest {

    private final static Logger log = LoggerFactory.getLogger(DynamicDataSourceTest.class);

    @Resource
    private CourseInfoService courseInfoService;

    @Test
    public void performanceTest() throws InterruptedException {
        Thread[] ts = new Thread[100];
        final CountDownLatch end = new CountDownLatch(ts.length);
        final CountDownLatch start = new CountDownLatch(1);
        Lock lock = new ReentrantLock();
        for (int i = 0; i < ts.length; ++i) {
            ts[i] = new Thread(() -> {
                try {
                    try {
                        start.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("start run..........");
                    CourseInfo info = new CourseInfo();
                    info.setCourseId("1");
                    courseInfoService.updateCourseInfo(info);
                    log.info("finished ..........");
                } finally {
                    end.countDown();
                }
            }, "Test-" + (i + 1));
        }
        for (Thread t : ts) {
            t.start();
        }
        start.countDown();
        end.await();
    }
}
