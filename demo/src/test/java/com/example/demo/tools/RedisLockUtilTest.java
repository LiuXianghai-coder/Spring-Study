package com.example.demo.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(MockitoExtension.class)
class RedisLockUtilTest {

    private final static Logger logger = LoggerFactory.getLogger(RedisLockUtilTest.class);

    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    public void init() {
        this.redisTemplate = new RedisTemplate<>();
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisConfig);
        connectionFactory.afterPropertiesSet();
        this.redisTemplate.setConnectionFactory(connectionFactory);
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        this.redisTemplate.afterPropertiesSet();;
    }

    @Test
    public void lockTest() throws InterruptedException {
        Thread[] ts = new Thread[10];
        String resourceId = "TK-0000001";
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(ts.length);
        final int[] val = new int[]{0};
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                try {
                    startLatch.await();
                    RedisLockUtil.tryLock(redisTemplate, resourceId);
                    try {
                        logger.info("{} acquire lock", Thread.currentThread().getName());
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100, 2000));
                        val[0] += 1;
                    } finally {
                        RedisLockUtil.unLock(redisTemplate, resourceId);
                        logger.info("{} release lock", Thread.currentThread().getName());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    endLatch.countDown();
                }
            });
        }

        for (Thread t : ts) {
            t.start();
        }
        startLatch.countDown();
        endLatch.await();
        logger.info("sum={}", val[0]);
    }
}