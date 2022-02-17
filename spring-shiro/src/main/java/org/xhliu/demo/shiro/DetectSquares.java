package org.xhliu.demo.shiro;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author xhliu
 * @time 2022-01-26-下午5:44
 */
public class DetectSquares {
    static int MAX = 1000;
    static int[][] dirs = new int[][]{{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

    static class Point implements Comparable<Point> {
        final int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            if (this.x != o.x) return this.x - o.x;
            return this.y - o.y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    Map<Point, Integer> map = new TreeMap<>();

    public DetectSquares() {
    }

    public void add(int[] point) {
        int x = point[0], y = point[1];
        Point p = new Point(x, y);

        map.put(p, map.getOrDefault(p, 0) + 1);
    }

    public int count(int[] point) {
        int ans = 0;
        int x = point[0], y = point[1];

        for (int[] dir : dirs) {
            for (int i = 0; i <= MAX; ++i) {
                int nxt_x = x + (i * dir[0]), nxt_y = y + (i * dir[1]);
                if (nxt_x < 0 || nxt_y < 0) break;

                Point corn = new Point(nxt_x, nxt_y);
                ans += map.getOrDefault(corn, 0) * check(corn, nxt_x, nxt_y, x, y);
            }
        }

        return ans;
    }

    int check(Point corn, int nxt_x, int nxt_y, int x, int y) {
        if (!map.containsKey(corn)) return 0;
        Point left = new Point(nxt_x, y);
        Point right = new Point(x, nxt_y);

        return map.getOrDefault(left, 0) * map.getOrDefault(right, 0);
    }

    public static void main(String[] args) {
        DetectSquares squares = new DetectSquares();
        squares.add(new int[]{419, 351});
        squares.add(new int[]{798, 351});
        squares.add(new int[]{798, 730});
        System.out.println(squares.count(new int[]{419, 730}));
    }
}
