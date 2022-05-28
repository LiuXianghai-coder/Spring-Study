package com.example.demo;

import org.checkerframework.checker.units.qual.C;

import java.util.*;

class CountIntervals {
    final TreeSet<int[]> ts = new TreeSet<>((a, b) -> {
        if (a[0] == b[0] && a[1] == b[1]) return 0;
        return a[1] - b[1];
    });
    int ans;

    public CountIntervals() {

    }

    public void add(int left, int right) {
        int L = left, R = right;
        int[] tmp = ts.ceiling(new int[]{0, left - 1});
        while (tmp != null) {
            if (tmp[0] > right + 1) break;
            L = Math.min(tmp[0], L);
            R = Math.max(tmp[1], R);
            ans -= tmp[1] - tmp[0] + 1;
            ts.remove(tmp);

            tmp = ts.ceiling(tmp);
        }
        ans += R - L + 1;
        ts.add(new int[]{L, R});
    }

    public int count() {
        return ans;
    }

    static final int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int m, n;
    int[][] grid;

    public int cutOffTree(List<List<Integer>> forest) {
        m = forest.size(); n = forest.get(0).size();
        grid = new int[m][n];
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                grid[i][j] = forest.get(i).get(j);

        boolean[][] visit = new boolean[m][n];

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
                    if (visit[x][y]) continue;
                    if (grid[x][y] == 0) continue;
                    visit[x][y] = true;
                    if (grid[x][y] > grid[tmp[0]][tmp[1]]) {
                        ans++;
                        deque.offer(new int[]{x, y});
                    }
                }
                grid[tmp[0]][tmp[1]] = 1;
            }
        }
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] > 1) return -1;
            }
        }

        return ans;
    }

    public int findSubstringInWraproundString(String p) {
        char[] arr = p.toCharArray();
        int n = arr.length;

        int ans = 0, lo = 0, hi = 0;
        Set<String> set = new HashSet<>();
        while (hi < n) {
            String tmp = p.substring(hi, hi + 1);
            if (!set.contains(tmp)) {
                ans++;
                set.add(tmp);
            }

            while (lo < hi && !validStr(p.substring(lo, hi + 1))) {
                String s = p.substring(lo, hi);
                if (!set.contains(s)) {
                    ans++; set.add(s);
                }

                lo++;
            }

            while (lo < hi && validStr(p.substring(lo, hi + 1))) {
                set.add(p.substring(lo, hi + 1));
                ans++; lo++;
            }

            hi++;
        }
        System.out.println("ans=" + ans + "\tlo=" + lo + "\thi=" + hi);
        while (lo < hi) {
            String str = p.substring(lo, hi);
            if (validStr(str) && !set.contains(str)) ans++;
            lo++;
        }

        return ans;
    }

    boolean validStr(String str) {
        boolean res = true;
        for (int i = 1; i < str.length(); ++i) {
            char ch1 = str.charAt(i - 1), ch2 = str.charAt(i);
            if (ch2 - ch1 != 1) {
                res = ch2 == 'a' && ch1 == 'z';
            }
        }
        return res;
    }

    public List<Integer> fallingSquares(int[][] pos) {
        int n = pos.length;

        TreeMap<int[], Integer> ts = new TreeMap<>((a, b) -> {
            if (a[0] == b[0] && a[1] == b[1]) return 0;
            return a[1] - b[1];
        });

        List<Integer> ans = new ArrayList<>();
        ts.put(new int[]{pos[0][0], pos[0][0] + pos[0][1] - 1}, pos[0][1]);
        ans.add(pos[0][1]);
        int max = pos[0][1];
        for (int i = 1; i < n; ++i) {
            int L = pos[i][0], R = L + pos[i][1] - 1;

            int[] tmp = new int[]{0, L};
            int[] ceil = ts.ceilingKey(tmp);
            int H = pos[i][1];
            System.out.println("L=" + L + "\tR=" + R);
            while (ceil != null) {
                // System.out.println("i=" + i +"\ttmp=" + Arrays.toString(tmp) + "\tceil=" + Arrays.toString(ceil));
                if (ceil[0] > R) break;

                H = Math.max(ts.get(ceil) + pos[i][1], H);

                int[] val = new int[]{ceil[0] + 1, ceil[1] + 1};
                ceil = ts.ceilingKey(val);
            }

            ts.put(new int[]{L, R}, H);

            if (H > max) max = H;
            ans.add(max);
        }

        return ans;
    }

    public static void main(String[] args) {
        CountIntervals obj = new CountIntervals();
        List<Integer> list = obj.fallingSquares(new int[][]{{7,2},{1,7},{9,5},{1,8},{3,4}});
        System.out.println(list);
//        String str = "[[7,2],[1,7],[9,5],[1,8],[3,4]]";
//        System.out.println(str.replaceAll("\\[", "{").replaceAll("]", "}"));
    }
}
