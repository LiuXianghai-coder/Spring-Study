package com.example.demo.tools;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lxh
 */
public class IdTool {

    private final static Logger log = LoggerFactory.getLogger(IdTool.class);

    private static final int TIME_BITS = 19; // 时间位需要向左移动的位数

    private static final int WORK_BITS = 10; // 机器编号需要向左移动的位数

    private static final long TIME = 0xFFFFFFFFFFEL << TIME_BITS;

    private static final long WORKER = 0x00000000003FFL << WORK_BITS;

    private static final long SEQ = 0x3FFL;

    private static long lastMills = 0L;

    private static final int MAX_SEQ = (1 << WORK_BITS) - 1;

    private static final Map<Long, AtomicInteger> cache = new ConcurrentHashMap<>();

    private static final AtomicInteger curSeq = new AtomicInteger(0);

    private static final Lock lock = new ReentrantLock();

    public static long snowFlake() {
        return snowFlake(0L);
    }

    public static long singleSnowFlake() {
        return singleSnowFlake(0L);
    }

    public static long snowFlake(long workId) {
        long cur = curTime();
        if (cache.containsKey(cur)) {
            AtomicInteger seq = cache.get(cur);
            int val = seq.getAndIncrement();
            if (val > MAX_SEQ) {
                return concurrentGen(workId, cur);
            }
            return (cur << TIME_BITS) | (workId << WORK_BITS) | val;
        }
        return concurrentGen(workId, cur);
    }

    private static long concurrentGen(long workId, long cur) {
        lock.lock();
        try {
            while (cache.containsKey(cur) && cache.get(cur).get() > MAX_SEQ) {
                cache.remove(cur);
                cur = waitToNextMills(cur);
            }
            AtomicInteger seq = cache.containsKey(cur) ? cache.get(cur) : new AtomicInteger(0);
            int val = seq.getAndIncrement();
            cache.put(cur, seq);
            return (cur << TIME_BITS) | (workId << WORK_BITS) | val;
        } finally {
            lock.unlock();
        }
    }

    public static synchronized long singleSnowFlake(long workId) {
        long cur = System.currentTimeMillis();
        if (lastMills <= 0) lastMills = cur;
        if (cur < lastMills) {
            throw new RuntimeException("时钟系统被回退");
        }
        int val;
        if (cur == lastMills) {
            val = curSeq.getAndIncrement();
            if (val > MAX_SEQ) {
                lastMills = waitToNextMills(lastMills);
                curSeq.set(0);
                val = curSeq.getAndIncrement();
            }
        } else {
            curSeq.set(0);
            val = curSeq.getAndIncrement();
            lastMills = cur;
        }
        return (lastMills << TIME_BITS) | (workId << WORK_BITS) | val;
    }

    private static long waitToNextMills(long lastMills) {
        long cur = System.currentTimeMillis();
        while (cur <= lastMills) {
            cur = System.currentTimeMillis();
        }
        return cur;
    }

    private static long curTime() {
        return System.currentTimeMillis();
    }

    public static String randomName(ThreadLocalRandom random) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; ++i) {
            int r = random.nextInt(0, 26);
            sb.append((char) ('a' + r));
            if (r > 20 && sb.length() > 3) break;
        }
        return sb.toString();
    }

    public static String randomName(ThreadLocalRandom random, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            int r = random.nextInt(0, 26);
            sb.append((char) ('a' + r));
            if (r > 20 && sb.length() > 3) break;
        }
        return sb.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        int sz = Runtime.getRuntime().availableProcessors();
        Thread[] ts = new Thread[sz];
        Map<Long, Object> map = new ConcurrentHashMap<>();
        final Object obj = new Object();
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; ++i) {
            ts[i] = new Thread(() -> {
                try {
                    start.await();
                    for (int j = 0; j < 1e7; j++) {
                        long id = IdTool.singleSnowFlake();
                        log.trace("Gen id: {}", id);
                        if (map.containsKey(id)) {
                            log.info("重复的id: {}", id);
                            return;
                        }
                        map.put(id, obj);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    end.countDown();
                }
            });
        }
        for (Thread t : ts) {
            t.start();
        }
        start.countDown();
        Stopwatch stopwatch = Stopwatch.createStarted();
        end.await();
        log.info("Take Time {} s", stopwatch.elapsed(TimeUnit.SECONDS));
    }
}
