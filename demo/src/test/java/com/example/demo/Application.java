package com.example.demo;

import edu.princeton.cs.algs4.RectHV;

import java.util.Arrays;

/**
 * @author xhliu2
 **/
public class Application {

    public static void main(String[] args) {
        RectHV rectHV1 = new RectHV(0.10546875, 0.576171875, 0.375, 0.802734375);
        RectHV rectHV2 = new RectHV(0, 0, 0.372, 0.577);

        System.out.println(rectHV1);
        System.out.println(rectHV2);

        System.out.println(rectHV1.intersects(rectHV2));
    }
}
