package com.example.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;

/**
 * @author xhliu2
 **/
public class Application {

    static void blockingQueueTest() throws Throwable {
        Thread[] producerTs = new Thread[Runtime.getRuntime().availableProcessors()];
        Thread[] consumerTs = new Thread[Runtime.getRuntime().availableProcessors()];
        final BlockingQueue<String> queue = new SynchronousQueue<>(true);

        CountDownLatch endLatch = new CountDownLatch(producerTs.length);
        for (int i = 0; i < consumerTs.length; i++) {
            consumerTs[i] = new Thread(() -> {
                String pollVal;
                try {
                    pollVal = queue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "-consumer:" + pollVal);
            });
            consumerTs[i].start();
            Thread.sleep(500);
        }

        for (int i = 0; i < producerTs.length; i++) {
            final int val = i;
            producerTs[i] = new Thread(() -> {
                try {
                    queue.put(String.valueOf(val));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    endLatch.countDown();
                }
            });
            producerTs[i].start();
        }

        endLatch.await();
    }

    public static void main(String[] args) throws Throwable {
        blockingQueueTest();
    }
}
