package com.example.demo.entity;

import java.util.Arrays;

/**
* @author lxh
* @date 2022/7/17-上午9:51
*/
public class Example {

    static class Dog {
        public final Cat cat = new Cat();
    }

    static class Cat {
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        System.out.println(dog.cat);
    }
}
