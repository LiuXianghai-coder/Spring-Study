package com.example.demo.service;

import com.example.demo.entity.GoodsStockInfo;
import com.example.demo.mapper.GoodsStockInfoMapper;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 *@author lxh
 */
@Service
@Transactional
public class FlashSaleService {

    private final static Logger log = LoggerFactory.getLogger(FlashSaleService.class);

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private GoodsStockInfoMapper stockInfoMapper;

    @Resource
    private PlatformTransactionManager txManager;

    public int loadStockInfo(Long goodsId) {
        Assert.notNull(goodsId, "商品 Id 不能为 null");

        GoodsStockInfo goodsStockInfo = stockInfoMapper.selectGoodsStock(goodsId);
        RAtomicLong atomicLong = redissonClient.getAtomicLong(String.valueOf(goodsId));
        atomicLong.set(goodsStockInfo.getStock());
        return 1;
    }

    public int flashSale(Long goodsId) {
        Assert.notNull(goodsId, "商品 Id 不能为 null");

        RAtomicLong atomicLong = redissonClient.getAtomicLong(String.valueOf(goodsId));
        long reserveStock = atomicLong.getAndDecrement();
        if (reserveStock <= 0) {
            return -1;
        }

        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        try {
//            stockInfoMapper.decrementGoodsStock(goodsId);
            stockInfoMapper.updateStockInfo(goodsId.intValue(), (int) reserveStock - 1);
            txManager.commit(status);
        } catch (Throwable t) {
            log.error("", t);
            txManager.rollback(status);
            atomicLong.getAndIncrement();
            return (int) reserveStock;
        }
        return (int) reserveStock - 1;
    }
}
