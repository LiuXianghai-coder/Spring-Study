package com.example.demo.mapper;

import com.example.demo.entity.SaleInfo;
import com.google.common.base.Stopwatch;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lxh
 */
@SpringBootTest
public class BatchUpdateTest {

    private final static Logger log = LoggerFactory.getLogger(BatchUpdateTest.class);

    @Resource
    private ApplicationContext context;

    private final List<SaleInfo> data = new ArrayList<>();

    @BeforeEach
    void loadData() {
        SaleInfoMapper mapper = context.getBean(SaleInfoMapper.class);
        List<SaleInfo> saleInfos = mapper.sampleInfo();
        if (!CollectionUtils.isEmpty(saleInfos)) {
            saleInfos.forEach(v -> v.setAmount(31415926));
            data.addAll(saleInfos);
        }
    }

    @Test
    public void simpleUpdateTest() {
        SaleInfoMapper mapper = context.getBean(SaleInfoMapper.class);
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (SaleInfo saleInfo : data) {
            mapper.update(saleInfo);
        }
        log.info("simpleUpdateTest take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void batchUpdateTest() {
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            SaleInfoMapper infoMapper = sqlSession.getMapper(SaleInfoMapper.class);
            List<SaleInfo> aux = new ArrayList<>();
            for (SaleInfo saleInfo : data) {
                aux.add(saleInfo);
                infoMapper.update(saleInfo);
                if (aux.size() >= 500) {
                    sqlSession.flushStatements();
                    aux.clear();
                }
            }
            if (!aux.isEmpty()) sqlSession.flushStatements();
            log.info("batchUpdateTest take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }
}
