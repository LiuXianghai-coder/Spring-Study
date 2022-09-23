package com.example.demo.tools;

import com.example.demo.entity.RateInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.RateInfoMapper;
import com.example.demo.mapper.UserInfoMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

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

    public static void main(String[] args) {
        try (
                SqlSession sqlSession = openSqlSession()
        ) {
           RateInfoMapper mapper = sqlSession.getMapper(RateInfoMapper.class);
           RateInfo info = new RateInfo();
           info.setId(1);
           info.setRateName("浮动利率");
           info.setRateVal("2.00");
           info.initFiled();
           mapper.update(info);
           sqlSession.commit();
        }
    }
}
