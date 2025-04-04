package org.xhliu.springredission;

import com.google.common.base.Stopwatch;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringRedissionApplication {

    private final static Logger log = LoggerFactory.getLogger(SpringRedissionApplication.class);

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringRedissionApplication.class, args);
        RedissonClient redissonClient = context.getBean(RedissonClient.class);

        log.info("ready to create delay deque");

        Stopwatch stopwatch = Stopwatch.createStarted();
        stopwatch.reset();
        stopwatch.start();
        for (int i = 0; i < 5; i++) {
            RBlockingDeque<String> chatBlockingDeque = redissonClient.getBlockingDeque("delay-queue");
            RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(chatBlockingDeque);
            delayedQueue.offer("测试延时消息_" + (i + 1), 5, TimeUnit.SECONDS);
        }

        while (true) {
            RBlockingDeque<String> chatBlockingDeque = redissonClient.getBlockingDeque("delay-queue");
            RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(chatBlockingDeque);
            String msg = chatBlockingDeque.take();
            log.info("{} take {} ms", msg, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }
}
