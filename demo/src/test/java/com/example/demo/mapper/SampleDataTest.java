package com.example.demo.mapper;

import com.example.demo.entity.BigColsSchema;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lxh
 */
@SpringBootTest
public class SampleDataTest {

    private final static Logger log = LoggerFactory.getLogger(SampleDataTest.class);

    @Resource
    private ApplicationContext context;

    @Test
    public void bigColsData() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            BigColsSchemaMapper mapper = sqlSession.getMapper(BigColsSchemaMapper.class);
            List<BigColsSchema> data = Lists.newArrayList();
            for (int i = 0; i < 100000; ++i) {
                data.add(BigColsSchema.sample());
                if (data.size() >= 500) {
                    mapper.saveAll(data);
                    data.clear();
                }
            }
            if (!data.isEmpty()) {
                mapper.saveAll(data);
                data.clear();
            }
            log.info("Generate data take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }
}
