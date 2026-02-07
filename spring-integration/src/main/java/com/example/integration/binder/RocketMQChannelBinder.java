package com.example.integration.binder;

import com.alibaba.cloud.stream.binder.rocketmq.RocketMQMessageChannelBinder;
import com.alibaba.cloud.stream.binder.rocketmq.integration.inbound.RocketMQInboundChannelAdapter;
import com.alibaba.cloud.stream.binder.rocketmq.properties.RocketMQBinderConfigurationProperties;
import com.alibaba.cloud.stream.binder.rocketmq.properties.RocketMQConsumerProperties;
import com.alibaba.cloud.stream.binder.rocketmq.properties.RocketMQExtendedBindingProperties;
import com.alibaba.cloud.stream.binder.rocketmq.provisioning.RocketMQTopicProvisioner;
import com.alibaba.cloud.stream.binder.rocketmq.utils.RocketMQUtils;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.util.StringUtils;

/**
 *@author lxh
 */
public class RocketMQChannelBinder extends RocketMQMessageChannelBinder {

    final RocketMQBinderConfigurationProperties binderConfigurationProperties;

    public RocketMQChannelBinder(RocketMQBinderConfigurationProperties binderConfigurationProperties,
                                 RocketMQExtendedBindingProperties extendedBindingProperties,
                                 RocketMQTopicProvisioner provisioningProvider) {
        super(binderConfigurationProperties, extendedBindingProperties, provisioningProvider);
        this.binderConfigurationProperties = binderConfigurationProperties;
    }

    @Override
    protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group,
                                                     ExtendedConsumerProperties<RocketMQConsumerProperties> extendedConsumerProperties
    ) {
        if (!StringUtils.hasLength(group)) {
            throw new RuntimeException(
                    "'group must be configured for channel " + destination.getName());
        }
        RocketMQUtils.mergeRocketMQProperties(this.binderConfigurationProperties,
                extendedConsumerProperties.getExtension());
        extendedConsumerProperties.getExtension().setGroup(group);

        RocketMQInboundChannelAdapter inboundChannelAdapter = new ExtendsRocketMQInboundChannelAdapter(
                destination.getName(), extendedConsumerProperties);
        ErrorInfrastructure errorInfrastructure = registerErrorInfrastructure(destination,
                group, extendedConsumerProperties);
        if (extendedConsumerProperties.getMaxAttempts() > 1) {
            inboundChannelAdapter
                    .setRetryTemplate(buildRetryTemplate(extendedConsumerProperties));
            inboundChannelAdapter.setRecoveryCallback(errorInfrastructure.getRecoverer());
        }
        else {
            inboundChannelAdapter.setErrorChannel(errorInfrastructure.getErrorChannel());
        }
        return inboundChannelAdapter;
    }
}
