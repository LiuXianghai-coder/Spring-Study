package com.example.demo.tools;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author xhliu
 * @create 2022-08-09-9:02
 **/
public class ExecutorTool {

    private static class Task implements Callable<Void> {

        private final static Logger log = LoggerFactory.getLogger(Task.class);

        private final int start, end;

        private final Queue<Long> queue; // thread safe queue

        private Task(int start, int end, Queue<Long> queue) {
            this.start = start;
            this.end = end;
            this.queue = queue;
        }

        @Override
        public Void call() throws Exception {
            long sum = 0L;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            queue.add(sum);
            log.info(Thread.currentThread().getName() + " cal result={}", sum);
            return null;
        }
    }

    private final static int CORE_SZ = Runtime.getRuntime().availableProcessors();

    private final static Logger log = LoggerFactory.getLogger(ExecutorTool.class);

    public void calculate() {
        ThreadFactory factory = new ThreadFactoryBuilder().build();
        ExecutorService exService = Executors.newFixedThreadPool(2*CORE_SZ, factory);
        int tasks = 10, unit = 10000;
        List<Callable<Void>> list = new ArrayList<>();
        Queue<Long> queue = new ConcurrentLinkedDeque<>();
        for (int i = 0; i < tasks; ++i) {
            list.add(new Task(i*unit, (i + 1)*unit, queue));
        }
        long start, end;
        start = System.currentTimeMillis();
        try {
            exService.invokeAll(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        for (Long val : queue) {
            System.out.println(val + "\t");
        }
        System.out.println();
        log.info("take time {} s", (end - start) * 10. / 1000);
        exService.shutdown();
    }

    public static void main(String[] args) {
        ExecutorTool tool = new ExecutorTool();
//        tool.calculate();
        BigDecimal val = new BigDecimal("125500000.00000");
        System.out.println(val.stripTrailingZeros().toPlainString());
    }
}
