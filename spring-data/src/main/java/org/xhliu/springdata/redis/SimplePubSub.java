package org.xhliu.springdata.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;

/**
 * @author lxh
 */
public class SimplePubSub
        extends BinaryJedisPubSub {

    private final static Logger log = LoggerFactory.getLogger(SimplePubSub.class);

    @Override
    public void onMessage(byte[] channel, byte[] message) {
        String channelName = new String(channel);
        String msg = new String(message);
        log.info("Client Receiver From {} message {}", channelName, msg);
    }

    @Override
    public void onSubscribe(byte[] channel, int subscribedChannels) {
        String channelName = new String(channel);
        log.info("Client Subscribe on {}", channelName);
    }
}
