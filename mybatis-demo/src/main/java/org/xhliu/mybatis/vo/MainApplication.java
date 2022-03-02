package org.xhliu.mybatis.vo;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhliu.mybatis.MybatisDemoApplication;
import org.xhliu.mybatis.mapper.MessageMapper;
import org.xhliu.mybatis.utils.SqlSessionFactoryUtil;

/**
 * @author xhliu
 * @time 2022-03-02-下午10:06
 */
public class MainApplication {
    private final static Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        try (
                SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ) {
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            Message message = messageMapper.getMessageById(1L);
            log.info("Single Param Get Message = {}", message);
        }
    }
}
