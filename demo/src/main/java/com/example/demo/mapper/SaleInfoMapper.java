package com.example.demo.mapper;

import com.example.demo.entity.SaleInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lxh
 */
@Mapper
public interface SaleInfoMapper extends ExtendsMapper<SaleInfo> {

    @Insert({
            "<script>",
            "INSERT INTO sale_info(id, sale_id, amount, year) VALUES ",
            "<foreach collection='param' item='item'  separator=','>",
            "                (#{item.id}, #{item.saleId}, #{item.amount}, #{item.year})\n",
            "            </foreach>",
            "</script>"
    })
    int insertAll(@Param("param") List<SaleInfo> saleInfoList);

    @Select({
            "<script>",
            "SELECT * FROM user_info ui JOIN sale_info si ON ui.user_name=si.sale_id",
            "</script>"
    })
    List<SaleInfo> selectSaleInfo();

    @Select({
            "<script>",
            "SELECT * FROM sale_info si WHERE sale_id BETWEEN 100 AND 10010",
            "</script>"
    })
    List<SaleInfo> sampleInfo();

    @Update({
            "<script>",
            "<foreach collection=\"data\" item=\"item\" separator=\";\">",
            "UPDATE sale_info SET id=#{item.id}, amount=#{item.amount}, " +
                    "year=#{item.year} WHERE id=#{item.id}",
            "</foreach>",
            "</script>"
    })
    int updateAll(@Param("data") List<? extends SaleInfo> data);

    @Update({
            "UPDATE sale_info SET id=#{item.id}, amount=#{item.amount}, " +
                    "year=#{item.year} WHERE id=#{item.id}",
    })
    int update(@Param("item") SaleInfo saleInfo);
}
