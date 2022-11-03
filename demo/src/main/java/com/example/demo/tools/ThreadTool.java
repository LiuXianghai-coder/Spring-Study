package com.example.demo.tools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xhliu
 **/
public class ThreadTool {
    public static void main(String[] args) throws InterruptedException {
        int sz = Runtime.getRuntime().availableProcessors();
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(sz);
        Thread[] threads = new Thread[sz];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    start.await();
                    System.out.println(Thread.currentThread().getName() + " await......");
                    Thread.sleep(Math.abs(ThreadLocalRandom.current().nextLong()) % 3000);
                    end.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " finish.....");
            }, "Thread-" + (i + 1));
        }
        for (Thread t : threads) t.start();
        start.countDown();
        end.await();
    }
}
