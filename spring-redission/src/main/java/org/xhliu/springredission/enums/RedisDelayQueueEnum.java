package org.xhliu.springredission.enums;

/**
 *@author lxh
 */
public enum RedisDelayQueueEnum {

    ORDER_PAYMENT_TIMEOUT("ORDER_PAYMENT_TIMEOUT","订单支付超时，自动取消订单", "orderPaymentTimeout"),
    ORDER_TIMEOUT_NOT_EVALUATED("ORDER_TIMEOUT_NOT_EVALUATED", "订单超时未评价，系统默认好评", "orderTimeoutNotEvaluated");

    /**
     * 延迟队列 Redis Key
     */
    public final String code;

    /**
     * 中文描述
     */
    public final String name;

    /**
     * 延迟队列具体业务实现的 Bean
     * 可通过 Spring 的上下文获取
     */
    public final String beanId;


    RedisDelayQueueEnum(String code, String name, String beanId) {
        this.code = code;
        this.name = name;
        this.beanId = beanId;
    }
}
