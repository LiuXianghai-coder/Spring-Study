package com.example.demo.entity;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

    public int[] exclusiveTime(int n, List<String> logs) {
        int[] ans = new int[n];
        List<String[]> list = new ArrayList<>();
        for (String log : logs) {
            list.add(log.split(":"));
        }
        list.sort((a, b) -> {
            Integer t1 = Integer.parseInt(a[2]);
            Integer t2 = Integer.parseInt(b[2]);
            return t1.compareTo(t2);
        });
        Deque<String[]> stack = new ArrayDeque<>();
        int[] map = new int[20];
        int lv = -1;
        for (String[] ss : list) {
            String state = ss[1];
            if ("start".equals(state)) {
                stack.push(ss);
                lv += 1;
                map[lv] = 0;
                continue;
            }

            String[] pop = stack.pop();
            int id = Integer.parseInt(ss[0]);
            int et = Integer.parseInt(ss[2]);
            int st = Integer.parseInt(pop[2]);
            ans[id] += et - st + 1 - map[lv + 1];
            map[lv] += et - st + 1;
            lv -= 1;
        }
        return ans;
    }

    public boolean validPartition(int[] nums) {
        int n = nums.length;

        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        dp[2] = nums[0] == nums[1];
        for (int i = 2, j = 3; i < n; ++i, ++j) {
            boolean tmp = false;
            // 3 is valid ?
            if ((nums[i] == nums[i - 1] && nums[i - 1] == nums[i - 2])
                    || (nums[i] - 1 == nums[i - 1] && nums[i - 1] - 1 == nums[i - 2])
            ) {
                tmp = dp[j - 3];
            }
            // 2 is valid ?
            if (nums[i - 1] == nums[i]) {
                tmp |= dp[j - 2];
            }
            dp[j] = tmp;
        }

        return dp[n];
    }

    int[] nums;
    boolean[] visit;

    public boolean canPartitionKSubsets(int[] _nums, int k) {
        nums = _nums;
        int sum = 0;
        for (int v : nums) sum += v;
        if (sum % k != 0) return false;
        int target = sum / k;
        visit = new boolean[nums.length];

        Arrays.sort(nums);
        for (int i = 0; i < nums.length; ++i) {
            if (visit[i]) continue;
            if (!dfs(i,0, target)) return false;
        }
        return true;
    }

    boolean dfs(int index, int curVal, int target) {
        if (curVal == target) return true;
        visit[index] = true;
        for (int i = nums.length - 1; i >= 0 ; --i) {
            if (visit[i]) continue;
            int tmp = curVal + nums[i];
            if (tmp > target) continue;
            visit[i] = true;
            if (dfs(i, tmp, target)) return true;
            visit[i] = false;
        }
        visit[index] = false;
        return false;
    }

    TireNode[] root = new TireNode[26];

    public int[] sumPrefixScores(String[] words) {
        for (String word : words) add(word);
        int n = words.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = search(words[i]);
        }
        return ans;
    }

    int search(String str) {
        return search(root, str);
    }

    int search(TireNode[] root, String str) {
        if (root == null) return 0;
        if (str == null || str.length() == 0) return 0;
        int ch = str.charAt(0) - 'a';
        return root[ch].cnt + search(root[ch].next, str.substring(1));
    }

    void add(String str) {
        add(root, str);
    }

    void add(TireNode[] root, String str) {
        if (str == null || str.trim().length() == 0) return;
        int ch = str.charAt(0) - 'a';
        if (root[ch] == null) root[ch] = new TireNode();
        root[ch].cnt++;
        add(root[ch].next, str.substring(1));
    }

    static class TireNode {
        int cnt = 0;
        TireNode[] next = new TireNode[26];
    }

    public int kthGrammar(int n, int k) {
        if (n == 1) return 0;
        return recursive(0, n, k);
    }

    int recursive(int t, int n, int k) {
        if (k == 1) return t;
        int cnt = (int) Math.pow(2, n - 1);
        int half = cnt / 2;
        if (k >= half) {
            return recursive(t == 0 ? 1 : 0, n - 1, k - half);
        }
        return recursive(t == 0 ? 1 : 0, n - 1, k);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.kthGrammar(30, 100000));
    }
}
