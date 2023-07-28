package com.example.demo.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lxh
 */
public class IdTool {

    private static final int TIME_BITS = 19; // 时间位需要向左移动的位数

    private static final int WORK_BITS = 10; // 机器编号需要向左移动的位数

    private static final long TIME = 0xFFFFFFFFFFEL << TIME_BITS;

    private static final long WORKER = 0x00000000003FFL << WORK_BITS;

    private static final long SEQ = 0x3FFL;

    private static long lastMills = 0L;

    private static final int MAX_SEQ = (1 << WORK_BITS) - 1;

    private static final AtomicInteger seq = new AtomicInteger(0);

    public static long snowFlake() {
        return snowFlake(0L);
    }

    public static synchronized long snowFlake(long workId) {
        long cur = System.currentTimeMillis();
        if (lastMills <= 0) lastMills = cur;
        if (cur < lastMills) {
            throw new RuntimeException("时钟系统被回退");
        }
        int val;
        if (cur == lastMills) {
            val = seq.getAndIncrement();
            if (val > MAX_SEQ) {
                lastMills = waitToNextMills(lastMills);
                seq.set(0);
                val = seq.getAndIncrement();
            }
        } else {
            seq.set(0);
            val = seq.getAndIncrement();
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

    public static void main(String[] args) throws InterruptedException {
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < 1e7; ++i) {
            long id = snowFlake();
            if (set.contains(id)) {
                throw new RuntimeException("重复的 id: " + id);
            }
            set.add(id);
            if (i % 1000 == 0) Thread.sleep(1);
        }
    }
}
