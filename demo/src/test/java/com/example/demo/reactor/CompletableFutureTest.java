package com.example.demo.reactor;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 *@author lxh
 */
@RunWith(MockitoJUnitRunner.class)
public class CompletableFutureTest {

    private final static Logger log = LoggerFactory.getLogger(CompletableFutureTest.class);

    static final int MIN_SLEEP = 100;
    static final int MAX_SLEEP = 500;

    static Object task1() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(random.nextInt(MIN_SLEEP, MAX_SLEEP));
            log.info("task1 take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return "task_1";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static Object task2() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(random.nextInt(MIN_SLEEP, MAX_SLEEP));
            log.info("task2 take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return "task_2";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static Object task3(Object param1) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        log.info("task3 params: {}", Arrays.toString(new Object[]{param1}));
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(random.nextInt(MIN_SLEEP, MAX_SLEEP));
            log.info("task3 take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return "task_3";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static Object task4(Object param1, Object param2) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        log.info("task4 params: {}", Arrays.toString(new Object[]{param1, param2}));
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(random.nextInt(MIN_SLEEP, MAX_SLEEP));
            log.info("task4 take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return "task_4";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static Object task5(Object param2) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        log.info("task5 params: {}", Arrays.toString(new Object[]{param2}));
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(random.nextInt(MIN_SLEEP, MAX_SLEEP));
            log.info("task5 take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return "task_5";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static Object task6(Object param3, Object param4, Object param5) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        log.info("task6 params: {}", Arrays.toString(new Object[]{param3, param4, param5}));
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(random.nextInt(MIN_SLEEP, MAX_SLEEP));
            log.info("task6 take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return "task_6";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
                start
               /     \
             task1   task2
             /    \   /   \
           task3  task4   task5
              \     |      /
               \    |     /
                \   |    /
                  task6
                    |
                   end
     */
    @Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Stopwatch stopwatch = Stopwatch.createStarted();
        CompletableFuture<Object> cf1 = CompletableFuture.supplyAsync(CompletableFutureTest::task1, executor);
        CompletableFuture<Object> cf2 = CompletableFuture.supplyAsync(CompletableFutureTest::task2, executor);
        CompletableFuture<Object> cf3 = cf1.thenApply(CompletableFutureTest::task3);
        CompletableFuture<Object> cf4 = cf1.thenCombine(cf2, CompletableFutureTest::task4);
        CompletableFuture<Object> cf5 = cf2.thenApply(CompletableFutureTest::task5);
        CompletableFuture<String> cf6 = CompletableFuture.allOf(cf3, cf4, cf5).thenApply(v -> {
            CompletableFutureTest.task6(cf3.join(), cf4.join(), cf5.join());
            return "result";
        });
        cf6.get();
        log.info("total take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void reactorTest() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Scheduler scheduler = Schedulers.fromExecutor(executor);
        final CountDownLatch latch = new CountDownLatch(1);

        Stopwatch stopwatch = Stopwatch.createStarted();
        Mono<Object> mono1 = Mono.fromCallable(CompletableFutureTest::task1)
                .subscribeOn(scheduler);
        Mono<Object> mono2 = Mono.fromCallable(CompletableFutureTest::task2)
                .subscribeOn(scheduler);
        Mono<Object> mono6 = Flux.zip(mono1, mono2)
                .flatMap(tuple -> Flux.zip(
                        Mono.fromCallable(() -> CompletableFutureTest.task3(tuple.getT1()))
                                .subscribeOn(scheduler),
                        Mono.fromCallable(() -> CompletableFutureTest.task4(tuple.getT1(), tuple.getT2()))
                                .subscribeOn(scheduler),
                        Mono.fromCallable(() -> CompletableFutureTest.task5(tuple.getT2()))
                                .subscribeOn(scheduler)
                ))
                .single()
                .flatMap(t -> Mono.fromCallable(() -> CompletableFutureTest.task6(t.getT1(), t.getT2(), t.getT3()))
                        .subscribeOn(scheduler))
                .doFinally(any -> latch.countDown());
        mono6.subscribe();
        latch.await();
        log.info("total take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
