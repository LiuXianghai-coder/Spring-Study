package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplusdemo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@author lxh
 */
@Mapper
public interface UserInfoMapper
        extends BaseMapper<UserInfo> {

    UserInfo selectUserInfo(@Param("param") UserInfo param);
}
