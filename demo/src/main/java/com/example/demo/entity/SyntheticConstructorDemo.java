package com.example.demo.entity;

/**
 * @author lxh
 */
public class SyntheticConstructorDemo {
    private NestedClass nestedClass = new NestedClass();

    class NestedClass {
        private NestedClass(){}
    }
}
