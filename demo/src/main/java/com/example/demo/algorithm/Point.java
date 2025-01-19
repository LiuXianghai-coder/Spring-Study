package com.example.demo.algorithm;

/**
 *@author lxh
 */
public abstract class Point
        implements Comparable<Point> {

    public abstract double distanceSquaredTo(Point point);
}
