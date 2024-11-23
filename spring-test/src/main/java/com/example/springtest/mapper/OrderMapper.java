package com.example.springtest.mapper;

import com.example.springtest.entity.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 *@author lxh
 */
@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO order_info(id, user_id, goods_id, goods_cnt, created_tme) " +
            "VALUES (#{id}, #{userId}, #{goodsId}, #{goodsCnt}, #{createdTime})")
    int insertOrderInfo(OrderInfo orderInfo);

    @Select("SELECT * FROM order_info WHERE id = #{orderId}")
    OrderInfo queryById(Integer orderId);
}
