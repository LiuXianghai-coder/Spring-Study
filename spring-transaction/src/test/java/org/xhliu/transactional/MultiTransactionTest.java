package org.xhliu.transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.xhliu.springtransaction.Application;
import org.xhliu.springtransaction.MultiThreadTransaction;

import javax.annotation.Resource;

/**
 * @author lxh
 */
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
public class MultiTransactionTest {

    @Resource
    private MultiThreadTransaction transaction;

    @Test
    public void multiThreadTransactionTest() throws InterruptedException {
        transaction.bizHandler();
    }
}
