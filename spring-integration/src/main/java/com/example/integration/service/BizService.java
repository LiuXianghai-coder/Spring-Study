package com.example.integration.service;

import cn.hutool.core.thread.ThreadUtil;
import com.example.integration.processor.StreamProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 *@author lxh
 */
@Service
public class BizService {

    private final static Logger logger = LoggerFactory.getLogger(BizService.class);

    @Resource
    private StreamProcessor streamProcessor;

    @StreamListener(StreamProcessor.INPUT)
    public void handleMessage(@Payload String message) {
        logger.info("Received message length: {}", message.length());
        ThreadUtil.safeSleep(TimeUnit.SECONDS.toMillis(5));
        logger.info("msg consumer finished.......");
    }

    public void sendMessage(String message) {
        streamProcessor.output().send(MessageBuilder.withPayload(message).build());
    }
}
