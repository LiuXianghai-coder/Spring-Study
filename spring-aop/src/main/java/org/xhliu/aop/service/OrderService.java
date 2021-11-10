package org.xhliu.aop.service;

import org.xhliu.aop.entity.Order;

public interface OrderService {
    Order createOrder(String userName, String product);

    Order queryOrder(String userName);
}
