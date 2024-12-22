package org.xhliu.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xhliu.mybatis.vo.UserContact;

/**
 * @author xhliu
 * @time 2022-03-02-下午10:02
 */
@Mapper
public interface UserContactMapper {
    UserContact getUserContactByUserId(@Param("userId") long userId);
}
