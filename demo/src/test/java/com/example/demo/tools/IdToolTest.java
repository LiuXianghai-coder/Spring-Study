package com.example.demo.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

class IdToolTest {

    private final static Logger log = LoggerFactory.getLogger(IdToolTest.class);

    @Test
    void snowFlake() throws InterruptedException {
        int cpus = Runtime.getRuntime().availableProcessors();
        Thread[] ts = new Thread[cpus];
        Set<Long> set = new HashSet<>();
        Map<Long, Object> map = new ConcurrentHashMap<>();

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                try {
                    start.await();
                    for (int j = 0; j < 1e6; ++j) {
                        long id = IdTool.snowFlake();
                        Assertions.assertFalse(map.containsKey(id));
                        map.put(id, new Object());
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
        end.await();
    }
}