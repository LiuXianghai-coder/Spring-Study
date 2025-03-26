package com.example.demo.mapper;

import com.example.demo.entity.OaStatistic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 *@author lxh
 */
@Mapper
public interface OaStatisticMapper {

    @Insert(value = "INSERT INTO ${tableName} " +
            "(id, statistic_content, created_id, created_time) " +
            "VALUES(#{id}, #{statisticContent}, #{createdId}, #{createdTime})")
    int insertStatisticContent(OaStatistic statistic);
}
