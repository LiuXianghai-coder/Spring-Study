package org.xhliu.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xhliu.aop.service.OrderService;
import org.xhliu.aop.service.UserService;

public class Application {
    public static void main( String[] args ) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:application-autoproxy.xml");

//        UserService userService = (UserService) context.getBean("userServiceProxy");
//        userService.createUser("Xianghai", "Liu");
//        userService.queryUser();

        UserService userService =  context.getBean(UserService.class);
        OrderService orderService = context.getBean(OrderService.class);

        userService.createUser("Xhanghai", "Liu");
        userService.queryUser();

        orderService.createOrder("Xianghai", "Phone");
    }
}
