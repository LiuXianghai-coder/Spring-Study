package org.xhliu.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xhliu.aop.service.OrderService;
import org.xhliu.aop.service.UserService;

public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args ) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:application-aspect.xml");

        UserService userService =  context.getBean(UserService.class);
        OrderService orderService = context.getBean(OrderService.class);

        userService.createUser("Xhanghai", "Liu");
        userService.queryUser();
//        log.info(orderService.createOrder("Xianghai", "Phone").toString());
//        log.info(orderService.queryOrder("Xianghai").toString());
    }
}
