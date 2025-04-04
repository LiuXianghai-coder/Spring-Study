package org.xhliu.springredission.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xhliu.springredission.service.RedisDelayQueueHandle;

import javax.annotation.Resource;
import java.util.Map;

/**
 *@author lxh
 */
@Service
public class OrderTimeoutNotEvaluated
        implements RedisDelayQueueHandle<Map<Object, Object>> {

    private final static Logger log = LoggerFactory.getLogger(OrderTimeoutNotEvaluated.class);

    @Resource
    private ObjectMapper mapper;

    @Override
    public void execute(Map<Object, Object> messageMap) {
        if (log.isInfoEnabled()) {
            try {
                log.info("(收到订单支付超时延迟消息) {}", mapper.writeValueAsString(messageMap));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
