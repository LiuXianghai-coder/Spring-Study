package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplusdemo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 *@author lxh
 */
@Mapper
public interface UserInfoMapper
        extends BaseMapper<UserInfo> {
}
