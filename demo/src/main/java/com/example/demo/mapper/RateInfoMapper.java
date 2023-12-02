package com.example.demo.mapper;

import com.example.demo.entity.RateInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author xhliu
 **/
public interface RateInfoMapper
        extends Mapper<RateInfo> {
    RateInfo selectById(@Param("id") long id);

    int update(RateInfo rateInfo);
}
