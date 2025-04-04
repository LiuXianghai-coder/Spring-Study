package org.xhliu.springredission.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.xhliu.springredission.enums.RedisDelayQueueEnum;
import org.xhliu.springredission.util.RedisDelayQueueUtil;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *@author lxh
 */
@Component
public class RedisDelayQueueRunner
        implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(RedisDelayQueueRunner.class);


    @Resource
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Resource
    private ApplicationContext context;

    @Resource
    private ThreadPoolTaskExecutor ptask;

    ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 5,
            30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("order-delay-%d").build()
    );

    @Override
    public void run(String... args) throws Exception {
        ptask.execute(() -> {
            while (true) {
                try {
                    RedisDelayQueueEnum[] queueEnums = RedisDelayQueueEnum.values();
                    for (RedisDelayQueueEnum queueEnum : queueEnums) {
                        Object value = redisDelayQueueUtil.getDelayQueue(queueEnum.code);
                        if (value != null) {
                            RedisDelayQueueHandle<Object> redisDelayQueueHandle =
                                    (RedisDelayQueueHandle<Object>) context.getBean(queueEnum.beanId);
                            executorService.execute(() -> {
                                redisDelayQueueHandle.execute(value);
                            });
                        }
                    }
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    log.error("(Redission延迟队列监测异常中断) {}", e.getMessage());
                }
            }
        });
        log.info("(Redission延迟队列监测启动成功)");
    }
}
