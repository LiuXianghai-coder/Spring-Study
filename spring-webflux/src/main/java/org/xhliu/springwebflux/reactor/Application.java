package org.xhliu.springwebflux.reactor;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        /*Flux.<String>generate(sink -> {
                    sink.next(sc.next());
                })
                .doFinally(any -> sc.close())
                .map(String::toUpperCase)
                .subscribe(System.out::println);*/

        Flux<Object> publish = Flux.create(sink -> {
                    while (true) {
                        sink.next(System.currentTimeMillis());
                    }
                })
                .sample(Duration.ofMillis(500))
                .publish().autoConnect();
        publish.subscribe(s -> System.out.println("Subscribe-1: " + s));
        publish.subscribe(s -> System.out.println("Subscribe-2: " + s));
//        publish.connect();
    }
}
