package com.example.integration.binder;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.cloud.stream.binder.rocketmq.integration.inbound.RocketMQInboundChannelAdapter;
import com.alibaba.cloud.stream.binder.rocketmq.properties.RocketMQConsumerProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;

/**
 *@author lxh
 */
public class ExtendsRocketMQInboundChannelAdapter
        extends RocketMQInboundChannelAdapter {

    private final ExtendedConsumerProperties<RocketMQConsumerProperties> extendedConsumerProperties;

    public ExtendsRocketMQInboundChannelAdapter(String topic,
                                                ExtendedConsumerProperties<RocketMQConsumerProperties> extendedConsumerProperties) {
        super(topic, extendedConsumerProperties);
        this.extendedConsumerProperties = extendedConsumerProperties;
    }

    @Override
    protected void onInit() {
        super.onInit();
        DefaultMQPushConsumer consumer =
                (DefaultMQPushConsumer) ReflectUtil.getFieldValue(this, "pushConsumer");
        RocketMQConsumerProperties consumerProperties = extendedConsumerProperties.getExtension();
        consumer.setPullThresholdForQueue(consumerProperties.getPullThresholdForQueue());
        consumer.setPullThresholdSizeForQueue(consumerProperties.getPullThresholdSizeForQueue());
        consumer.setPullBatchSize(consumerProperties.getPullBatchSize());
    }
}
