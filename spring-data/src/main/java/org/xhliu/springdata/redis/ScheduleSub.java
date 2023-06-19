package org.xhliu.springdata.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定期从指定的频道订阅一次消息
 *
 * @author lxh
 */
@Service
public class ScheduleSub {

    private final static Logger log = LoggerFactory.getLogger(ScheduleSub.class);

    private final ScheduledThreadPoolExecutor executor;

    private final JedisPool jedisPool;

    private final BinaryJedisPubSub sub;

    @Autowired
    public ScheduleSub(JedisPool pool,
                       BinaryJedisPubSub subscribe,
                       Topic topic) {
        this.jedisPool = pool;
        this.sub = subscribe;
        executor = new ScheduledThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() - 1
        );
        executor.scheduleWithFixedDelay(() -> {
            Jedis jedis = pool.getResource();
            jedis.subscribe(subscribe, topic.getTopic().getBytes());
        }, 5, 3, TimeUnit.SECONDS);
    }

    public void scheduleSub(Topic topic) {
        executor.scheduleWithFixedDelay(() -> {
            Jedis jedis = jedisPool.getResource();
            jedis.subscribe(sub, topic.getTopic().getBytes());
        }, 10, 10, TimeUnit.SECONDS);
    }
}
