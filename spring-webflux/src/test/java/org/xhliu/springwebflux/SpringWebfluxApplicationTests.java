package org.xhliu.springwebflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xhliu.springwebflux.repository.CustomerRepository;

@SpringBootTest
class SpringWebfluxApplicationTests {
    @Autowired
    CustomerRepository repo;

    @Test
    void contextLoads() {
        System.out.println(repo.toString());
    }

}
