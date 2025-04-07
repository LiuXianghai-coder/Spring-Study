package com.example.demo.mapper;

import com.example.demo.entity.GoodsStockInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@author lxh
 */
@Mapper
public interface GoodsStockInfoMapper {

    GoodsStockInfo selectGoodsStock(@Param("goodsId") Long goodsId);

    int decrementGoodsStock(@Param("goodsId") Long goodsId);

    int updateStockInfo(@Param("goodsId") Integer goodsId, @Param("stock") Integer stock);
}
