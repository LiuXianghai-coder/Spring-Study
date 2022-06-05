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

            root.left = null; root.right = null;
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

    public static void main(String[] args) {
        Integer[] nodes = {2, 0, 33, null, 1, 25, 40, null, null, 11, 31, 34, 45, 10, 18, 29, 32,
                null, 36, 43, 46, 4, null, 12, 24, 26, 30, null, null, 35, 39, 42, 44, null,
                48, 3, 9, null, 14, 22, null, null, 27, null, null, null, null, 38, null, 41,
                null, null, null, 47, 49, null, null, 5, null, 13, 15, 21, 23, null, 28, 37,
                null, null, null, null, null, null, null, null, 8, null, null, null, 17, 19,
                null, null, null, null, null, null, null, 7, null, 16, null, null, 20, 6
        };

        TreeNode root = null;
        for (Integer val : nodes) {
            if (val == null) continue;
            root = TreeNode.addNode(root, val);
        }

        Solution solution = new Solution();
        System.out.println(solution.deleteNode(root, 33));

        solution.traverse(root);
    }
}
