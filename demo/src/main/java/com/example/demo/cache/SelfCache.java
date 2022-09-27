package com.example.demo.cache;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lxh
 */
public class SelfCache implements Cache {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private String id;

    private Map<Object, Object> cache = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(SelfCache.class);

    @Override
    public String getId() {
        return id;
    }

    public SelfCache(String id) {
        this.id = id;
    }

    @Override
    public void putObject(Object key, Object value) {
        log.info("selfCache put cache");
        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        log.info("selfCache get cache");
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        log.info("selfCache remove cache");
        return cache.remove(key);
    }

    @Override
    public void clear() {
        log.info("selfCache clear cache");
        cache.clear();
    }

    @Override
    public int getSize() {
        log.info("selfCache get cache size: " + cache.size());
        return cache.size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return lock;
    }
}
