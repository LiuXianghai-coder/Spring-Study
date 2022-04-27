package org.xhliu.sentinel.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.http.codec.HttpMessageWriter;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author xhliu
 * @time 2022-03-09-下午10:24
 */
public class XhliuSentinelGatewayBlockExceptionHandler implements WebExceptionHandler {
    private final List<ViewResolver> viewResolvers;
    private final List<HttpMessageWriter<?>> messageWriters;
    private final Supplier<ServerResponse.Context> contextSupplier = () -> new ServerResponse.Context() {
        public List<HttpMessageWriter<?>> messageWriters() {
            return XhliuSentinelGatewayBlockExceptionHandler.this.messageWriters;
        }

        public List<ViewResolver> viewResolvers() {
            return XhliuSentinelGatewayBlockExceptionHandler.this.viewResolvers;
        }
    };

    public XhliuSentinelGatewayBlockExceptionHandler(
            List<ViewResolver> viewResolvers,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        this.viewResolvers = viewResolvers;
        this.messageWriters = serverCodecConfigurer.getWriters();
    }

    /*
        自定义封装的限流消息
     */
    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.getHeaders().add("Content-type", "application/json;charset=UTF-8");
        byte[] datas = "{\"code\":999,\"msg\":\"访问人数过多\"}".getBytes();
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(datas);
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        } else {
            return !BlockException
                    .isBlockException(ex) ? Mono.error(ex)
                    : this.handleBlockedRequest(exchange, ex).flatMap((response) -> {
                return this.writeResponse(response, exchange);
            });
        }
    }

    private Mono<ServerResponse> handleBlockedRequest(
            ServerWebExchange exchange,
            Throwable throwable
    ) {
        return GatewayCallbackManager.getBlockHandler()
                .handleRequest(exchange, throwable);
    }
}
