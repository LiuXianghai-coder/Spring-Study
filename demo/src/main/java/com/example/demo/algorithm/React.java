package com.example.demo.algorithm;

/**
 *@author lxh
 */
public abstract class React {

    public abstract React buildLeftReact(Point point, int dimension);

    public abstract React buildRightReact(Point point, int dimension);

    public abstract double distanceSquaredTo(Point point);

    public abstract boolean contains(Point point);

    public abstract boolean intersects(React react);
}
