package org.xhliu.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author xhliu
 * @time 2022-03-09-下午7:50
 */
/*
    需要注意的是自定义的 Filter 的类名必须是以 “GatewayFilterFactory” 结尾，这是因为 Spring Cloud GateWay
    在进行解析是会去掉这个结尾部分，这也是 “约定优于配置” 的一个场景
*/
@Component
public class XhliuGatewayFilterFactory
        extends AbstractGatewayFilterFactory<XhliuGatewayFilterFactory.XhliuConfig> {

    private final static Logger log = LoggerFactory.getLogger(XhliuGatewayFilterFactory.class);

    public XhliuGatewayFilterFactory() {
        super(XhliuConfig.class);
    }

    @Override
    public GatewayFilter apply(XhliuConfig config) {
        return ((exchange, chain) -> {
            log.info("XhliuGateWayFilter [Pre] Filter Request, Config's name={}", config.getName());
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(
                            () -> {
                                log.info("XhliuGateWayFilter [Post] Response Filter");
                            }
                    ));
        });
    }

    /*
        该配置类的主要目的是获取在 yaml 配置文件中配置的属性信息
    */
    static class XhliuConfig {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
