package com.example.demo.datastructrue;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 *@author lxh
 */
public class PointSET {

    private final TreeSet<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : pointSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        List<Point2D> ans = new ArrayList<>();
        double xmin = rect.xmin(), ymin = rect.ymin();
        double xmax = rect.xmax(), ymax = rect.ymax();
        for (Point2D point : pointSet) {
            if (xmin <= point.x() && ymin <= point.y()
                    && point.x() <= xmax && point.y() <= ymax) {
                ans.add(point);
            }
        }
        return ans;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Point2D ans = null;
        double dist = Integer.MAX_VALUE;
        for (Point2D point : pointSet) {
            double distSquare = point.distanceSquaredTo(p);
            if (distSquare < dist) {
                dist = distSquare;
                ans = point;
            }
        }
        return ans;
    }
}
