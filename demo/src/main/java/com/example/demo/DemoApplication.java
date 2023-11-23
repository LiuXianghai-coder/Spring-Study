package com.example.demo;

import com.example.demo.entity.BigJson;
import com.example.demo.mapper.BigJsonMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@tk.mybatis.spring.annotation.MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {

    private final static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        BigJsonMapper jsonMapper = context.getBean(BigJsonMapper.class);
        DataSourceTransactionManager txManager = context.getBean(DataSourceTransactionManager.class);
        Thread[] ts = new Thread[8];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(new Task(jsonMapper, txManager));
        }
        Arrays.stream(ts).forEach(Thread::start);
    }

    public static class Task
            implements Runnable {

        private final BigJsonMapper jsonMapper;

        private final PlatformTransactionManager txManager;

        public Task(BigJsonMapper jsonMapper,
                    PlatformTransactionManager txManager) {
            this.jsonMapper = jsonMapper;
            this.txManager = txManager;
        }

        @Override
        public void run() {
            TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
            try {
                jsonMapper.insertSelective(BigJson.example());
                txManager.commit(status);
            } catch (Throwable t) {
                txManager.rollback(status);
                throw t;
            }
        }
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
