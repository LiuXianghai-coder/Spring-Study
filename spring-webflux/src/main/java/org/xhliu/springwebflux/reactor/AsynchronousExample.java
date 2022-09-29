package org.xhliu.springwebflux.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class AsynchronousExample {
    static String basPath = "D:/data/example/";

    static class Element {
        private final String name;
        private final Integer[] array;

        Element(String name, Integer[] array) {
            this.name = name;
            this.array = array;
        }

        public String getName() {
            return name;
        }

        public Integer[] getArray() {
            return array;
        }
    }

    static class ReadFunction implements Function<File, Element> {
        private final String name;

        public ReadFunction(String name) {
            this.name = name;
        }

        @Override
        public Element apply(File file) {
            List<Integer> list = new ArrayList<>();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null)
                    list.add(Integer.valueOf(line));
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            Integer[] ans = new Integer[list.size()];
            list.toArray(ans);

            return new Element(name, ans);
        }
    }

    static class SortFunction implements Function<Element, Element> {

        @Override
        public Element apply(Element element) {
            Integer[] tmp = element.getArray();
            Arrays.sort(tmp);
            return element;
        }
    }

    private static void executor() {
        File[] readFiles = new File[]{
                new File(basPath + "/data_1.txt"),
                new File(basPath + "/data_2.txt"),
                new File(basPath + "/data_3.txt"),
                new File(basPath + "/data_4.txt"),
                new File(basPath + "/data_5.txt"),
                new File(basPath + "/data_6.txt"),
        };

        int sz = Runtime.getRuntime().availableProcessors();;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(sz, sz,
                300, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        List<Callable<Element>> tasks = new ArrayList<>();
        for (File file : readFiles) {
            Element element = new ReadFunction(file.getName()).apply(file);
            tasks.add(() -> new SortFunction().apply(element));
        }
        long start = System.currentTimeMillis();
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Thread Poll Take Time " + (end - start) + " ms");
    }

    public static void main(String[] args) throws InterruptedException {
        File[] readFiles = new File[]{
                new File(basPath + "/data_1.txt"),
                new File(basPath + "/data_2.txt"),
                new File(basPath + "/data_3.txt"),
                new File(basPath + "/data_4.txt"),
                new File(basPath + "/data_5.txt"),
                new File(basPath + "/data_6.txt"),
        };

        AtomicLong start = new AtomicLong();
        AtomicLong end = new AtomicLong();
        Flux.just(readFiles)
                .doOnSubscribe(any -> start.set(System.currentTimeMillis()))
                .flatMap(
                        file -> Mono.just(new ReadFunction(file.getName()).apply(file))
                                .subscribeOn(Schedulers.newParallel("Thread-" + file.getName()))
                                .flatMap(element -> Mono.just(new SortFunction().apply(element)))
                )
                .doOnNext(element -> System.out.println(element.getName() + " has been finished......"))
                .subscribe(new Subscriber<Element>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onNext(Element element) {
                        System.out.println("Get Element=" + element.getName());
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        end.set(System.currentTimeMillis());
                        System.out.println("Flux Take Time: " + (end.get() - start.get()) + " ms");
//                        System.exit(0);
                    }
                });
//        System.out.println("before after");
//        executor();
    }
}
