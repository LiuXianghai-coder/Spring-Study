package com.example.demo.entity;

/**
 * @author xhliu2
 * @create 2021-09-29 9:04
 **/
public class Mouse {
    private Mouse(){}

    public static final Mouse INSTANCE = new Mouse();

    private static class FiledHolder {
        static {
            System.out.println("Filed Holder Initialize......");
        }

        static final Mouse holder = new Mouse();
    }

    public static Mouse getMineInstance() {return INSTANCE;}

    public static Mouse getInstance() {return FiledHolder.holder;}
}
