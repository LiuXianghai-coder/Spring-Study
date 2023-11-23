package com.example.demo.tools;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class LoggingThreadPoolExecutorTest {

    static class Task implements Runnable {

        private final static Logger log = LoggerFactory.getLogger(Task.class);

        @Override
        public void run() {
//            throw new TooManyResultsException();
            log.info("任务执行中.......");
        }
    }

    @Test
    public void loggingTest() throws InterruptedException {
        ThreadPoolExecutor executor = new LoggingThreadPoolExecutor(
                1,
                1,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>()
        );
        executor.submit(new Task());
        executor.shutdown();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }
}