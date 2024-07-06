package org.xhliu.springdata.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonLock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.xhliu.springdata.SpringDataApplication;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@ContextConfiguration(classes = SpringDataApplication.class)
public class RedLockTest {

    @Resource(name = "redisDistributeLock")
    private RedissonLock redissonLock;

    private int cnt = 0;

    @BeforeTestMethod("redLockTest")
    public void init() {
        cnt = 0;
    }

    @Test
    public void redLockTest() throws InterruptedException {
        Thread[] ts = new Thread[100];
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                redissonLock.lock();
                try {
                    start.await();
                    cnt += 1;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    end.countDown();
                    redissonLock.unlock();
                }
            });
        }
        for (Thread t : ts) {
            t.start();
        }
        start.countDown();
        end.await();
        Assertions.assertEquals(cnt, ts.length);
    }
}
