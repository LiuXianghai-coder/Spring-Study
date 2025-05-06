package org.xhliu.springredission.service.impl;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.xhliu.springredission.rpo.RedisDataDeltaRpo;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *@author lxh
 */
@Service
public class RedisDataService {

    private final static Logger log = LoggerFactory.getLogger(RedisDataService.class);

    private final static int BATCH_SIZE = 500;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    public <F, V> void incrementUpdateHash(RedisDataDeltaRpo<F, V> deltaRpo) {
        Assert.notNull(deltaRpo, "请求参数对象不能为 null");
        String hashKey = deltaRpo.getHashKey();
        Assert.notNull(hashKey, "操作 hash 对象时，对应的 hashKey 不能为 null");

        Map<F, V> deltaData = deltaRpo.getDeltaData();
        Set<F> deletedFields = deltaRpo.getDeletedFields();

        deleteFieldKeys(hashKey, deletedFields);
        updateDeltaData(hashKey, deltaData);
    }

    public <F, V> void incrementUpdateHash(RedisDataDeltaRpo<F, V> deltaRpo, Comparator<F> fieldComp) {
        Assert.notNull(deltaRpo, "请求参数对象不能为 null");
        String hashKey = deltaRpo.getHashKey();
        Assert.notNull(hashKey, "操作 hash 对象时，对应的 hashKey 不能为 null");

        Map<F, V> deltaData = deltaRpo.getDeltaData();
        Set<F> deletedFields = deltaRpo.getDeletedFields();

        deleteFiledKeysByComp(hashKey, deletedFields, fieldComp);
        updateDeltaDataByComp(hashKey, deltaData, fieldComp);
    }

