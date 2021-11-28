package org.xhliu.springwebflux.webflux.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component(value = "handleA")
public class HandlerA {
    private final static Logger log = LoggerFactory.getLogger(HandlerA.class);

    public Mono<ServerResponse> echo(ServerRequest request) {
        log.info("Ready to echo......");

        return ServerResponse.ok().body(Mono.just(request.queryParams().toString()), String.class);
    }
}
