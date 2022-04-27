package com.example.demo;

import com.example.demo.repository.ExampleRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xhliu
 * @create 2022-04-12-16:00
 **/
@SpringBootTest
public class ApplicationTest {
    @Resource
    private ExampleRepo exampleRepo;

    @Test
    public void test() {
    }
}
