package com.example.demo.entity;

/**
 * @author xhliu2
 * @create 2021-09-06 16:16
 **/
public class Example {
    private static class Holder {
        static {
            System.out.println("Holder Initialize");
        }

        private final static Example INSTANCE = new Example();
    }

    public Example(){}

    public static Example getInstance() {
        return Holder.INSTANCE;
    }

    static {
        System.out.println("Example Initialize");
    }

    private final static Example INSTANCE = new Example();

    public static Example getSimpleInstance() {
        return INSTANCE;
    }
}
