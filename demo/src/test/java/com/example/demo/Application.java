package com.example.demo;

import java.util.*;

/**
 * @author xhliu2
 **/
public class Application {

    public static void main(String[] args) {
        Application app = new Application();
//        System.out.println(app.minimumFuelCost(new int[][]{{3, 1}, {3, 2}, {1, 0}, {0, 4}, {0, 5}, {4, 6}}, 2));
        System.out.println(app.minimumFuelCost(new int[][]{{0, 1}, {0, 2}, {1, 3}, {1, 4}}, 5));
    }

    public long minimumFuelCost(int[][] roads, int seats) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, List<Integer>> directGraph = new HashMap<>();

        int n = roads.length;
        int[] in = new int[n + 1];
        for (int i = 0; i <= n; ++i) {
            graph.computeIfAbsent(i, any -> new ArrayList<>());
            directGraph.computeIfAbsent(i, any -> new ArrayList<>());
        }
        for (int[] road : roads) {
            int x = road[0], y = road[1];
            graph.get(x).add(y);
            graph.get(y).add(x);
        }

        Deque<Integer> deque = new LinkedList<>();
        deque.offer(0);
        boolean[] visited = new boolean[n + 1];
        while (!deque.isEmpty()) {
            int sz = deque.size();
            for (int i = 0; i < sz; ++i) {
                int poll = deque.poll();
                visited[poll] = true;
                List<Integer> vertexList = graph.get(poll);
                for (int vertex : vertexList) {
                    if (visited[vertex]) continue;
                    in[poll]++;
                    deque.offer(vertex);
                    directGraph.get(vertex).add(poll);
                }
            }
        }

        // 重新遍历入度为 0 的节点，以得到最少油耗
        Arrays.fill(visited, false);
        boolean[] seated = new boolean[n + 1];
        Deque<int[]> q = new LinkedList<>();
        for (int i = 0; i <= n; ++i) {
            if (in[i] == 0) {
                seated[i] = true;
                q.offer(new int[]{i, 1, 0});
            }
        }
        Map<Integer, TreeSet<int[]>> satMap = new HashMap<>();
        int ans = 0;
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; ++i) {
                int[] poll = q.poll();
                int start = poll[0], curSeats = poll[1], costs = poll[2];
                List<Integer> vertexList = directGraph.get(start);
                for (int vertex : vertexList) {
                    if (vertex == 0) {
                        ans += costs + 1;
                        continue;
                    }
                    satMap.computeIfAbsent(vertex, any -> new TreeSet<>(Comparator.comparingInt(a -> a[1])));
                    --in[vertex];
                    TreeSet<int[]> ts = satMap.get(vertex);
                    if (ts.isEmpty()) { // 当前处理的节点未被接收
                        if (curSeats < seats) {
                            ts.add(new int[]{vertex, curSeats + 1, costs + 1});
                        } else {
                            ts.add(new int[]{vertex, curSeats, costs + 1});
                            ts.add(new int[]{vertex, 1, 0});
                        }
                    } else {
                        int[] ceil = ts.ceiling(new int[]{0, 0, 0});
                        if (ceil[1] >= seats) ts.add(new int[]{vertex, curSeats, costs + 1});
                        else ceil[1] += 1;
                    }
                    if (in[vertex] == 0) {
                        for (int[] ints : ts) {
                            q.offer(ints);
                        }
                    }
                }
            }
        }
        return ans;
    }
}
