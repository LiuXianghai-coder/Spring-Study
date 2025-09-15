package com.example.demo;

import java.util.PriorityQueue;
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

    public double maxAverageRatio(int[][] classes, int extraStudents) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            double r1 = ((a[0] + 1) * 1.0 / (a[1] + 1)) - (a[0] * 1.0 / a[1]);
            double r2 = ((b[0] + 1) * 1.0 / (b[1] + 1)) - (b[0] * 1.0 / b[1]);
            return Double.compare(r2, r1);
        });
        for (int[] cc : classes) {
            pq.offer(new int[]{cc[0], cc[1]});
        }
        for (int i = 0; i < extraStudents; ++i) {
            int[] poll = pq.poll();
            pq.offer(new int[]{poll[0] + 1, poll[1] + 1});
        }
        double sum = 0;
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            sum += 1.0 * poll[0] / poll[1];
        }
        return sum / classes.length;
    }

    public static void main(String[] args) throws Throwable {
        Application app = new Application();
//        System.out.println(app.maxAverageRatio(new int[][]{{1, 2}, {3, 5}, {2, 2}}, 2));
        System.out.println(app.maxAverageRatio(new int[][]{{2, 4}, {3, 9}, {4, 5}, {2, 10}}, 4));
    }
}
