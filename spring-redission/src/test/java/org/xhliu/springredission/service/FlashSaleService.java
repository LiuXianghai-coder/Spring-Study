package org.xhliu.springredission.service;

import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@Service
public class FlashSaleService {

    private final static Logger log = LoggerFactory.getLogger(FlashSaleService.class);

    @Resource
    private RedissonClient redissonClient;

    public int flashSale(Integer goodsId) {
        return -1;
    }
}
