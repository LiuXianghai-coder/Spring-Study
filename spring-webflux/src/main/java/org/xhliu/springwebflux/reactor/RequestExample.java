package org.xhliu.springwebflux.reactor;

import org.xhliu.springwebflux.reactor.domian.User;
import org.xhliu.springwebflux.reactor.repository.ReactiveRepository;
import org.xhliu.springwebflux.reactor.repository.ReactiveUserRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class RequestExample {
    ReactiveRepository<User> repository = new ReactiveUserRepository();

    /*
        TODO Create a StepVerifier that initially requests all values
         and expect 4 values to be received
     */
    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create(flux)
                .expectNextCount(4)
                .expectComplete();
    }

    /*
        TODO Create a StepVerifier that initially requests 1 value and expects User.SKYLER
         then requests another value and expects User.JESSE then stops verifying by cancelling the source
     */
    StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
        return StepVerifier.create(flux)
                .thenRequest(1)
                .thenCancel();
    }

    /*
        TODO Return a Flux with all users stored in the repository that
         prints automatically logs for all Reactive Streams signals
     */
    Flux<User> fluxWithLog() {
        return repository
                .findAll()
                .log();
    }

    /*
        TODO Return a Flux with all users stored in the repository that prints "Starring:"
         on subscribe, "firstname lastname" for all values and "The end!" on complete
    */
    Flux<User> fluxWithDoOnPrintln() {
        return repository.findAll()
                .doOnSubscribe(s -> System.out.println("Starring:" + s))
                .doOnNext(p -> System.out.println(p.getFirstname() + " " + p.getLastname()))
                .doOnComplete(() -> System.out.println("The end!"));
    }
}
