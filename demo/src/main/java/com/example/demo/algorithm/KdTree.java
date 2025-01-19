package com.example.demo.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *@author lxh
 */
public class KdTree {

    /*
        插入或删除到一定次数后需要重新构建当前 K-D 树
     */
    private final static int DEFAULT_BUILD_THRESHOLD = 1000;

    public static final int HORIZONTAL = 0; // Y 轴维度（水平方向）
    public static final int VERTICAL = 1; // X 轴维度（垂直方向）

    public static class Node
            implements Comparable<Node> {

        public static final Node DEFAULT_DIMESSION_NODE = new Node(null, HORIZONTAL);

        private final Point point;

        private final int dimension;

        private Node left, right;

        private Node(Point point, int dimension) {
            this.point = point;
            this.dimension = dimension;
        }

        public int nextDimension(int dimension) {
            if (dimension == HORIZONTAL) return VERTICAL;
            return HORIZONTAL;
        }

        public double currentDimensionVal(Point point) {
            if (!(point instanceof Point2D)) {
                throw new IllegalArgumentException();
            }

            if (this.dimension == VERTICAL) {
                return ((Point2D) point).x();
            }
            return ((Point2D) point).y();
        }

        @Override
        public int compareTo(Node o) {
            if (o == null) {
                throw new IllegalArgumentException();
            }
            if (!(this.point instanceof Point2D) || !(o.point instanceof Point2D)) {
                throw new IllegalArgumentException();
            }
            if (this.dimension == VERTICAL) {
                return Double.compare(((Point2D) this.point).x(), ((Point2D) o.point).x());
            }
            return Double.compare(((Point2D) this.point).y(), ((Point2D) o.point).y());
        }
    }

    Node root;

    private final List<Point> pointList = new ArrayList<>();

    private int modifyCnt = 0;

    private int size = 0;

    private final int batchSize;

    public KdTree() {
        this(DEFAULT_BUILD_THRESHOLD);
    }

    public KdTree(int batchSize) {
        this.batchSize = batchSize;
    }

    public KdTree(double[][] points) {
        this(points, DEFAULT_BUILD_THRESHOLD);
    }

    public KdTree(double[][] points, int batchSize) {
        this.batchSize = batchSize;
        for (double[] p : points) {
            pointList.add(buildPoint(p));
        }
        this.root = build(null, pointList, 0, points.length - 1);
    }

    public void insert(double[] point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }

        Point buildPoint = buildPoint(point);
        this.root = insert(root, buildPoint, defaultDimensionNode().dimension);
        modifyCnt++;
        size++;
        pointList.add(buildPoint);

