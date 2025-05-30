package com.example.demo.mapper;

import com.example.demo.dto.SplitStatisticDto;
import com.example.demo.entity.OaStatistic;
import com.example.demo.rpo.SplitStatisticRpo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@author lxh
 */
@Mapper
public interface OaStatisticMapper {

    @Insert(value = "INSERT INTO ${tableName} " +
            "(id, statistic_content, created_id, created_time) " +
            "VALUES(#{id}, #{statisticContent}, #{createdId}, #{createdTime})")
    int insertStatisticContent(OaStatistic statistic);

    List<SplitStatisticDto> unionAllPageSearch(@Param("param") SplitStatisticRpo rpo);

    List<SplitStatisticDto> firstPageSearch(@Param("param") SplitStatisticRpo rpo);

    long countFirstPageSearch(@Param("param") SplitStatisticRpo rpo);

    List<SplitStatisticDto> secondPageSearch(@Param("param") SplitStatisticRpo rpo);
}
