package org.xhliu.springwebflux.reactor.repository;

import org.reactivestreams.Publisher;
import org.xhliu.springwebflux.reactor.domian.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements ReactiveRepository<User> {
    private final List<User> userList = new ArrayList<>();

    public Mono<Void> save(Publisher<User> publisher) {
        return Mono.empty();
    }

    public Mono<User> findFirst() {
        return null;
    }

    public Flux<User> findAll() {
        return null;
    }

    public Mono<User> findById(String id) {
        return null;
    }
}
