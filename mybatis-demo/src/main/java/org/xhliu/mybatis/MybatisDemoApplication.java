package org.xhliu.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.xhliu.mybatis.mapper.UserMapper;
import org.xhliu.mybatis.vo.User;

@SpringBootApplication
@MapperScan("org.xhliu.mybatis.mapper")
public class MybatisDemoApplication {

    private final static Logger log = LoggerFactory.getLogger(MybatisDemoApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MybatisDemoApplication.class, args);
        UserMapper userMapper = context.getBean(UserMapper.class);
        User user = userMapper.getUserById(2L);
        log.info("{}", user);
    }

}
