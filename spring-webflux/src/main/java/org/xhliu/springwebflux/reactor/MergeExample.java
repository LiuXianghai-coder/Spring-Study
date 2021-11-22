package org.xhliu.springwebflux.reactor;

import org.xhliu.springwebflux.reactor.domian.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MergeExample {
    // TODO Merge flux1 and flux2 values with interleave
    Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
        return Flux.merge(flux1, flux2);
    }

    // TODO Merge flux1 and flux2 values with no interleave (flux1 values and then flux2 values)
    Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {
        return Flux.concat(flux1, flux2);
    }

    // TODO Create a Flux containing the value of mono1 then the value of mono2
    Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
        return Flux.concat(mono1, mono2);
    }

}
