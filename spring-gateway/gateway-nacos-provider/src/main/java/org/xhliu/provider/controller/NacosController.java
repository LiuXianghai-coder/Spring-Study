package org.xhliu.provider.controller;

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
 * @time 2022-03-09-下午8:22
 */
@Configuration
public class NacosController {
    private final static Logger log = LoggerFactory.getLogger(NacosController.class);

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(
                RequestPredicates.GET("/sayHello"),
                request -> {
                    log.info("[gateway-nacos-provider]: sayHello");
                    return ServerResponse.ok()
                            .body(
                                    Mono.just("[gateway-nacos-provider]: sayHello\n"),
                                    String.class
                            );
                });
    }
}
