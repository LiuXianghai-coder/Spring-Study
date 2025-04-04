package org.xhliu.springredission.task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xhliu.springredission.SpringRedissionApplicationTests;
import org.xhliu.springredission.enums.RedisDelayQueueEnum;
import org.xhliu.springredission.util.RedisDelayQueueUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 *@author lxh
 */
@SpringBootTest(classes = {SpringRedissionApplicationTests.class})
public class DelayedTaskTest {

    @Resource
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Test
    public void orderPayTaskTest() throws InterruptedException {
        final DelayQueue<OrderPayTask> delayQueue = new DelayQueue<>();
        Thread thread = new Thread(() -> {
            List<OrderPayTask> data = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                data.add(new OrderPayTask(String.format("task-%d", (i + 1)), (long) i, (i + 1) * 500L));
            }
            data.forEach(delayQueue::offer);
        });
        thread.start();

        while (thread.isAlive() || !delayQueue.isEmpty()) {
            OrderPayTask task = delayQueue.take();
            System.out.println(task);
        }
    }

    @Test
    public void redisDelayQueueTest() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("orderId", "100");
        map1.put("remark", "订单支付超时，自动取消订单");

        Map<String, String> map2 = new HashMap<>();
        map2.put("orderId", "200");
        map2.put("remark", "订单超时未评价，系统默认好评");

        // 添加订单支付超时，自动取消订单延迟队列。为了测试效果，延迟10秒钟
        redisDelayQueueUtil.addDelayQueue(map1, 10,
                TimeUnit.SECONDS, RedisDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.code);

        // 订单超时未评价，系统默认好评。为了测试效果，延迟20秒钟
        redisDelayQueueUtil.addDelayQueue(map2, 20,
                TimeUnit.SECONDS, RedisDelayQueueEnum.ORDER_TIMEOUT_NOT_EVALUATED.code);

        LockSupport.park();
    }
}