    private <F, V> void updateDeltaData(String hashKey, Map<F, V> deltaData) {
        if (CollectionUtils.isEmpty(deltaData)) {
            return;
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        Map<F, V> batchFieldMap = Maps.newHashMap();
        long updated = 0L;
        for (Map.Entry<F, V> entry : deltaData.entrySet()) {
            batchFieldMap.put(entry.getKey(), entry.getValue());
            if (batchFieldMap.size() >= BATCH_SIZE) {
                updated += updateBatchDeltaData(hashKey, batchFieldMap);
                batchFieldMap.clear();
            }
        }
        updated += updateBatchDeltaData(hashKey, batchFieldMap);
        log.info("hashKey: {}, updated: {}, takeTime: {} ms", hashKey,
                updated, stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private <F, V> long updateBatchDeltaData(String hashKey, Map<F, V> deltaData) {
        if (CollectionUtils.isEmpty(deltaData)) {
            return 0;
        }

        String updateScript = "local batch_size = 100\n" +
                "local success_count = 0\n" +
                "for i = 1, #ARGV, 3 * batch_size do\n" +
                "    local batch_end = math.min(i + 3 * batch_size - 1, #ARGV)\n" +
                "    for j = i, batch_end, 3 do\n" +
                "        local key = ARGV[j]\n" +
                "        local field = ARGV[j + 1]\n" +
                "        local value = ARGV[j + 2]\n" +
                "        local ok, err = pcall(redis.call, 'HSET', key, field, value)\n" +
                "        if ok then\n" +
                "            success_count = success_count + 1\n" +
                "        else\n" +
                "            redis.log(redis.LOG_WARNING, \"Error updating hash: \" .. tostring(err))\n" +
                "        end\n" +
                "    end\n" +
                "end\n" +
                "return success_count";
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(updateScript, Long.class);
        List<Object> params = Lists.newArrayList();
        for (Map.Entry<F, V> entry : deltaData.entrySet()) {
            params.add(hashKey);
            params.add(entry.getKey());
            params.add(entry.getValue());
        }
        Long ans = redisTemplate.execute(script, new ArrayList<>(), params.toArray());
        return ans == null ? 0 : ans;
    }

    private <F> void deleteFieldKeys(String hashKey, Set<F> deleteFields) {
        if (CollectionUtils.isEmpty(deleteFields)) {
            return;
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        long deleted = 0;
        Set<F> batchFieldSet = Sets.newHashSet();
        for (F field : deleteFields) {
            batchFieldSet.add(field);
            if (batchFieldSet.size() >= BATCH_SIZE) {
                deleted += deleteBatchFieldKeys(hashKey, batchFieldSet);
                batchFieldSet.clear();
            }
        }
        deleted += deleteBatchFieldKeys(hashKey, batchFieldSet);
        log.info("hashKey: {}, deleted: {}, takeTime: {} ms", hashKey,
                deleted, stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    private <F, V> void deleteFiledKeysByComp(String hashKey, Set<F> deleteFields, Comparator<F> fieldComp) {
        if (CollectionUtils.isEmpty(deleteFields)) {
            return;
        }
        Map<F, V> bathSacanMap = batchScanHash(hashKey, BATCH_SIZE);
        Set<F> deleteAbleFields = Sets.newHashSet();
        while (!CollectionUtils.isEmpty(bathSacanMap)) {
            for (Map.Entry<F, V> entry : bathSacanMap.entrySet()) {
                for (F field : deleteFields) {
                    if (fieldComp.compare(entry.getKey(), field) == 0) {
                        deleteAbleFields.add(field);
                        break;
                    }
                }
            }
        }
        deleteFieldKeys(hashKey, deleteAbleFields);
    }

    private <F, V> void updateDeltaDataByComp(String hashKey, Map<F, V> deltaData, Comparator<F> fieldComp) {
        if (CollectionUtils.isEmpty(deltaData)) {
            return;
        }
        Map<F, V> bathSacanMap = batchScanHash(hashKey, BATCH_SIZE);
        Map<F, V> updateData = Maps.newHashMap();
        while (!CollectionUtils.isEmpty(bathSacanMap)) {
            for (Map.Entry<F, V> entry : bathSacanMap.entrySet()) {
                for (Map.Entry<F, V> fvEntry : deltaData.entrySet()) {
                    if (fieldComp.compare(entry.getKey(), fvEntry.getKey()) == 0) {
                        updateData.put(entry.getKey(), fvEntry.getValue());
                    }
                }
            }
        }
        updateDeltaData(hashKey, updateData);
    }

    private <F> long deleteBatchFieldKeys(String hashKey, Set<F> deleteFields) {
        if (CollectionUtils.isEmpty(deleteFields)) {
            return 0;
        }
        return redisTemplate.opsForHash().delete(hashKey, deleteFields.toArray());
    }

    @SuppressWarnings("unchecked")
    protected <HK, HV> Map<HK, HV> batchScanHash(String hashKey, int count) {
        Map<HK, HV> resultMap = new HashMap<>();

        RedisTemplate<String, Object> fvRedisTemplate = cloneRedisTemplate();
        ScanOptions.ScanOptionsBuilder optionsBuilder = ScanOptions.scanOptions();
        optionsBuilder.count(count);

        // 使用 HSCAN 命令进行迭代扫描
        Cursor<Map.Entry<Object, Object>> cursor = fvRedisTemplate.opsForHash().scan(hashKey, optionsBuilder.build());
        try {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                resultMap.put((HK) entry.getKey(), (HV) entry.getValue());
            }
        } catch (Throwable t) {
            log.error("", t);
        } finally {
            try {
                cursor.close();
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return resultMap;
    }

    private RedisTemplate<String, Object> cloneRedisTemplate() {
        RedisTemplate<String, Object> fvRedisTemplate = new RedisTemplate<>();
        assert redisTemplate.getConnectionFactory() != null;
        fvRedisTemplate.setConnectionFactory(redisTemplate.getConnectionFactory());
        fvRedisTemplate.setKeySerializer(redisTemplate.getKeySerializer());
        fvRedisTemplate.setValueSerializer(redisTemplate.getValueSerializer());
        fvRedisTemplate.setHashKeySerializer(redisTemplate.getHashKeySerializer());
        fvRedisTemplate.setHashValueSerializer(redisTemplate.getHashValueSerializer());
        fvRedisTemplate.afterPropertiesSet();
        return fvRedisTemplate;
    }
}
