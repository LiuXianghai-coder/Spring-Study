package org.xhliu.springwebflux.reactor;

import reactor.core.publisher.Mono;

public class MonoExample {
    // TODO Return an empty Mono
    Mono<String> emptyMono() {
        return Mono.empty();
    }

    // TODO Return a Mono that never emits any signal
    Mono<String> monoWithNoSignal() {
        return Mono.never();
    }

    // TODO Return a Mono that contains a "foo" value
    Mono<String> fooMono() {
        return Mono.just("foo");
    }

    // TODO Create a Mono that emits an IllegalStateException
    Mono<String> errorMono() {
        return Mono.error(new IllegalStateException());
    }
}
