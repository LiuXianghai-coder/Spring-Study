package com.example.demo.mapper;

import com.example.demo.entity.RateInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author xhliu
 * @create 2022-07-14-15:34
 **/
public interface RateInfoMapper {
    RateInfo selectById(@Param("id") long id);

    RateInfo selectById(@Param("id") long id, @Param("name") String name);

    int insert(RateInfo rateInfo);
}
