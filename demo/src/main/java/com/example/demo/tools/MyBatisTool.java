package com.example.demo.tools;

import com.example.demo.entity.RateInfo;
import com.example.demo.mapper.RateInfoMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * @author lxh
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
                SqlSession sqlSession1 = openSqlSession();
                SqlSession sqlSession2 = openSqlSession()
        ) {
            RateInfoMapper mapper1 = sqlSession1.getMapper(RateInfoMapper.class);
            RateInfoMapper mapper2 = sqlSession2.getMapper(RateInfoMapper.class);

            System.out.println("session1 第一次查询：" + mapper1.selectById(1L));
            sqlSession1.commit();
            System.out.println("session1 提交事务");
            System.out.println("session2 第一次查询：" + mapper2.selectById(1L));
            System.out.println("session1 第二次查询：" + mapper1.selectById(1L));
            System.out.println("session2 第二次查询：" + mapper2.selectById(1L));
        }
    }
}
