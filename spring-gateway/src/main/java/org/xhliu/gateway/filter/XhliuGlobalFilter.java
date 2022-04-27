package org.xhliu.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xhliu
 * @time 2022-03-09-下午8:04
 */
@Component
public class XhliuGlobalFilter
        implements GlobalFilter, Ordered {

    private final Logger log = LoggerFactory.getLogger(XhliuGlobalFilter.class);

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {
        log.info("XhliuGlobalFilter [Pre] Filter");
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> log.info("XhliuGlobalFilter [Post] Filter")));
    }

    /*
         表示该过滤器的执行顺序，值越小，执行优先级越高
    */
    @Override
    public int getOrder() {
        return 0;
    }
}
