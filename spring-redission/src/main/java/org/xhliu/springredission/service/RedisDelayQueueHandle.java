package org.xhliu.springredission.service;

/**
 *@author lxh
 */
public interface RedisDelayQueueHandle<T> {

    void execute(T t);
}
