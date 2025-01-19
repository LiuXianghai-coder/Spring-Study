package com.example.demo.algorithm;

/**
 *@author lxh
 */
public class React2D
        extends React {

    private final double xmin, ymin;   // minimum x- and y-coordinates
    private final double xmax, ymax;   // maximum x- and y-coordinates

    public React2D(double xmin, double ymin, double xmax, double ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
        if (Double.isNaN(xmin) || Double.isNaN(xmax)) {
            throw new IllegalArgumentException("x-coordinate is NaN: " + toString());
        }
        if (Double.isNaN(ymin) || Double.isNaN(ymax)) {
            throw new IllegalArgumentException("y-coordinate is NaN: " + toString());
        }
        if (xmax < xmin) {
            throw new IllegalArgumentException("xmax < xmin: " + toString());
        }
        if (ymax < ymin) {
            throw new IllegalArgumentException("ymax < ymin: " + toString());
        }
    }

    public double xmin() {
        return xmin;
    }

    public double xmax() {
        return xmax;
    }

    public double ymin() {
        return ymin;
    }

    public double ymax() {
        return ymax;
    }

    @Override
    public React buildLeftReact(Point point, int dimension) {
        checkParameters(point);
        Point2D point2D = (Point2D) point;
        if (dimension == KdTree.VERTICAL) {
            return new React2D(this.xmin, this.ymin, point2D.x(), this.ymax);
        }
        return new React2D(this.xmin, this.ymin, this.xmax, point2D.y());
    }

    @Override
    public React buildRightReact(Point point, int dimension) {
        checkParameters(point);
        Point2D point2D = (Point2D) point;
        if (dimension == KdTree.VERTICAL) {
            return new React2D(point2D.x(), this.ymin, this.xmax, this.ymax);
        }
        return new React2D(this.xmin, point2D.y(), this.xmax, this.ymax);
    }

    @Override
    public double distanceSquaredTo(Point point) {
        if (!(point instanceof Point2D)) {
            throw new IllegalArgumentException("不兼容的点类型: " + (point == null ? null : point.getClass()));
        }

        Point2D p = (Point2D) point;
        double dx = 0.0, dy = 0.0;
        if (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = p.x() - xmax;
        if (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = p.y() - ymax;
        return dx * dx + dy * dy;
    }

    @Override
    public boolean contains(Point point) {
        checkParameters(point);
        Point2D p = (Point2D) point;
        return (p.x() >= xmin) && (p.x() <= xmax)
                && (p.y() >= ymin) && (p.y() <= ymax);
    }

    @Override
    public boolean intersects(React react) {
        checkParameters(react);
        React2D that = (React2D) react;
        return this.xmax >= that.xmin && this.ymax >= that.ymin
                && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    @Override
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }

    private static void checkParameters(React react) {
        if (!(react instanceof React2D)) {
            throw new IllegalArgumentException("不兼容的矩形类型: " + (react == null ? null : react.getClass()));
        }
    }

    private static void checkParameters(Point point) {
        if (!(point instanceof Point2D)) {
            throw new IllegalArgumentException("不兼容的点类型: " + (point == null ? null : point.getClass()));
        }
    }
}
