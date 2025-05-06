package org.xhliu.springredission.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.xhliu.springredission.SpringRedissionApplicationTests;
import org.xhliu.springredission.rpo.RedisDataDeltaRpo;
import org.xhliu.springredission.service.impl.RedisDataService;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 *@author lxh
 */
@SpringBootTest(classes = SpringRedissionApplicationTests.class)
public class RedisDataServiceTest {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private RedisDataService redisDataService;

    @BeforeEach
    public void init() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Test
    public void incrementUpdateHashTest() {
        String hashKey = "incrementHashKey";
        final int keySize = 10_000;
        Map<Object, Object> rawData = Maps.newHashMap();
        for (int i = 0; i < keySize; i++) {
            rawData.put("key_" + i, "value_" + i);
        }
        redisTemplate.opsForHash().putAll(hashKey, rawData);

        Map<Object, Object> redisData = redisTemplate.opsForHash().entries(hashKey);
        Assertions.assertEquals(redisData.size(), rawData.size());
        for (Map.Entry<Object, Object> entry : rawData.entrySet()) {
            Assertions.assertEquals(entry.getValue(), redisData.get(entry.getKey()));
        }

        Map<String, String> updatedData = Maps.newHashMap();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int updatedStarted = random.nextInt(0, keySize);
        for (int i = updatedStarted, j = 0; i < keySize && j < 10_000; ++j, i++) {
            updatedData.put("key_" + i, "value_updated_" + i);
        }
        Set<String> deletedFields = Sets.newHashSet();
        for (int i = 0; i < updatedStarted; i++) {
            deletedFields.add("key_" + i);
        }

        RedisDataDeltaRpo<String, String> deltaRpo = RedisDataDeltaRpo.RedisDataDeltaRpoBuilder.
                aRedisDataDeltaRpo(String.class, String.class)
                .deltaData(updatedData).hashKey(hashKey)
                .deletedFields(deletedFields)
                .build();

        redisDataService.incrementUpdateHash(deltaRpo);
        redisData = redisTemplate.opsForHash().entries(hashKey);
        for (Map.Entry<String, String> entry : updatedData.entrySet()) {
            Assertions.assertEquals(entry.getValue(), redisData.get(entry.getKey()));
        }
        System.out.println(deletedFields.size());
        for (String field : deletedFields) {
            Assertions.assertFalse(redisData.containsKey(field));
        }

    }
}
