package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplusdemo.entity.CourseInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 *@author lxh
 */
@Mapper
public interface CourseInfoMapper
        extends BaseMapper<CourseInfo> {
}
