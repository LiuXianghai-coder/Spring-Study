package com.example.springtest.service;

import com.example.springtest.entity.OrderInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@Service
public class GoodsService {

    public boolean updateGoodsStoreInfo(int goodsId, int cnt) {
        return true;
    }

    public boolean sendNotify(int userId) {
        return true;
    }
}
