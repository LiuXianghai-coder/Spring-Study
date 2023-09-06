package com.example.demo.mapper;

import com.example.demo.entity.SaleInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@author lxh
 */
@Mapper
public interface SaleInfoMapper {

    @Insert({
            "<script>",
            "INSERT INTO sale_info(id, sale_id, amount, year) VALUES ",
            "<foreach collection='param' item='item'  separator=','>",
            "                (#{item.id}, #{item.saleId}, #{item.amount}, #{item.year})\n",
            "            </foreach>",
            "</script>"
    })
    int insertAll(@Param("param") List<SaleInfo> saleInfoList);
}
