package org.xhliu.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.xhliu.mybatis.vo.User;

/**
 * @author xhliu
 * @time 2022-03-02-下午9:05
 */

// 只需要定义方法，别的交给 Mybatis
public interface UserMapper {
    User getUserById(@Param("id") Long userId);
}

