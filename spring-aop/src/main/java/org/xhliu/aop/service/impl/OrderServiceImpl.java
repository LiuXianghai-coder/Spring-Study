package org.xhliu.aop.service.impl;

import org.xhliu.aop.entity.Order;
import org.xhliu.aop.service.OrderService;

/**
 * @author xhliu2
 * @create 2021-11-10 16:46
 **/
public class OrderServiceImpl implements OrderService {
    private static Order order;

    public Order createOrder(String userName, String product) {
        order = new Order.Builder()
                    .userName(userName).product(product).build();
        return order;
    }

    public Order queryOrder(String userName) {
        return order;
    }
}
