package com.example.demo.tools;

import com.example.demo.entity.OaPenetrateData;
import com.example.demo.entity.OaPenetrateTask;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoView;
import com.example.demo.mapper.OaPenetrateDataMapper;
import com.example.demo.mapper.OaPenetrateTaskMapper;
import com.example.demo.mapper.RateInfoMapper;
import com.example.demo.mapper.UserInfoMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lxh
 * @date 2022/6/6-下午10:22
 */
public class MyBatisTool {
    private static class Holder {
        private final static SqlSessionFactory sqlSessionFactory;

        static {
            InputStream in = null;
            try {
                /*
                	参考 Maven 项目结构，Resources 会从 resource 目录下加载对应的资源
                	通过 getResourceAsStream() 方法读取对应的配置文件，将它读入到输入流中
                */
                in = Resources.getResourceAsStream("mybatis-config.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 使用构建者模式从输入流中创建一个新的 SqlSessionFactory 对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        }

        // 现在，通过该方法获取的 SqlSessionFactory 对象就是单例的了
        public static SqlSessionFactory getSqlSessionFactory() {
            return sqlSessionFactory;
        }
    }

    public static SqlSession openSqlSession() {
        return Holder.getSqlSessionFactory().openSession();
    }

    public static SqlSession openSqlSession(boolean autoCommit) {
        return Holder.getSqlSessionFactory().openSession(autoCommit);
    }

    public static void main(String[] args) {
        try (
                SqlSession sqlSession = openSqlSession()
        ) {
            UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
            List<UserInfoView> views = mapper.selectViewsById(1L);
            for (UserInfoView view : views) {
                System.out.println(view.getFriends());
            }

        }
    }
}
