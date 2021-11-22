package org.xhliu.springwebflux.reactor;

import org.xhliu.springwebflux.reactor.domian.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class TransformExample {
    static Function<User, User> capital = user ->
            new User(
                    user.getUsername().toUpperCase(),
                    user.getFirstname().toUpperCase(),
                    user.getLastname().toUpperCase()
            );

    // TODO Capitalize the user username, firstname and lastname
    Mono<User> capitalizeOne(Mono<User> mono) {
        return mono.map(capital);
    }

    // TODO Capitalize the users username, firstName and lastName
    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(capital);
    }

    // TODO Capitalize the users username, firstName and lastName using #asyncCapitalizeUser
    Flux<User> asyncCapitalizeMany(Flux<User> flux) {
        return flux.flatMap(this::asyncCapitalizeUser);
    }

    Mono<User> asyncCapitalizeUser(User u) {
        return Mono.just(new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
    }
}
