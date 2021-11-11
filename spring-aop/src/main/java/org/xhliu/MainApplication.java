package org.xhliu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xhliu.aop.service.UserService;
import org.xhliu.service.PersonService;

public class MainApplication {
    private final static Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:application-schema-based.xml");

//        PersonService service = context.getBean(PersonService.class);
//        service.createPerson("Xianghai", "Liu", 22);
//        service.queryPerson("Xianghai");

        UserService userService = context.getBean(UserService.class);
        userService.createUser("Xianghai", "Liu");
        userService.queryUser();
    }
}
