package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Solution {
    static final int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int m, n;
    int[][] grid;
    boolean[][] visit;

    public int cutOffTree(List<List<Integer>> forest) {
        m = forest.size();
        n = forest.get(0).size();
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

                    ans++;
                    deque.offer(new int[]{x, y});
                    if (!isMin(x, y)) continue;

                    grid[x][y] = 1;
                    visit[x][y] = true;
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
        int min = Math.min(rowMin, colMin);
        return grid[x][y] == min;
    }

    public static class TreeNode {
        int val;
        TreeNode left, right;

        public TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public static TreeNode addNode(TreeNode root, int val) {
            if (root == null) return new TreeNode(val);
            if (root.val <= val) root.right = addNode(root.right, val);
            else root.left = addNode(root.left, val);
            return root;
        }
    }

    TreeNode rotateLeft(TreeNode above, TreeNode below) {
        above.right = below.left;
        below.left = above;
        return below;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (root.val == key) {
            if (root.left == null && root.right == null) return null;
            if (root.right == null) return root.left;
            if (root.left == null) return root.right;

            TreeNode suc = root.right, prev = root;
            while (suc.left != null || suc.right != null) {
                if (suc.left == null) {
                    suc = rotateLeft(suc, suc.right);
                    if (prev == root) root.right = suc;
                    else prev.left = suc;
                }

                prev = suc;
                suc = suc.left;
            }

            System.out.println("suc=" + suc.val + "\tprev=" + prev.val);

            if (prev == root) {
                suc.left = root.left;
                suc.right = root.right.right;
            } else {
                suc.right = root.right;
                suc.left = root.left;
                prev.left = null;
            }

            root.left = null;
            root.right = null;
            return suc;
        }

        if (root.val < key) root.right = deleteNode(root.right, key);
        else root.left = deleteNode(root.left, key);

        return root;
    }

    public void traverse(TreeNode root) {
        // 创建对应的 dot 文件以便生成对应的树图
        StringBuilder sb = new StringBuilder();
        sb.append("digraph avl {\n");
        firstTraverse(root, sb);
        sb.append("}");
        try {
            File file = new File("/tmp/Graphviz");
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("mkdir success...");
                }
                file = new File("/tmp/Graphviz/avl.dot");

                if (!file.exists()) {
                    if (file.createNewFile()) {
                        System.out.println("create avl.dot file success....");
                    }
                }
            }

            /*
                生成二插树的脚本文件：
                    https://gist.githubusercontent.com/nanpuyue/b5950f20937f01aa43227d269aa83918/raw/dec7bc293ef051ca159c546d14c6caed75be111c/tree.g
                保存该文件为 tree.g
                执行：dot /tmp/Graphviz/avl.dot | gvpr -c -f tree.g | neato -n -Tpng -o avl.png 即可
             */
            Path path = Paths.get("/tmp/Graphviz/avl.dot");
            Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void firstTraverse(TreeNode node, StringBuilder builder) {
        if (node == null) return;

        if (node.left != null)
            builder.append("\t")
                    .append(node.val)
                    .append("->")
                    .append(node.left.val)
                    .append(";\n");
        if (node.right != null)
            builder.append("\t")
                    .append(node.val)
                    .append("->")
                    .append(node.right.val)
                    .append(";\n");

        firstTraverse(node.left, builder);
        firstTraverse(node.right, builder);
    }

    Map<String, Integer> map = new HashMap<>();

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        int n = words.length, wl = words[0].length(), sum = n * wl;

        int[] cnt = new int[26], tar = new int[26];
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
            for (int j = 0; j < wl; ++j) tar[word.charAt(j) - 'a']++;
        }

        char[] arr = s.toCharArray();
        int len = arr.length, lo = 0, hi = 0;
        while (hi < len) {
            while (hi < len && hi - lo + 1 <= sum) cnt[arr[hi++] - 'a']++;

            if (check(cnt, tar) && order(s.substring(lo, hi), wl)) ans.add(lo);

            while (lo < hi && hi - lo + 1 > sum) cnt[arr[lo++] - 'a']--;
        }

        return ans;
    }

    boolean check(int[] cnt, int[] tar) {
        for (int i = 0; i < cnt.length; ++i)
            if (cnt[i] != tar[i]) return false;
        return true;
    }

    boolean order(String s, int wl) {
        Map<String, Integer> tmp = new HashMap<>();
        for (int i = 0; i < s.length(); i += wl) {
            String s1 = s.substring(i, i + wl);
            tmp.put(s1, tmp.getOrDefault(s1, 0) + 1);
        }

        return tmp.equals(map);
    }

    static int mod = 1000_000_000 + 7;

    int[][] g, dp;
    public int countPaths(int[][] _g) {
        g = _g;
        m = g.length; n = g[0].length;
        dp = new int[m][n];
        for (int i = 0; i < m; ++i) Arrays.fill(dp[i], -1);

        int ans = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                ans = (ans + dfs(i, j)) % mod;
            }
        }

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }

        return ans;
    }

    int dfs(int i, int j) {
        if (dp[i][j] > 0) return dp[i][j];

        dp[i][j] = 1; // 每个位置自身都是一条有效的路径
        for (int[] dir : dirs) {
            int x = i + dir[0], y = j + dir[1];
            if (x < 0 || x >= m || y < 0 || y >= n || g[x][y] >= g[i][j]) continue;

            dp[i][j] = (dp[i][j] + dfs(x, y)) % mod;
        }

        return dp[i][j];
    }

    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        long[] dp = new long[n + 1];
        Arrays.fill(dp, 1L);
        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < n; ++i) map.put((long) arr[i], i);
        long ans = 0L;
        for (int i = 0; i < n - 2; ++i) {
            for (int j = i + 1; j < n - 1; ++j) {
                long tmp = (long) arr[i] + arr[j];
                if (!map.containsKey(tmp)) continue;
                int idx = map.get(tmp);
                dp[idx] = Math.max(dp[i] + 2, dp[idx]);
                if (dp[idx] > ans) ans = dp[idx];
            }
        }

        return (int) ans;
    }

    void fib(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i < n; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        System.out.println(Arrays.toString(dp));
    }

    public boolean canChange(String start, String target) {
        char[] s = start.toCharArray();
        char[] t = target.toCharArray();

        int n = s.length;

        TreeSet<int[]> lts = new TreeSet<>(Comparator.comparingInt(a -> a[0]));
        TreeSet<int[]> rts = new TreeSet<>(Comparator.comparingInt(a -> a[0]));
        int ls = 0;
        for (int i = 0; i < n; ++i) {
            if (s[i] == 'L') {
                lts.add(new int[]{i, ls});
            } else if (s[i] != '_') ls = i + 1;
        }

        int rs = n - 1;
        for (int i = n - 1; i >= 0; --i) {
            if (s[i] == 'R') rts.add(new int[]{i, rs});
            else if (s[i] != '_') rs = i - 1;
        }

        for (int i = 0; i < s.length; ++i) {
            if (s[i] == t[i]) continue;
            if (s[i] == '_' && t[i] == 'L') {
                int[] tmp = lts.ceiling(new int[]{i + 1, 0});
                // System.out.println(i + "\t" + idx);
                if (tmp == null || tmp[1] > i) return false;
                exchange(s, i, tmp[0]);
            }
        }

        for (int i = n - 1; i >= 0; --i) {
            if (s[i] == t[i]) continue;
            if (s[i] == '_' && t[i] == 'R') {
                int[] tmp = rts.floor(new int[]{i - 1, 0});
                // System.out.println(i + "\t" + idx + " simple");
                if (tmp == null || tmp[1] < i) return false;
                exchange(s, i, tmp[0]);
            }
        }

        // System.out.println(String.valueOf(s));
        for (int i = 0; i < n; ++i) {
            if (s[i] != t[i]) return false;
        }
        return true;
    }

    void exchange(char[] s, int i, int j) {
        char ch = s[i];
        s[i]= s[j];
        s[j] = ch;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
    }
}
