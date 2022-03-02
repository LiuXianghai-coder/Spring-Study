package org.xhliu.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.xhliu.mybatis.vo.MessageDetail;

/**
 * @author xhliu
 * @time 2022-03-02-下午9:56
 */
public interface MessageDetailMapper {
    MessageDetail getMessageByMsgId(@Param("msgId") String msgId);
}
