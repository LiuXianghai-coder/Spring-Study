package org.xhliu.sentinel.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebExceptionHandler;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xhliu
 * @time 2022-03-09-下午9:21
 */
@Configuration
public class GateWayConfiguration {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    @Autowired
    public GateWayConfiguration(
            ObjectProvider<List<ViewResolver>> viewResolvers,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        this.viewResolvers = viewResolvers.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    // 全局限流过滤器SentinelGatewayFilter
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    // 限流异常处理器
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebExceptionHandler
    sentinelGatewayBlockExceptionHandler() {
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);

        /*
            自定义异常处理
         */
        return new XhliuSentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    // 初始化限流规则
    @PostConstruct
    public void doInit() {
        initCustomizedApis();
        initGatewayRules();
    }

    /**
     * 自定义API分组限流，将/foo/**和/bar/**进行统一分组，并提供 name=first_customized_api，
     * 然后在初始化网关限流规则时，针对该name设置限流规则。
     * 同时，我们可以通过setMatchStrategy来设置不同 path下的限流参数策略
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition apiDefinition = new ApiDefinition("first_customized_api");
        apiDefinition.setPredicateItems(new HashSet<>() {
            {
                add(new ApiPathPredicateItem().setPattern("/foo/**")
                        .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                add(new ApiPathPredicateItem().setPattern("/bar/**")
                        .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
            }
        });
        definitions.add(apiDefinition);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        GatewayFlowRule gatewayFlowRule =
                new GatewayFlowRule("gateway-nacos-provider")
                        .setCount(1).setIntervalSec(1);
        GatewayFlowRule gatewayFlowRule1 =
                new GatewayFlowRule("first_customized_api")
                        .setCount(1).setIntervalSec(1);

        rules.add(gatewayFlowRule);
        rules.add(gatewayFlowRule1);
        GatewayRuleManager.loadRules(rules);
    }
}
