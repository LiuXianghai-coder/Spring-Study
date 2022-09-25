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

    public static void main(String[] args) {
        try (
                // 打开两个 SqlSession，使得具备两个 Session 上下文
                SqlSession sqlSession1 = openSqlSession();
                SqlSession sqlSession2 = openSqlSession()
        ) {
            RateInfoMapper mapper1 = sqlSession1.getMapper(RateInfoMapper.class);
            RateInfoMapper mapper2 = sqlSession2.getMapper(RateInfoMapper.class);

            // 两个 Session 分别执行第一次查询
            RateInfo info1 = mapper1.selectById(1L);
            RateInfo info2 = mapper2.selectById(1L);
            System.out.println("info1: " + info1);
            System.out.println("info2: " + info2);

            // session1 执行数据的更新操作
            RateInfo data = new RateInfo();
            data.setId(1L);
            data.setRateName("LPR");
            data.setRateVal(new BigDecimal("3.141"));
            mapper1.update(data);
            sqlSession1.commit();
            System.out.println("sqlSession1 完成数据的更新");

            // 更新完成之后两个 Session 再分别执行一次数据的查询
            info1 = mapper1.selectById(1L);
            info2 = mapper2.selectById(1L);
            System.out.println("info1: " + info1);
            System.out.println("info2: " + info2);
        }
    }
}
