package com.example.demo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author xhliu
 **/
public class ThreadTool {

    private final static Logger log = LoggerFactory.getLogger(ThreadTool.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int sz = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(sz, sz * 2, 60,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setUncaughtExceptionHandler((t, ex) -> {
                        log.error("thread exception", ex);
                    });
                    return thread;
                }
        );
        Thread thread = new Thread(() -> {
            throw new RuntimeException("抛出的异常");
        });
        thread.setUncaughtExceptionHandler((t, ex) -> {
            log.error("thread exception", ex);
        });
        thread.start();
    }
}
