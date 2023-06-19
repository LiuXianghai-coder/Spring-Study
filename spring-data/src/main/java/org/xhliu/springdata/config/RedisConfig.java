package org.xhliu.springdata.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.xhliu.springdata.redis.SimplePubSub;
import redis.clients.jedis.*;

@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration(exclude = {
        JmxAutoConfiguration.class
})
public class RedisConfig {

    @Bean
    public JedisClientConfig jedisClientConfig() {
        return DefaultJedisClientConfig.builder()
                .build();
    }

    @Bean
    public JedisPool jedisPool(JedisClientConfig config) {
        return new JedisPool(new HostAndPort("127.0.0.1", 6379), config);
    }

    @Bean
    public BinaryJedisPubSub jedisPubSub() {
        return new SimplePubSub();
    }

    @Bean
    public Topic redisTopic() {
        return new ChannelTopic("item-1");
    }
}
