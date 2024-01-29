package com.example.demo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class MultipleThreadTest {

    public static final Logger log = LoggerFactory.getLogger(MultipleThreadTest.class);

    public static void main(String[] args) throws InterruptedException {
        MultipleThreadTest test = new MultipleThreadTest();
        test.executorThreadLiveTest();
    }

    @Test
    public void executorThreadLiveTest() throws InterruptedException {
        ThreadFactory factory = new ThreadFactoryBuilder()
                .setNameFormat("LiveThread")
                .build();
        BlockingQueue<Runnable> queue = new SynchronousQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0,
                100,
                2,
                TimeUnit.SECONDS,
                queue
        );
        for (int i = 0; i < 10; ++i) {
            executor.submit(new WorkTask());
        }
        for (int i = 0; i < 50; i++) {
            executor.submit(new WorkTask());
        }
    }

    static class WorkTask
            implements Runnable {

        @Override
        public void run() {
            try {
                log.info("start task.......");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
