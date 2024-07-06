package org.xhliu.springdata.config;

import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RedissonClient;
import org.redisson.command.CommandSyncService;
import org.redisson.config.Config;
import org.redisson.config.ConfigSupport;
import org.redisson.config.SingleServerConfig;
import org.redisson.connection.ConnectionManager;
import org.redisson.connection.SingleConnectionManager;
import org.redisson.liveobject.core.RedissonObjectBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.xhliu.springdata.redis.SimplePubSub;
import redis.clients.jedis.*;

import java.util.UUID;

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

    @Bean(name = "redisDistributeLock")
    public RedissonLock redissonLock(CommandSyncService syncService) {
        return new RedissonLock(syncService, "redis_distribute_lock");
    }

    @Bean(name = "commandSyncService")
    public CommandSyncService commandSyncService(@Qualifier("singleRedisServerConfig") Config config) {
        RedissonClient client = Redisson.create(config);
        SingleConnectionManager manager = new SingleConnectionManager(config.useSingleServer(), config, UUID.randomUUID());
        return new CommandSyncService(manager, new RedissonObjectBuilder(client));
    }

    @Bean("singleRedisServerConfig")
    public Config config() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return config;
    }
}
