package org.xhliu.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xhliu.mybatis.mapper.MessageMapper;
import org.xhliu.mybatis.mapper.UserMapper;
import org.xhliu.mybatis.utils.SqlSessionFactoryUtil;
import org.xhliu.mybatis.vo.Message;
import org.xhliu.mybatis.vo.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MybatisDemoApplication {

    private final static Logger log = LoggerFactory.getLogger(MybatisDemoApplication.class);

    public static void main(String[] args) {
//        SpringApplication.run(MybatisDemoApplication.class, args);
        try (
                SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ) {
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            Message message = messageMapper.getMessageById(1L);
            log.info("Single Param Get Message = {}", message);

            // 使用 java.util.Map 的方式传入对应的查询参数
            Map<String, Object> map = new HashMap<>();
            map.put("id", 2L);
            map.put("msg_id", "msg_1");
            Message messageByMap = messageMapper.getMessageByMap(map);
            log.info("Map Param Get Message = {}", message);

            // 使用 “构建者” 模式创建对应的参数实体对象
            Message params = Message.Builder.builder()
                    .id(1L)
                    .msgId("1")
                    .build();
            // 通过参数实体对象进行对应的数据查询
            Message messageByEntity = messageMapper.getMessageByEntity(params);
            log.info("Message Entity Param Get Message = {}", messageByEntity);

            // 插入 Message 记录
            Message newMessage = Message.Builder.builder()
                    .msgId("msg_4").deleted(0).status(1)
                    .content("New Message-4!!")
                    .createTime(LocalDateTime.now())
                    .build();
            int rows = messageMapper.insert(newMessage);
            log.info("save messages row = {}", rows);
            sqlSession.commit(); // 如果 sqlSession 没有开启自动提交，则需要手动提交来提交该事务

            // 首先需要查到对应的 Message
            Message messageById = messageMapper.getMessageById(2L);
            messageById.setContent("Update Content");
            int updated = messageMapper.update(messageById);
            log.info("update rows = {}", updated);
            sqlSession.commit(); // 视情况是否要手动提交事务

            message = Message.Builder.builder().id(4L).build(); // 构建者模式传入对应的参数
            int deleteNum = messageMapper.delete(message);
            log.info("delete rows={}", deleteNum);
            sqlSession.commit(); // 视情况是否要手动提交事务

            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUserById(1L);
            log.info("Get User = {}", user);
        }
    }

}
