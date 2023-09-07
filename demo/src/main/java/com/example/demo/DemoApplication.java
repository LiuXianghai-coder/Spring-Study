package com.example.demo;

import com.example.demo.entity.BigDto;
import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.SaleInfoMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@tk.mybatis.spring.annotation.MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {

    private final static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SqlSession sqlSession = context.getBean(SqlSession.class);
        UserInfoMapper infoMapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo userInfo = new UserInfo();userInfo.setName("xhliu");
        System.out.println(infoMapper.selectByParam(userInfo));
        SaleInfoMapper mapper = context.getBean(SaleInfoMapper.class);
        List<BigDto> data = new ArrayList<>();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Reflector reflector = new Reflector(BigDto.class);
        List<Invoker> getInvokers = Lists.newArrayList();
        for (int i = 1; i <= 9; ++i) {
            getInvokers.add(reflector.getSetInvoker("obj" + i));
        }
        int sz = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(sz,
                Integer.MAX_VALUE,
                300,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>()
        );
        for (int i = 0; i < 20; ++i) {
            final BigDto bigDto = new BigDto();
            for (Invoker getInvoker : getInvokers) {
                executor.submit(() -> {
                    try {
                        getInvoker.invoke(bigDto, new Object[]{mapper.selectSaleInfo()});
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            data.add(bigDto);
        }
        System.out.println(data);
        executor.shutdown();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        log.info("Take time {} s", stopwatch.elapsed(TimeUnit.SECONDS));
        System.exit(0);
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
