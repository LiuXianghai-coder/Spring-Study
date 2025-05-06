package org.xhliu.springredission.rpo;

import java.util.Map;
import java.util.Set;

/**
 * 用于存储差异信息的 RPO 信息对象
 *
 *@author lxh
 */
public class RedisDataDeltaRpo<F, V> {

    private final String hashKey;

    private final Map<F, V> deltaData;

    private final Set<F> deletedFields;

    private RedisDataDeltaRpo(String hashKey, Map<F, V> deltaData,
                              Set<F> deletedFields) {
        this.hashKey = hashKey;
        this.deltaData = deltaData;
        this.deletedFields = deletedFields;
    }

    public Map<F, V> getDeltaData() {
        return deltaData;
    }

    public String getHashKey() {
        return hashKey;
    }

    public Set<F> getDeletedFields() {
        return deletedFields;
    }

    public static final class RedisDataDeltaRpoBuilder<S, T> {
        private String hashKey;
        private Map<S, T> deltaData;
        private Set<S> deletedFields;

        private RedisDataDeltaRpoBuilder() {
        }

        public static <S, T> RedisDataDeltaRpoBuilder<S, T> aRedisDataDeltaRpo(Class<S> sClazz, Class<T> tClazz) {
            return new RedisDataDeltaRpoBuilder<>();
        }

        public RedisDataDeltaRpoBuilder<S, T> hashKey(String hashKey) {
            this.hashKey = hashKey;
            return this;
        }

        public RedisDataDeltaRpoBuilder<S, T> deltaData(Map<S, T> deltaData) {
            this.deltaData = deltaData;
            return this;
        }

        public RedisDataDeltaRpoBuilder<S, T> deletedFields(Set<S> deletedFields) {
            this.deletedFields = deletedFields;
            return this;
        }

        public RedisDataDeltaRpo<S, T> build() {
            return new RedisDataDeltaRpo<>(hashKey, deltaData, deletedFields);
        }
    }
}
