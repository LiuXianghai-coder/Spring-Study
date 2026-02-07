package com.example.integration.autoconfigure;

import com.alibaba.cloud.stream.binder.rocketmq.RocketMQMessageChannelBinder;
import com.alibaba.cloud.stream.binder.rocketmq.properties.RocketMQBinderConfigurationProperties;
import com.alibaba.cloud.stream.binder.rocketmq.properties.RocketMQExtendedBindingProperties;
import com.alibaba.cloud.stream.binder.rocketmq.provisioning.RocketMQTopicProvisioner;
import com.example.integration.binder.RocketMQChannelBinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ RocketMQExtendedBindingProperties.class,
        RocketMQBinderConfigurationProperties.class })
public class RocketMQBinderAutoConfiguration {

    @Resource
    private RocketMQExtendedBindingProperties extendedBindingProperties;

    @Resource
    private RocketMQBinderConfigurationProperties rocketBinderConfigurationProperties;

    @Bean
    public RocketMQTopicProvisioner rocketMQTopicProvisioner() {
        return new RocketMQTopicProvisioner();
    }

    @Bean
    @Primary
    public RocketMQMessageChannelBinder rocketMQMessageChannelBinder(
            RocketMQTopicProvisioner provisioningProvider) {
        return new RocketMQChannelBinder(rocketBinderConfigurationProperties,
                extendedBindingProperties, provisioningProvider);
    }
}
