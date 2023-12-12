package com.example.demo;

import com.example.demo.entity.SaleInfo;
import com.example.demo.mapper.SaleInfoMapper;
import com.example.demo.transaction.DataSourceHolder;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@tk.mybatis.spring.annotation.MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import({MybatisAutoConfiguration.class})
public class DemoApplication {

    private final static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SqlSession sqlSession = context.getBean(SqlSession.class);
        SaleInfoMapper mapper = context.getBean(SaleInfoMapper.class);
        Example example = Example.builder(SaleInfo.class)
                .andWhere(Sqls.custom().andBetween("id", 50001L, 50009L))
                .build();
        DataSourceHolder.setCurDataSource("mysql");
        List<SaleInfo> data = mapper.selectByExample(example);
        mapper.mysqlUpdateAll(data);

        DataSourceHolder.setCurDataSource("postgresql");
        data = mapper.selectByExample(example);
        mapper.psqlUpdateAll(data);
    }

    static String randomName(ThreadLocalRandom random) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; ++i) {
            int r = random.nextInt(0, 26);
            sb.append((char) ('a' + r));
            if (r > 20 && sb.length() > 3) break;
        }
        return sb.toString();
    }
}
