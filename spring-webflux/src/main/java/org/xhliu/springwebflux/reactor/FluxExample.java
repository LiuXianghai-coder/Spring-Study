package org.xhliu.springwebflux.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhliu.springwebflux.reactor.domian.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

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
        return Flux.interval(Duration.ofMillis(10)).take(5);
    }

    public static void main(String[] args) throws InterruptedException {
        FluxExample example = new FluxExample();
        List<Long> elements = new ArrayList<>();
        Flux<Long> counter = example.counter();
//
//        StepVerifier.create(counter)
//                .expectNext(0L)
//                .expectNext(1L)
//                .expectNext(2L)
//                .expectNext(3L)
//                .expectNext(4L)
//                .verifyComplete();


//        Flux.just(1L, 2L, 3L, 4L, 5L)
//            .log()
//            .subscribe(elements::add);

//        Flux<User> userFlux = Flux.just(User.SKYLER, User.JESSE);
//        StepVerifyExample stepVerifyExample = new StepVerifyExample();
//        stepVerifyExample.expectSkylerJesseComplete(userFlux);
//
//
//        Supplier<Flux<Long>> supplier = () -> Flux.interval(Duration.ofSeconds(1)).take(3600L);
//        stepVerifyExample.expect3600Elements(supplier);
//        StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofHours(3)))
//                .expectSubscription()
//                .expectNoEvent(Duration.ofHours(2))
//                .thenAwait(Duration.ofHours(1))
//                .expectNextCount(1)
//                .expectComplete()
//                .verify();

        RequestExample requestExample = new RequestExample();
//        Flux<User> userFlux = Flux.just(User.SKYLER, User.JESSE, User.SAUL, User.WALTER);
//        requestExample.requestAllExpectFour(userFlux).verify();

//        Flux<User> userFlux = Flux.just(User.JESSE);
//        requestExample.requestOneExpectSkylerThenRequestOneExpectJesse(userFlux)
//                .verify();
        requestExample.fluxWithDoOnPrintln().subscribe();
    }
}
