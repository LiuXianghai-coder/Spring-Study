package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * LPS
 *
 * @author xhliu2
 * @create 2021-08-10 16:13
 **/
public class LPS {
    // A utility function to get max of two integers
    static int max(int x, int y) {
        return Math.max(x, y);
    }

    // Returns the length of the longest
    // palindromic subsequence in seq
    static int lps(String seq) {
        int len = seq.length();
        if (1 == len) return 1;

        char[] array = seq.toCharArray();
        int[][] dp = new int[len][len];

        for (int i = 0; i < len; ++i) dp[i][i] = 1;

        for (int step = 2; step <= len; ++step) {
            for (int i = 0; i <= len - step; ++i) {
                int j = i + step - 1;
                if (array[i] == array[j]) {
                    if (step == 2) dp[i][j] = 2;
                    else dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][len - 1];
    }

    static int cutRod(int[] prices, int len) {
        if (0 == len) return 0;
        if (1 == len) return 1;

        int[] dp = new int[len + 1];
        dp[1] = prices[0];

        for (int i = 2; i <= len; ++i)
            dp[i] = Math.max(prices[i - 1], dp[i - 1] + dp[1]);

        return dp[len];
    }

    /**
     * 得到长度为 n 的木棒能够按照候选的长度组合的最大元素个数
     *
     * @param n : 木棒的长度
     * @param x : 可切成的候选长度 p
     * @param y : 可切成的候选长度 q
     * @param z : 可切成的候选长度 r
     * @return : 能够切分的最大段数
     */
    public static int maximizeCuts(int n, int x, int y, int z) {
        int[] dp = new int[n + 1];

        // 由于有的长度是无法在当前的候选长度集合中进行切分的，因此需要考虑跳过它
        for (int i = 1; i <= n; ++i)
            dp[i] = -1;

        // 边界情况，长度为 0 的木棒对任何候选切分子集切分的段数都为 0
        dp[0] = 0;

        for (int i = 0; i <= n; ++i) {
            // 如果当前访问的长度是无法由现有的组合子集组合，那那么就说明不能被切分，跳过它
            if (dp[i] == -1) continue;

            /*
                如果当前的长度加上备选的切割长度要小于总共的木棒长度，
                那么就可以将组个分割的子段长度加到现有的长度上，同时增加段数
                由于加上的长度结果可能由多种组合情况，因此要找到最大的组合段数
            */
            if (i + x <= n)
                dp[i + x] = Math.max(dp[i + x], dp[i] + 1);
            if (i + y <= n)
                dp[i + y] = Math.max(dp[i + y], dp[i] + 1);
            if (i + z <= n)
                dp[i + z] = Math.max(dp[i + z], dp[i] + 1);
        }

        // 如果目标段数不能由现有的组合段数组合而来，则跳过它
        if (dp[n] == -1) return 0;

        return dp[n];
    }

    public int[] smallestK(int[] arr, int k) {
        int[] ans = new int[k];
        int lo = 0, hi = arr.length - 1;
        int index = partition(arr, lo, hi);

        while (index != k) {
            if (index < k) lo = index + 1;
            else hi = index - 1;

            index = partition(arr, lo, hi);
        }

        System.arraycopy(arr, 0, ans, 0, k);

        return ans;
    }

    private int partition(int[] arr, final int lo, final int hi) {
        if (lo >= hi) return lo;

        int ridx = lo + ((hi - lo) >> 1);
        exchange(arr, lo, ridx);

        int tmp = arr[lo], i = lo, j = hi + 1;
        while (true) {
            while (arr[++i] <= tmp)
                if (i == hi) break;

            while (tmp <= arr[--j])
                if (j == lo) break;

            if (i >= j) break;

            exchange(arr, i, j);
        }

        exchange(arr, lo, j);

        return j;
    }

    private static void exchange(int[] arr, int lo, int hi) {
        int tmp = arr[lo];
        arr[lo] = arr[hi];
        arr[hi] = tmp;
    }

    public int[] smallestK_1(int[] list, int k) {
        if (k >= list.length)
            return list;
        quickSort(list, 0, list.length - 1, k);
        int[] ans = new int[k];
        System.arraycopy(list, 0, ans, 0, k);
        return ans;
    }

    static private void quickSort(int[] list, int l, int r, int k) {
        if (l >= r)
            return;
        int mid = l + ((r - l) >> 1), key = list[mid];
        list[mid] = list[l];
        list[l] = key;
        int i = l, j = r;
        while (i < j) {
            while (i < j && list[j] >= key) --j;
            list[i] = list[j];
            while (i < j && list[i] <= key) ++i;
            list[j] = list[i];
        }
        list[i] = key;
        if (i > k)
            quickSort(list, l, i - 1, k);
        else {
            quickSort(list, i + 1, r, k);
        }
    }

    public String minWindow(String s, String t) {
        final char[] source = s.toCharArray();
        final char[] target = t.toCharArray();

        final Map<Character, Integer> map = new ConcurrentHashMap<>();
        final Map<Character, Integer> tmpMap = new ConcurrentHashMap<>();

        for (char ch : target)
            map.put(ch, map.getOrDefault(ch, 0) + 1);

        int left = 0, right = 0;
        int[] ans = new int[]{-1, 100001};
        while (right < source.length) {
            while (true) {
                if (map.containsKey(source[right]))
                    tmpMap.put(source[right], tmpMap.getOrDefault(source[right], 0) + 1);
                if (equal(map, tmpMap)) break;

                right++;
            }

            while (left <= right && equal(map, tmpMap)) {
                if (map.containsKey(source[left])) {
                    tmpMap.put(source[left], tmpMap.getOrDefault(source[left], 0) - 1);
                    left++;
                    break;
                }
                left++;
            }

            System.out.printf("left: %d\tright:%d\n", left, right);

            if (right - left < ans[1] - ans[0]) {
                ans[0] = left - 1;
                ans[1] = right;
            }

            right++;
        }

        if (ans[0] < 0) return "";

        return s.substring(ans[0], ans[1] + 1);
    }

    private boolean equal(Map<Character, Integer> map, Map<Character, Integer> tmpMap) {
        for (Character key : map.keySet()) {
            if (map.get(key) > tmpMap.getOrDefault(key, 0))
                return false;
        }

        return true;
    }

    private final int[] map = new int[127];
    private final int[] tmpMap = new int[127];

    public boolean checkInclusion(String s1, String s2) {
        if (s2.length() < s1.length())
            return false;

        final char[] s1Array = s1.toCharArray();
        final char[] s2Array = s2.toCharArray();

        for (char ch : s1Array)
            map[ch]++;
        int flagNum = IntStream.of(map).sum();

        int N = s2Array.length;
        int left = 0, right = 0;
        int tmpFlagNum = 0;
        while (right < N) {
            while (right < N && tmpFlagNum < flagNum) {
                char ch = s2Array[right];
                if (map[ch] > 0 && tmpMap[ch] < map[ch]) {
                    tmpMap[ch]++;
                    tmpFlagNum++;
                }
                right++;
            }

            if (tmpFlagNum != flagNum) break;

            while (right - left > s1Array.length) {
                char ch = s2Array[left];
                if (tmpMap[ch] > 0) {
                    tmpMap[ch]--;
                    tmpFlagNum--;
                }

                left++;
            }

            if (right - left == s1Array.length) {
                if (tmpFlagNum == flagNum)
                    return true;
            }
        }

        return false;
    }

    private String mcs(String s, String t) {
        if (s.length() < t.length()) return "";

        char[] source = s.toCharArray();
        char[] target = t.toCharArray();

        final int N = source.length;

        final int[] map = new int[128];
        final int[] tmpMap = new int[128];
        for (char ch : target) map[ch]++;

        int left = 0, right = 0;
        final int[] ans = new int[]{-1, Integer.MAX_VALUE - 2};
        while (right < N) {
            tmpMap[source[right]]++;

            while (left <= right && check(map, tmpMap)) {
                tmpMap[source[left]]--;

                if (right - left < ans[1] - ans[0]) {
                    ans[0] = left;
                    ans[1] = right;
                }

                left++;
            }

            right++;
        }

        if (ans[0] == -1) return "";

        return s.substring(ans[0], ans[1] + 1);
    }

    private boolean check(int[] map, int[] tmpMap) {
        for (int i = 0; i < map.length; ++i) {
            if (tmpMap[i] < map[i]) return false;
        }

        return true;
    }

    private void wrapLine(String[] words, final int width) {
        int N = words.length;

        int[] l = new int[N];
        for (int i = 0; i < N; i++) {
            l[i] = words[i].length();
        }

        final int[][] lc = new int[N + 1][N + 1];
        final int[][] extra = new int[N + 1][N + 1];
        final int[] C = new int[N + 1];
        final int[] p = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            extra[i][i] = width - l[i - 1];
            for (int j = i + 1; j <= N; j++) {
                extra[i][j] = extra[i][j - 1] - l[j - 1] - 1;
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = i; j <= N; j++) {
                if (extra[i][j] < 0) {
                    lc[i][j] = Integer.MAX_VALUE;
                } else if (j == N && extra[i][j] >= 0) {
                    lc[i][j] = 0;
                } else {
                    lc[i][j] = extra[i][j] * extra[i][j];
                }
            }
        }

        C[0] = 0;
        for (int j = 1; j <= N; j++) {
            C[j] = Integer.MAX_VALUE;
            for (int i = 1; i <= j; i++) {
                if (
                        C[i - 1] != Integer.MAX_VALUE
                                && lc[i][j] != Integer.MAX_VALUE
                                && C[i - 1] + lc[i][j] < C[j]
                ) {
                    C[j] = C[i - 1] + lc[i][j];
                    p[j] = i;
                }
            }
        }

        printSolution(words, p, N);
    }

    private int printSolution(String[] words, int[] p, int n) {
        int k;
        if (p[n] == 1)
            k = 1;
        else
            k = printSolution(words, p, p[n] - 1) + 1;

        for (int i = p[n]; i <= n; i++) {
            System.out.print(words[i - 1] + " ");
        }
        System.out.println();

        return k;
    }


    public List<Integer> findAnagrams(String s, String p) {
        final List<Integer> ans = new ArrayList<>();
        if (s.length() < p.length())
            return ans;

        final char[] srcArray = p.toCharArray();
        for (char ch : srcArray)
            map[ch]++;

        final char[] tarArray = s.toCharArray();
        final int N = p.length();
        for (int left = 0, right = left + N - 1;
             right < tarArray.length; ++right, ++left) {

            System.out.printf("left: %d\tright:%d\tisValid:%b\n",
                    left, right, isValid(tarArray, left, right));
            if (isValid(tarArray, left, right)) {
                ans.add(left);
            }
        }

        return ans;
    }

    private boolean isValid(char[] tarArray, final int left, final int right) {
        final int[] tmpMap = new int[127];
        for (int i = left; i <= right; ++i) {
            tmpMap[tarArray[i]]++;
        }

        for (int i = 0; i < tmpMap.length; ++i) {
            if (tmpMap[i] < map[i])
                return false;
        }

        return true;
    }

    /* Driver program to test above functions */
    public static void main(String[] args) {
        LPS application = new LPS();

        System.out.println(application.findAnagrams("cbaebabacd", "abc").toString());
    }
}
