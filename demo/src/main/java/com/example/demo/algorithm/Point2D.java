package com.example.demo.algorithm;

import edu.princeton.cs.algs4.StdDraw;

/**
 * 二维平面的点的类型
 *
 *@author lxh
 */
public class Point2D
        extends Point {

    private final double x;    // x coordinate
    private final double y;    // y coordinate

    public Point2D(double x, double y) {
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        if (x == 0.0) this.x = 0.0;  // convert -0.0 to +0.0
        else this.x = x;

        if (y == 0.0) this.y = 0.0;  // convert -0.0 to +0.0
        else this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public double distanceSquaredTo(Point point) {
        if (!(point instanceof Point2D)) {
            throw new IllegalArgumentException();
        }
        Point2D that = (Point2D) point;
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return dx*dx + dy*dy;
    }

    @Override
    public int compareTo(Point point) {
        if (!(point instanceof Point2D)) {
            throw new IllegalArgumentException();
        }
        Point2D that = (Point2D) point;
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        return Double.compare(this.x, that.x);
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
