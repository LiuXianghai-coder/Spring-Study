package com.example.demo.entity;

import java.util.*;

public class Solution {
    static final int START = 0;
    static final int START_TAG = 1 << 1;
    static final int CONTENT = 1 << 2;
    static final int CDATA = 1 << 3;
    static final int END = 1 << 4;

    public boolean isValid(String code) {
        int n = code.length();
        Deque<String> tagStack = new LinkedList<>();

        /*
         0 表示开始解析状态
         1 << 1 表示正在解析开始标签状态
         1 << 2 表示正在处理标签内容的状态
         1 << 3 表示正在处理 cdata 部分的状态
         1 << 4 表示正在处理结束标签的状态
         */
        int flag = 0;
        for (int i = 0, j, cnt_i = 0; i < n; ) {
            char ch = code.charAt(i);
            if (flag == START) {
                if (ch != '<') return false;
                i += 1;
                flag = START_TAG;
            } else if (flag == CONTENT) {
                if (code.charAt(i) == ' ') return false;
                j = i;
                while (j < n && code.charAt(j) != '<') j++;
                if (j >= n - 2) return false;

                if (code.substring(cnt_i, j).trim().length() == 0) return false;

                ch = code.charAt(j + 1);
                if (ch == '!') flag = CDATA;
                else if (ch == '/') {
                    if (code.charAt(j - 1) == ' ') return false;
                    flag = END;
                } else if (ch >= 'A' && ch <= 'Z') flag = START;

                else return false;

                i = j + 2;
            } else if (flag == CDATA) {
                if (i >= n - 1) return false;
                j = i;

                if (code.charAt(i) != '[') return false;

                j += 1;
                while (j < n && code.charAt(j) != '[') j++;
                if (j >= n) return false;

                String str = code.substring(i + 1, j);
                if (!str.equals("CDATA")) return false;

                while (j < n - 3 && (code.charAt(j) != ']' || code.charAt(j + 1) != ']' || code.charAt(j + 2) != '>'))
                    j++;

                if (j >= n - 3) return false;

                i = j + 3;
                flag = CONTENT;
            } else if (flag == START_TAG) {
                j = i;
                while (j < n && code.charAt(j) != '>') {
                    ch = code.charAt(j);
                    if (ch < 'A' || ch > 'Z') return false;

                    j++;
                }
                int len = j - i;
                if (len < 1 || len > 9) return false;

                tagStack.push(code.substring(i, j));
                flag = CONTENT;
                cnt_i = j;
            } else {
                j = i;
                while (j < n && code.charAt(j) != '>') j++;
                String str = code.substring(i, j);
                String poll = tagStack.pop();
                if (!str.equals(poll)) return false;

                i = j + 1;
                flag = START;
            }
        }

        return tagStack.isEmpty();
    }

    public static class Node implements Comparable<Node> {
        String content;
        List<String> words;

        Node(String cnt, List<String> words) {
            this.content = cnt;
            this.words = words;
        }

        boolean isNumber(String str) {
            char[] arr = str.toCharArray();
            for (char c : arr) if (c < '0' || c > '9') return false;
            return true;
        }

        @Override
        public int compareTo(Node node) {
            List<String> words2 = node.words;
            int sz1 = words.size(), sz2 = words2.size();
            int i;
            for (i = 1; i < Math.min(sz1, sz2); ++i) {
                String s1 = words.get(i), s2 = words2.get(i);
                if (s1.equals(s2)) continue;

                boolean n1 = isNumber(s1), n2 = isNumber(s2);
                if (n2) return -1;
                if (n1) return 1;

                return s1.compareTo(s2);
            }
            if (i < sz1) return 1;
            if (i < sz2) return -1;

            return words.get(0).compareTo(words2.get(0));
        }
    }

    Node genNode(String log) {
        List<String> tmp = new ArrayList<>();
        String[] words = log.split(" ");
        Collections.addAll(tmp, words);
        return new Node(log, tmp);
    }

    public String[] reorderLogFiles(String[] logs) {
        List<Node> nodeList = new ArrayList<>();
        for (String log : logs) {
            nodeList.add(genNode(log));
        }

        nodeList.sort(Node::compareTo);

        int n = logs.length;
        String[] ans = new String[n];

        for (int i = 0; i < n; ++i) ans[i] = nodeList.get(i).content;

        return ans;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("".split(", ").length);
    }
}
