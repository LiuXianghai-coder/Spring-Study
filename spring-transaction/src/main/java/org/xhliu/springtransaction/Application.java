package org.xhliu.springtransaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.xhliu.springtransaction.entity.CourseInfo;
import org.xhliu.springtransaction.service.ApplicationService;
import org.xhliu.springtransaction.service.CourseInfoService;

import java.util.concurrent.CountDownLatch;

/**
 * @author lxh
 */
@SpringBootApplication
@MapperScan(basePackages = {"org.xhliu.springtransaction.mapper"})
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        ApplicationService service = context.getBean(ApplicationService.class);
        service.bizHandler();
    }
}
