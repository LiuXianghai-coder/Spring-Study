package org.xhliu.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author xhliu
 * @time 2022-01-26-下午5:35
 */
@SpringBootTest
public class Solution {
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



    @Test
    public void test() {
        Map<Point, Integer> map = new TreeMap<>();
        Point corn = new Point(798, 351);
        Point left = new Point(419, 351);
        Point right = new Point(798,  730);
        Point cur = new Point(419,  730);

        map.put(corn, map.getOrDefault(corn, 0) + 1);
        map.put(left, map.getOrDefault(left, 0) + 1);
        map.put(right, map.getOrDefault(right, 0) + 1);

        System.out.println(map.get(new Point(cur.x, corn.y)));
        System.out.println(map.get(new Point(corn.x, cur.y)));
        System.out.println(map.get(new Point(798, 351)));
    }
}
