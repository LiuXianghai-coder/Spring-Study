package org.xhliu.sharding.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xhliu.sharding.dto.SplitStatisticDto;
import org.xhliu.sharding.entity.OaStatistic;
import org.xhliu.sharding.rpo.SplitStatisticRpo;

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
