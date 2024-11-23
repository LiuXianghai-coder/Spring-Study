package com.example.springtest.service;

import com.example.springtest.entity.OrderInfo;
import com.example.springtest.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

/**
 *@author lxh
 */
@Service
public class OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private OrderMapper orderMapper;

    private GoodsService goodsService;

    @Transactional
    public boolean createOrder(OrderInfo orderInfo) {
        log.info("准备创建订单，对应的订单信息=[{}]", orderInfo);
        int orderId = new BigInteger(UUID.randomUUID().toString()
                .replaceAll("-", "").getBytes())
                .mod(BigInteger.valueOf(Integer.MAX_VALUE - 1))
                .intValue();
        orderInfo.setId(orderId);
        orderInfo.setCreatedTime(new Date());
        orderMapper.insertOrderInfo(orderInfo);

        boolean updated = goodsService.updateGoodsStoreInfo(
                orderInfo.getGoodsId(), orderInfo.getGoodsCnt());// 更新库存信息
        if (!updated) {
            throw new RuntimeException("库存信息更新失败");
        }
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            goodsService.sendNotify(orderInfo.getUserId());
        }).start();
        return Boolean.TRUE;
    }

    @Resource
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }
}
