package com.example.demo;

import java.util.*;

public class Solution {
    static final int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int m, n;
    int[][] grid;
    boolean[][] visit;

    public int cutOffTree(List<List<Integer>> forest) {
        m = forest.size(); n = forest.get(0).size();
        grid = new int[m][n];
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                grid[i][j] = forest.get(i).get(j);

        visit = new boolean[m][n];

        int ans = 0;
        Deque<int[]> deque = new LinkedList<>();
        if (grid[0][0] > 1) grid[0][0] = 1;

        visit[0][0] = true;
        deque.offer(new int[]{0, 0});
        while (!deque.isEmpty()) {
            int sz = deque.size();
            while (sz-- > 0) {
                int[] tmp = deque.poll();
                assert tmp != null;
                for (int[] dir : dirs) {
                    int x = tmp[0] + dir[0], y = tmp[1] + dir[1];
                    if (x < 0 || x >= m || y < 0 || y >= n) continue;
                    if (visit[x][y] || grid[x][y] == 0) continue;

                    ans++; deque.offer(new int[]{x, y});
                    if (!isMin(x, y)) continue;

                    grid[x][y] = 1; visit[x][y] = true;
                }
            }
        }

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
//                if (grid[i][j] > 1) return -1;
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }

        return ans;
    }

    boolean isMin(int x, int y) {
        int rowMin = Integer.MAX_VALUE, colMin = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            if (visit[x][i] || grid[x][i] <= 1) continue;
            rowMin = Math.min(grid[x][i], rowMin);
        }

        for (int i = 0; i < m; ++i) {
            if (visit[i][y] || grid[i][y] <= 1) continue;
            colMin = Math.min(grid[i][y], colMin);
        }
        int min = Math.min(rowMin , colMin);
        return grid[x][y] == min;
    }


    public static void main(String[] args) {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(54581641, 64080174, 24346381, 69107959));
        list.add(Arrays.asList(86374198, 61363882, 68783324, 79706116));
        list.add(Arrays.asList(668150, 92178815, 89819108, 94701471));
        list.add(Arrays.asList(83920491, 22724204, 46281641, 47531096));
        list.add(Arrays.asList(89078499, 18904913, 25462145, 60813308));

        Solution solution = new Solution();
        System.out.println(solution.cutOffTree(list));
    }
}
