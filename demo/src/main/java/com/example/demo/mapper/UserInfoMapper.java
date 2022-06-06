package com.example.demo.mapper;

import com.example.demo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author lxh
 * @date 2022/6/6-下午10:26
 */
public interface UserInfoMapper {
    UserInfo getUserById(@Param("id") long id);
}
