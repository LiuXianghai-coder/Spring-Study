package org.xhliu.consumer.beans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author xhliu
 * @create 2022-02-24-15:00
 **/
@Component
public class ConsumerBeanConfig {
    @Bean
    @LoadBalanced  // 通过 @LoadBalanced 注解开启负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
