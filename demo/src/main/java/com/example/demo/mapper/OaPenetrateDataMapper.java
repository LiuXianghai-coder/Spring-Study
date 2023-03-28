package com.example.demo.mapper;

import com.example.demo.entity.OaPenetrateData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaPenetrateDataMapper {
    int insertRows(@Param("rows") List<? extends OaPenetrateData> rows);

    List<String> selectAllPoolIds();
}
