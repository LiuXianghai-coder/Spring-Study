package org.xhliu.mybatis;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.xhliu.mybatis.mapper.StoreSnapshotMapper;
import org.xhliu.mybatis.vo.StoreSnapshot;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MybatisDemoApplication.class)
public class MybatisDemoApplicationTest {

    private final static Logger logger = LoggerFactory.getLogger(MybatisDemoApplicationTest.class);

    @Resource
    private PlatformTransactionManager txManager;

    @Resource
    private StoreSnapshotMapper storeSnapshotMapper;

    @Test
    public void multipleBenchTest() {
        Thread[] threads = new Thread[10];
        final CountDownLatch  countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                batchCreateUserInfo(100);
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        countDownLatch.countDown();
    }

    protected int batchCreateUserInfo(int batchSize) {
        TransactionStatus txStatus = txManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<StoreSnapshot> snapshotList = new ArrayList<>(batchSize);
            for (int i = 0; i < batchSize; i++) {
                StoreSnapshot snapshot = new StoreSnapshot();
                snapshot.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                snapshot.setSnapDate(RandomUtil.randomDate(new DateTime(), DateField.DAY_OF_YEAR, 3650, 365));
                snapshot.setWarehouseId(RandomUtil.randomString(32));
                snapshot.setCreateId(RandomUtil.randomString(32));
                snapshot.setCreatedTime(new DateTime());
                snapshot.setModifyId(RandomUtil.randomString(32));
                snapshot.setModifyTime(new DateTime());
                snapshotList.add(snapshot);
            }
            storeSnapshotMapper.batchInsert(snapshotList);
            txManager.commit(txStatus);
        } catch (Throwable t) {
            logger.error("批量创建用户信息出现异常", t);
            txManager.rollback(txStatus);
        }
        return 0;
    }
}
