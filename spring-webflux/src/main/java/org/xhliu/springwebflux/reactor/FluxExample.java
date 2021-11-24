package org.xhliu.springwebflux.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhliu.springwebflux.reactor.domian.User;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
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

//        RequestExample requestExample = new RequestExample();
//        Flux<User> userFlux = Flux.just(User.SKYLER, User.JESSE, User.SAUL, User.WALTER);
//        requestExample.requestAllExpectFour(userFlux).verify();

//        Flux<User> userFlux = Flux.just(User.JESSE);
//        requestExample.requestOneExpectSkylerThenRequestOneExpectJesse(userFlux)
//                .verify();
//        requestExample.fluxWithDoOnPrintln().subscribe();

        /*Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
                .zipWith(
                        Flux.range(0, Integer.MAX_VALUE),
                        (one, two) -> String.format("First Flux: %d, Second Flux: %d\n", one, two)
                )
                .subscribe(new Subscriber<String>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(1);
                        System.out.println("onSubscribe.....");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext: " + s);
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

//        ConnectableFlux<Object> publisher = Flux.create(fluxSink -> {
//                    while (true) {
//                        fluxSink.next(System.currentTimeMillis());
//                    }
//                })
//                .sample(Duration.ofSeconds(1))
//                .publish();
//
//        publisher.subscribe(System.out::println);
//        publisher.connect();

        /*Flux.just(1, 2, 3, 4, 5)
                .log()
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;
                    private int amt = 0;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        this.subscription.request(2);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                        amt++;
                        if (amt % 2 == 0) this.subscription.request(2);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // nothing should to do....
                    }

                    @Override
                    public void onComplete() {
                        // nothing should to do....
                    }
                });*/

//        System.out.println("Main Thread: " + Thread.currentThread().getName());
//        List<Integer> elements = new ArrayList<>();
        Flux<Integer> flux = Flux.just(1, 2, 3, 4)
                .log()
                .map(i -> i * 2)
//                .subscribeOn(Schedulers.parallel())
                .doOnNext(s -> System.out.println("Current Thread: " + Thread.currentThread().getName()));
        new Thread(flux::subscribe, "subscribe-Thread").start();

//        Flux flux = Flux.generate(sink->{
//			System.out.println("generate : " + Thread.currentThread().getName());
//			sink.next("");
//		})
//		.doOnNext(any -> System.out.println("1st doOnNext:" + Thread.currentThread().getName()))
//		.take(3);
//		new Thread(flux::subscribe, "subscribeThread").start();

//        Thread.sleep(1000);
//        System.out.println("elements.size(): " + elements.size());
//
//        System.out.println("======================================");
//        Flux.interval(Duration.ofMillis(100))
//                .take(9)
//                .doOnNext(System.out::println)
//                .subscribe();
//        Thread.sleep(1000);
    }
}
