package com.example.demo.tools;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.LockSupport;

/**
 * 一个基于单实例 Redis 的中心锁工具类
 *
 *@author lxh
 */
public class RedisLockUtil {

    private static final Duration LOCK_EXPIRE_TIME = Duration.ofSeconds(120); // 出现异常时锁的最大持有时间
    private static final Duration MAX_AWAIT_TIME = Duration.ofSeconds(120); // 尝试获取锁的最大等待时间
    private static final Duration MAX_PARK_TIME = Duration.ofSeconds(5); // 单次被挂起的最大时间
    private static final Duration MAX_SPIN_TIME = Duration.ofMillis(200); // 和一般的自旋不同，这里也会挂起一段时间
    private static final int MAX_SPIN_CNT = 5; // 最大自旋重试次数

    public static void tryLock(RedisTemplate<String, Object> redisTemplate, String resourceId) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        Boolean result;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long start = System.currentTimeMillis();
        int spinCnt = 0;
        int value = random.nextInt();
        while (true) {
            result = opsForValue.setIfAbsent(resourceId, value, LOCK_EXPIRE_TIME);
            if (result != null && result) break;

            /*
                由于缺乏与 Redis 之间必要的同步机制，因此这里只能通过轮询的方式来判断是否能够获取到锁
             */
            if (spinCnt <= MAX_SPIN_CNT) {
                spinCnt += 1;
                // 这里的自旋用于避免频繁访问 redis，但是相比一般的挂起具有更短的挂起时间
                LockSupport.parkNanos(MAX_SPIN_TIME.toNanos());
                continue;
            }

            /*
                自旋次数耗尽，说明现在的资源竞争比较激烈，因此可以挂起一段时间，减少资源的竞争
             */
            spinCnt = 0; // 重置自旋次数，参与后续的资源竞争
            long current = System.currentTimeMillis();
            if (current - start <= MAX_AWAIT_TIME.toMillis()) {
                LockSupport.parkNanos(MAX_PARK_TIME.toNanos());
                continue;
            }
            throw new RuntimeException("获取资源锁[" + resourceId + "]超时");
        }
    }

    public static void unLock(RedisTemplate<String, Object> redisTemplate, String resourceId) {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.getAndDelete(resourceId);
    }
}
