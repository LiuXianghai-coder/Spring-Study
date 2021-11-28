package org.xhliu.springwebflux.webflux.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.xhliu.springwebflux.webflux.component.HandlerA;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Configuration
public class RouterController {
    @Bean
    public RouterFunction<ServerResponse> route1() {
        return RouterFunctions.route(
                RequestPredicates.GET("/route1"),
                request -> ServerResponse.ok().body(Mono.just("This is a Mono Sample"), String.class)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> route2() {
        return RouterFunctions.route(
                RequestPredicates.GET("/route2"),
                request -> ServerResponse.ok().body(Mono.just("This is route2"), String.class)
        ).andRoute(
                RequestPredicates.GET("/route3"),
                request -> ServerResponse.ok().body(Mono.just("This is route3"), String.class)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> route3(@Autowired HandlerA handlerA) {
        return RouterFunctions.route(
                RequestPredicates.GET("/route4"),
                handlerA::echo
        );
    }
}
