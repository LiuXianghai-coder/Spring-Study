package org.xhliu.cloudnacos.demo;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author xhliu
 * @time 2022-02-27-下午5:51
 */
public class NacosDemoListener {
    private final static Logger log = LoggerFactory.getLogger(NacosDemoListener.class);

    private final static String SERVER_ADDRESS = "127.0.0.1:8848";

    private final static String DATA_ID_1 = "nacos.cfg.dataId";

    private final static String GROUP_1 = "test";

    public static void main(String[] args) throws NacosException, InterruptedException {
        ConfigService configService = NacosFactory.createConfigService(SERVER_ADDRESS);
        configService.addListener(DATA_ID_1, GROUP_1, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String s) {
                log.info("receive = {}", s);
            }
        });

        CountDownLatch latch = new CountDownLatch(2);
        latch.await();
    }
}