        if (modifyCnt > batchSize) {
            modifyCnt = 0;
            build(null, pointList, 0, pointList.size() - 1);
        }
    }

    public int size() {
        return size;
    }

    public boolean contains(double[] point) {
        return contains(root, buildPoint(point));
    }

    /**
     * 查询在一个矩形区域内所有的点
     *
     * @param rect 待查询的区间的坐标限制
     * @return 矩形区域内的所有的点
     */
    public Iterable<Point> range(React rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        List<Point> ans = new ArrayList<>();
        range(root, initReact(), rect, ans);
        return ans;
    }

    /**
     * 查询在当前的 K-D 树中，距离当前点最近的点
     *
     * @param point 待查询的点
     * @return 当前 K-D 树中距离查询点最近的点
     */
    public Point nearest(double[] point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return nearest(root, initReact(), buildPoint(point), null, Double.POSITIVE_INFINITY);
    }

    // 首个节点默认的维度
    protected Node defaultDimensionNode() {
        return Node.DEFAULT_DIMESSION_NODE;
    }

    // 从一个 double 数组中转换为节点对应的 Point 对象
    protected Point buildPoint(double[] point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return new Point2D(point[0], point[1]);
    }

    // 初始所在的空间，如一个 1 x 1 的正方形，或者更高维度的空间

    protected React initReact() {
        return new React2D(0, 0, 1, 1);
    }

    private Node build(Node root, List<Point> points, int left, int right) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Node dimensionNode = root == null ? defaultDimensionNode() : root;
        Node currDimensionNode = new Node(null, dimensionNode.nextDimension(dimensionNode.dimension));
        if (left == right) {
            return new Node(points.get(left), currDimensionNode.dimension);
        }
        if (left > right) {
            return null;
        }
        int index = findMidIndex(currDimensionNode, points, left, right);
        Node node = new Node(points.get(index), currDimensionNode.dimension);
        node.left = build(node, points, left, index - 1);
        node.right = build(node, points, index + 1, right);
        return node;
    }

    private void range(Node parent, React baseReact,
                       React rect, List<Point> list) {
        if (parent == null) return;
        Point point = parent.point;
        if (rect.contains(point)) {
            list.add(point);
        }

        React leftRect = baseReact.buildLeftReact(point, parent.dimension);
        React rightRect = baseReact.buildRightReact(point, parent.dimension);

        if (rect.intersects(leftRect)) {
            range(parent.left, leftRect, rect, list);
        }
        if (rect.intersects(rightRect)) {
            range(parent.right, rightRect, rect, list);
        }
    }

    private Point nearest(Node parent, React react,
                          Point p, Point closerPoint,
                          double rawDistance) {
        if (parent == null) {
            return closerPoint;
        }

        Point point = parent.point;
        double curDistance = p.distanceSquaredTo(point);
        if (closerPoint == null || curDistance < rawDistance) {
            closerPoint = point;
            rawDistance = curDistance;
        }

        React leftRect = react.buildLeftReact(p, parent.dimension);
        React rightRect = react.buildRightReact(p, parent.dimension);

        double leftDistance = leftRect.distanceSquaredTo(p);
        double rightDistance = rightRect.distanceSquaredTo(p);

        /*
            如果当前待查询点距离划分的超平面的距离大于当前已经查询到的最近的点的距离，
            那么这部分超平面内的点不需要再进行查询
         */
        if (leftDistance <= rightDistance) {
            if (leftDistance < rawDistance) {
                closerPoint = nearest(parent.left, leftRect, p, closerPoint, rawDistance);
                rawDistance = closerPoint.distanceSquaredTo(p);
            }

            if (rightDistance < rawDistance) {
                closerPoint = nearest(parent.right, rightRect, p, closerPoint, rawDistance);
            }
        } else {
            if (rightDistance < rawDistance) {
                closerPoint = nearest(parent.right, rightRect, p, closerPoint, rawDistance);
                rawDistance = closerPoint.distanceSquaredTo(p);
            }

            if (leftDistance < rawDistance) {
                closerPoint = nearest(parent.left, leftRect, p, closerPoint, rawDistance);
            }
        }

        return closerPoint;
    }

    private boolean contains(Node root, Point point) {
        if (root == null) {
            return false;
        }
        Node node = new Node(point, root.dimension);
        int comp = root.compareTo(node);
        if (comp == 0) return true;
        if (comp < 0) {
            return contains(root.left, point);
        }
        return contains(root.right, point);
    }

    private Node insert(Node root, Point point, int dimension) {
        if (root == null) {
            return new Node(point, defaultDimensionNode().nextDimension(dimension));
        }
        Node node = new Node(point, root.dimension);
        if (node.compareTo(root) <= 0) {
            root.left = insert(root.left, point, root.nextDimension(dimension));
        } else {
            root.right = insert(root.right, point, root.nextDimension(dimension));
        }
        return root;
    }

    private int findMidIndex(Node root, List<Point> points, int left, int right) {
        return quickSplit(root, points, left, right);
    }

    private int quickSplit(Node root, List<Point> points, int left, int right) {
        Point cur = points.get(left);
        double val = root.currentDimensionVal(cur);
        int lo = left + 1, hi = right;
        while (true) {
            while (lo < right && Double.compare(root.currentDimensionVal(points.get(lo)), val) <= 0) lo++;
            while (left < hi && Double.compare(root.currentDimensionVal(points.get(hi)), val) > 0) hi--;

            if (lo >= hi) break;

            exchange(points, lo, hi);
        }
        exchange(points, left, hi);
        return hi;
    }

    private void exchange(List<Point> points, int i, int j) {
        Point tmp = points.get(i);
        points.set(i, points.get(j));
        points.set(j, tmp);
    }
}
