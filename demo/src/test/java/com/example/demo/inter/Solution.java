package com.example.demo.inter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;

/**
 * @author xhliu
 * @create 2022-06-01-8:33
 **/
public class Solution {
    int[] sticks;
    int n, visit;

    public boolean makesquare(int[] _sticks) {
        sticks = _sticks;
        n = sticks.length;
        int[] edges = new int[4];
        return backTrace(edges, 0, 0);
    }

    boolean backTrace(int[] edges, int cur, int take) {
        if (cur >= 4) {
            if (take >= n && check(edges)) {
                System.out.println(Arrays.toString(edges));
                return true;
            }
            return false;
        }

        boolean ans;
        for (int i = 0; i < n; ++i) {
            if ((visit & (1 << i)) != 0) continue;

            for (int j = 0; j < 4; ++j) {
                visit |= 1 << i;

                edges[j] += sticks[i];
                ans = backTrace(edges, cur + 1, take + 1);
                if (ans) return true;

                visit ^= 1 << i;
                edges[cur] -= sticks[i];
                ans = backTrace(edges, cur + 1, take);
                if (ans) return true;
            }
        }

        return false;
    }

    boolean check(int[] edges) {
        int val = edges[0];
        for (int i = 1; i < edges.length; ++i)
            if (edges[i] != val) return false;
        return true;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        static TreeNode add(TreeNode root, int val) {
            if (root == null) return new TreeNode(val);
            if (val < root.val) root.left = add(root.left, val);
            else root.right = add(root.right, val);
            return root;
        }
    }

    int key;

    public TreeNode deleteNode(TreeNode root, int _key) {
        key = _key;
        return dfs(root);
    }

    TreeNode dfs(TreeNode root) {
        if (root == null) return null;

        if (root.val == key) {
            if (root.left == null && root.right == null) return null;

            TreeNode node, prev;
            if (root.right == null) {
                node = root.left;
                prev = root;
                while (node.right != null) {
                    prev = node;
                    node = node.right;
                }
                TreeNode nxtRight = null;

                prev.right = node.left;
                node.left = root.left;
            } else {
                node = root.right;
                prev = root;
                while (node.left != null) {
                    prev = node;
                    node = node.left;
                }

                prev.left = node.right;
                node.left = root.left;
                node.right = root.right;
            }

            return node;
        }

        if (root.val > key) root.right = dfs(root.right);
        else root.left = dfs(root.left);

        return root;
    }

    char[] arr;
    int[][] dp;

    public int minFlipsMonoIncr(String s) {
        arr = s.toCharArray();
        n = arr.length;
        dp = new int[n][2];
        for (int i = 0; i < n; ++i) Arrays.fill(dp[i], Integer.MAX_VALUE);

        dfs(arr, 0, 0);

        return dp[0][arr[0] - '0'];
    }

    int dfs(char[] arr, int cur, int opNum) {
        if (cur >= n) {
            return check() ? opNum : Integer.MAX_VALUE;
        }

        int op1, op2, idx;

        arr[cur] = arr[cur] == '0' ? '1' : '0';
        idx = arr[cur] - '0';
        if (dp[cur][idx] != Integer.MAX_VALUE) {
            op1 = opNum + dp[cur][idx];
        } else {
            op1 = dfs(arr, cur + 1, opNum + 1);
        }
        dp[cur][idx] = Math.min(op1, dp[cur][idx]);
        arr[cur] = arr[cur] == '0' ? '1' : '0';

        idx = arr[cur] - '0';
        if (dp[cur][idx] != Integer.MAX_VALUE) {
            op2 = opNum + dp[cur][idx];
        } else {
            op2 = dfs(arr, cur + 1, opNum);
        }
        dp[cur][idx] = Math.min(op2, dp[cur][idx]);

        return Math.min(op1, op2);
    }

    boolean check() {
        for (int i = 1; i < n; ++i)
            if (arr[i] < arr[i - 1]) return false;

        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        long start, end;

        start = System.currentTimeMillis();
        System.out.println(solution.minFlipsMonoIncr("01"));
        end = System.currentTimeMillis();
        System.out.printf("Take Time %.4f ms\n", (end - start) * 1.0 / 1000);

//        start = System.currentTimeMillis();
//        System.out.println(solution.minFlipsMonoIncr("010110"));
//        end = System.currentTimeMillis();
//        System.out.printf("Take Time %.4f ms\n", (end - start) * 1.0 / 1000);
//
//        start = System.currentTimeMillis();
//        System.out.println(solution.minFlipsMonoIncr("00011000"));
//        end = System.currentTimeMillis();
//        System.out.printf("Take Time %.4f ms\n", (end - start) * 1.0 / 1000);
//
//        start = System.currentTimeMillis();
//        System.out.println(solution.minFlipsMonoIncr("10011111110010111011"));
//        end = System.currentTimeMillis();
//        System.out.printf("Take Time %.4f ms\n", (end - start) * 1.0 / 1000);
    }
}
