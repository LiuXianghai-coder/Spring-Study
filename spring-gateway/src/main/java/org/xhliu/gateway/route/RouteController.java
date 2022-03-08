package org.xhliu.gateway.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author xhliu
 * @time 2022-03-08-下午9:19
 */
@Configuration
public class RouteController {
    private final static Logger log = LoggerFactory.getLogger(RouteController.class);

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route(
                RequestPredicates.GET("/sayParam"),
                request -> {
                    log.info("params = {}", request.queryParams());
                    return ServerResponse.ok().body(Mono.just("Finished"), String.class);
                });
    }
}
