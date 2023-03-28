package com.example.demo.mapper;

import com.example.demo.entity.OaPenetrateTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaPenetrateTaskMapper {
    int insertRows(@Param("rows") List<? extends OaPenetrateTask> rows);
}
