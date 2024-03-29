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
import java.util.concurrent.ThreadLocalRandom;
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
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = random.nextInt(1, 1000000);
        if (!CollectionUtils.isEmpty(saleInfos)) {
            saleInfos.forEach(v -> v.setAmount(amount));
            data.addAll(saleInfos);
        }
    }

    @Test
    public void simpleUpdateTest() {
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE)) {
            SaleInfoMapper infoMapper = sqlSession.getMapper(SaleInfoMapper.class);
            for (SaleInfo saleInfo : data) {
                infoMapper.update(saleInfo);
            }
        }
        log.info("simpleUpdateTest take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void multipleQueriesUpdate() {
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE)) {
            SaleInfoMapper infoMapper = sqlSession.getMapper(SaleInfoMapper.class);
            List<SaleInfo> aux = new ArrayList<>();
            for (SaleInfo saleInfo : data) {
                aux.add(saleInfo);
                if (aux.size() >= 500) {
                    infoMapper.updateAll(aux);
                    aux.clear();
                }
            }
            if (!aux.isEmpty()) {
                infoMapper.updateAll(aux);
            }
        }
        log.info("multipleQueriesUpdate take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void insertUpdate() {
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE)) {
            SaleInfoMapper infoMapper = sqlSession.getMapper(SaleInfoMapper.class);
            List<SaleInfo> aux = new ArrayList<>();
            for (SaleInfo saleInfo : data) {
                aux.add(saleInfo);
                if (aux.size() >= 500) {
                    infoMapper.mysqlUpdateAll(aux);
                    aux.clear();
                }
            }
            if (!aux.isEmpty()) {
                infoMapper.mysqlUpdateAll(aux);
            }
        }
        log.info("insertUpdate take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void batchUpdateTest() {
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            SaleInfoMapper infoMapper = sqlSession.getMapper(SaleInfoMapper.class);
            int cnt = 0;
            for (SaleInfo saleInfo : data) {
                cnt++;
                infoMapper.update(saleInfo);
                if (cnt >= 100) {
                    sqlSession.flushStatements();
                    cnt = 0;
                }
            }
            sqlSession.flushStatements();
            log.info("batchUpdateTest take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }
}
