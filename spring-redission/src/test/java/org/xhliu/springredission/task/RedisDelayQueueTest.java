package org.xhliu.springredission.task;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.xhliu.springredission.SpringRedissionApplicationTests;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 *@author lxh
 */
@SpringBootTest(classes = SpringRedissionApplicationTests.class)
public class RedisDelayQueueTest {

    private final static Logger log = LoggerFactory.getLogger(RedisDelayQueueTest.class);

    @Resource
    RedissonClient redissonClient;

    @Test
    public void delayQueueTest() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        stopwatch.reset();
        stopwatch.start();

        // 获取一个基于 Redis 的阻塞队列
        RBlockingDeque<String> chatBlockingDeque = redissonClient.getBlockingDeque("delay-queue");
        // 将这个阻塞队列封装成延时队列
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(chatBlockingDeque);
        for (int i = 0; i < 5; i++) {
            delayedQueue.offer("测试延时消息_" + (i + 1), (i + 1) * 2, TimeUnit.SECONDS);
        }

        // 特使阻塞是由于 take 方法导致的
        Assertions.assertTrue(stopwatch.elapsed(TimeUnit.MILLISECONDS) <= 2 * 1000);

        // 校验每条消息是否延时生效
        for (int i = 0; i < 5; i++) {
            String msg = chatBlockingDeque.take();
            long delayTimeMills = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            log.info("{} take {} ms", msg, delayTimeMills);
            Assertions.assertTrue(delayTimeMills >= (i + 1) * 2 * 1000);
        }
    }
}
