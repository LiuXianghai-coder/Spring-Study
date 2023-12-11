package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *@author lxh
 */
public class ServiceUtil {

    private final static Logger log = LoggerFactory.getLogger(ServiceUtil.class);

    public static String service1() {
        log.info("service-1 has been invoked");
        return "service-1";
    }

    public static String service2() {
        log.info("service-2 has been invoked");
        return "service-2";
    }

    public static String service3() {
        log.info("service-3 has been invoked");
        return "service-3";
    }

    public static String merge(Object... elements) {
        log.info(Arrays.toString(elements));
        return "Merge-Service";
    }

    public static void main(String[] args) {
        List<Mono<String>> monoList = new ArrayList<>();
        monoList.add(Mono.fromCallable(ServiceUtil::service1)
                .subscribeOn(Schedulers.newSingle("service-thread-1")));
        monoList.add(Mono.fromCallable(ServiceUtil::service2)
                .subscribeOn(Schedulers.newSingle("service-thread-2")));
        monoList.add(Mono.fromCallable(ServiceUtil::service3)
                .subscribeOn(Schedulers.newSingle("service-thread-3")));

        Mono<String> mergeMono = Flux.zip(monoList, Tuples::fromArray)
                .single()
                .flatMap(tuple2 -> Mono.fromCallable(() -> merge(tuple2.toArray())));
        mergeMono.doOnSubscribe(any -> log.info("开始执行合并的相关操作"))
                .subscribe();
    }
}
