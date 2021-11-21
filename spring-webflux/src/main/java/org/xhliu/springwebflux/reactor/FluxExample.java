package org.xhliu.springwebflux.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluxExample {

    private final static Logger log = LoggerFactory.getLogger(FluxExample.class);

    public Flux<Integer> empty() {
        return Flux.empty();
    }

    /*
        TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
    */
    public Flux<String> fooBarFluxFromValues() {
        return Flux.just("foo", "bar");
    }

    /*
        TODO Create a Flux from a List that contains 2 values "foo" and "bar"
    */
    Flux<String> fooBarFluxFromList() {
        List<String> iterable = Arrays.asList("foo", "bar");
        return Flux.fromIterable(iterable);
    }

    /*
        TODO Create a Flux that emits an IllegalStateException
    */
    Flux<String> errorFlux() {
        return Flux.error(new IllegalStateException());
    }

    /*
        TODO Create a Flux that emits increasing values from 0 to 9 each 100ms
    */
    Flux<Long> counter() {
        return Flux.interval(Duration.ofMillis(10)).take(1);
    }

    public static void main(String[] args) {
        FluxExample example = new FluxExample();
        List<Long> elements = new ArrayList<>();
        example.counter().subscribe(System.out::println);

//        Flux.just(1L, 2L, 3L, 4L, 5L)
//            .log()
//            .subscribe(elements::add);

        System.out.println(elements.size());
    }
}
