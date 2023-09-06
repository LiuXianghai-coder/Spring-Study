package com.example.demo;

import com.example.demo.entity.SaleInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoView;
import com.example.demo.mapper.SaleInfoMapper;
import com.example.demo.mapper.UserInfoMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.mapper"})
@tk.mybatis.spring.annotation.MapperScan(value = {"com.example.demo.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        UserInfoMapper infoMapper = context.getBean(UserInfoMapper.class);
        long id = 30000;
        String[] genders = new String[]{"Male", "FeMale"};
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<UserInfo> data = new ArrayList<>();
        for (int i = 0; i < 30000; ++i) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(id++);
            userInfo.setGender(genders[random.nextInt(0, genders.length)]);
            userInfo.setName(randomName(random));
            userInfo.setSimpleId(String.valueOf(random.nextInt(10000, 100000)));
            data.add(userInfo);
            if (data.size() > 500) {
                infoMapper.insertAll(data);
                data.clear();
            }
        }
        infoMapper.insertAll(data);
        SaleInfoMapper mapper = context.getBean(SaleInfoMapper.class);
        List<SaleInfo> saleInfos = new ArrayList<>();
        for (int i = 0; i < 30000; i++) {
            SaleInfo saleInfo = new SaleInfo();
            saleInfo.setId(id++);
            saleInfo.setAmount(random.nextInt(1, 100000));
            saleInfo.setSaleId(random.nextLong(1, 100000));
            saleInfo.setYear(OffsetDateTime.of(random.nextInt(2000, 2023),
                    random.nextInt(1, 13),
                    random.nextInt(1, 28),
                    random.nextInt(0, 24),
                    random.nextInt(0, 60),
                    random.nextInt(0, 60),
                    0, ZoneOffset.UTC
                    ));
            saleInfos.add(saleInfo);
            if (saleInfos.size() > 500) {
                mapper.insertAll(saleInfos);
                saleInfos.clear();
            }
        }
        mapper.insertAll(saleInfos);
    }

    static String randomName(ThreadLocalRandom random) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; ++i) {
            int r = random.nextInt(0, 26);
            sb.append((char)('a' + r));
            if (r > 20 && sb.length() > 3) break;
        }
        return sb.toString();
    }
}
