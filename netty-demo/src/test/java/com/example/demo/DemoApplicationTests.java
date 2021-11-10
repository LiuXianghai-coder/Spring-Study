package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class DemoApplicationTests {
    private static int longestPalindromicSequence(String sequence) {
        int len = sequence.length();
        char[] string = sequence.toCharArray();
        int[][] dp = new int[len][len];

        int i, cl, j;
        for (i = 0; i < len; ++i) {
            dp[i][i] = 1;
        }

        for (cl = 2; cl <= len; ++cl) {
            for (i = 0; i < len - cl + 1; ++i) {
                j = i + cl - 1;
                if (string[i] == string[j] && cl == 2) dp[i][j] = 2;
                else if (string[i] == string[j]) dp[i][j] = dp[i + 1][j - 1] + 2;
                else dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
            }
        }

        return dp[0][len - 1];
    }

    public static int countDigitOne(int n) {
        int ans = 0;
        int digit = 1;

        while (digit <= n) {
            ans += (n / (digit * 10)) * digit
                    + Math.min(Math.max(n % (digit * 10) - digit + 1, 0), digit);

            digit *= 10;
        }

        return ans;
    }

    public static int lbs(int[] array) {
        int len = array.length;

        if (1 == len) return 1;

        int[] lis = new int[len];
        int[] lds = new int[len];

        for (int i = 0; i < len; ++i) {
            lis[i] = 1;
            lds[i] = 1;
        }

        for (int i = 1; i < len; ++i) {
            for (int j = 0; j < i; ++j) {
                if (array[i] > array[j])
                    lis[i] = Math.max(lis[i], lis[j] + 1);
            }
        }

        for (int i = len - 2; i >= 0; --i) {
            for (int j = len - 1; j > i; --j) {
                if (array[i] > array[j])
                    lds[i] = Math.max(lds[i], lds[j] + 1);
            }
        }

        int ans = 1;
        for (int i = 0; i < len; ++i) {
            ans = Math.max(ans, lis[i] + lds[i] - 1);
        }

        return ans;
    }

    public int numIslands(char[][] grid) {
        int ans = 0;

        int row = grid.length;
        int col = grid[0].length;

        boolean[][] mark = new boolean[row][col];
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (!mark[i][j] && grid[i][j] == '1') {
                    printArray(mark);
                    ans++;
                    dfs(grid, mark, i, j);
                }
            }
        }

        return ans;
    }

    private void printArray(boolean[][] val) {
        int col = val[0].length;

        for (boolean[] booleans : val) {
            for (int j = 0; j < col; ++j) {
                System.out.print(booleans[j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void dfs(char[][] grid, boolean[][] mark, int rowIndex, int colIndex) {
        if (mark[rowIndex][colIndex]) return;

        int row = grid.length, col = grid[0].length;

        mark[rowIndex][colIndex] = true;
        if (rowIndex < row - 1 && grid[rowIndex + 1][colIndex] == '1')
            dfs(grid, mark, rowIndex + 1, colIndex);

        if (rowIndex > 0 && grid[rowIndex - 1][colIndex] == '1')
            dfs(grid, mark, rowIndex - 1, colIndex);

        if (colIndex < col - 1 && grid[rowIndex][colIndex + 1] == '1')
            dfs(grid, mark, rowIndex, colIndex + 1);

        if (colIndex > 0 && grid[rowIndex][colIndex - 1] == '1')
            dfs(grid, mark, rowIndex, colIndex - 1);
    }

    public static int editDistance(String s1, String s2) {
        int lenS1 = s1.length(), lenS2 = s2.length();

        if (lenS2 == 0) return lenS1;
        if (lenS1 == 0) return lenS2;

        if (s1.charAt(0) == s2.charAt(0))
            return editDistance(s1.substring(1), s2.substring(1));

        return Math.min(
                editDistance(s1.substring(1), s2.substring(1)),
                Math.min(
                        editDistance(s1, s2.substring(1)),
                        editDistance(s1.substring(1), s2)
                )
        ) + 1;
    }

    public static int editDistanceDp(String s1, String s2) {
        int lenS1 = s1.length(), lenS2 = s2.length();
        int[][] dp = new int[lenS1 + 1][lenS2 + 1];

        for (int i = 0; i <= lenS1; ++i) {
            for (int j = 0; j <= lenS2; ++j) {
                if (0 == i) dp[i][j] = j;
                else if (0 == j) dp[i][j] = i;
                else if (s1.charAt(i - 1) == s2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(
                            dp[i - 1][j],
                            Math.min(
                                    dp[i - 1][j - 1],
                                    dp[i][j - 1]
                            )
                    ) + 1;
            }
        }

        return dp[lenS1][lenS2];
    }

    public static int minCostPath(int[][] cost, int m, int n) {
        if (m < 0 || n < 0)
            return Integer.MAX_VALUE;
        if (m == 0 && n == 0)
            return cost[m][n];

        return Math.min(
                minCostPath(cost, m, n - 1),
                Math.min(
                        minCostPath(cost, m - 1, n - 1),
                        minCostPath(cost, m - 1, n)
                )
        ) + cost[m][n];
    }

    public static int minCostPathDP(int[][] cost, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        dp[0][0] = cost[0][0];
        for (int i = 1; i <= m; ++i) dp[i][0] = cost[i][0] + dp[i - 1][0];
        for (int i = 1; i <= n; ++i) dp[0][i] = cost[0][i] + dp[0][i - 1];

        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                dp[i][j] = Math.min(
                        dp[i][j - 1],
                        Math.min(
                                dp[i - 1][j],
                                dp[i - 1][j - 1]
                        )
                ) + cost[i][j];
            }
        }

        return dp[m][n];
    }

    // ################################ Coin Exchange ######################################
    public static int coinExchangeRecur(int[] values, int index, int target) {
        if (target == 0) return 1;

        if (target < 0) return 0;

        if (index < 0) return 0;

        return coinExchangeRecur(values, index, target - values[index])
                + coinExchangeRecur(values, index - 1, target);
    }

    public static int coinExchange(int[] coins, int target) {
        int N = coins.length;
        int[][] dp = new int[N + 1][target + 1];

        for (int i = 1; i <= N; ++i) {
            for (int j = 0; j <= target; ++j) {
                if (j == 0) {
                    dp[i][j] = 1;
                    continue;
                }
                if (j >= coins[i - 1])
                    dp[i][j] += dp[i][j - coins[i - 1]];
                dp[i][j] += dp[i - 1][j];
            }
        }

        return dp[N][target];
    }
    // ############################### Coin Exchange End #######################################


    /* ############################### Min Matrix Chain Multiplication ####################################### */
    public static int minMultiMatrixRecur(int[] values, int i, int j) {
        if (i == j) return 0;

        int ans = Integer.MAX_VALUE;
        for (int k = i; k < j; ++k) {
            ans = Math.min(
                    ans,
                    minMultiMatrixRecur(values, i, k)
                            + minMultiMatrixRecur(values, k + 1, j)
                            + values[i - 1] * values[k] * values[j]
            );
        }

        return ans;
    }

    public static int minMultiMatrix(int[] values, int n) {
        int[][] dp = new int[n][n];

        int j;
        for (int L = 2; L < n; ++L) {
            for (int i = 1; i < n - L + 1; ++i) {
                j = i + L - 1;

                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; ++k)
                    dp[i][j] = Math.min(
                            dp[i][j],
                            dp[i][k] + dp[k + 1][j] + values[i - 1] * values[k] * values[j]
                    );
            }
        }

        return dp[1][n - 1];
    }
    /* ############################### Min Matrix Chain Multiplication End ####################################### */

    static long nCr(int n, int r) {
        if (r > n) return 0;

        if (n == r) return 1;
        if (r == 0) return 1;

        long ans = 1L;
        for (int i = 1; i <= r; ++i) {
            ans *= n - i + 1;
            ans /= i;
        }

        return ans;
    }

    /* ############################# 0-1 Bag Problem ##################################### */
    public static int bagProblem(int[] values, int[] weight, int capacity) {
        int len = values.length;
        int[][] dp = new int[len + 1][capacity + 1];

        for (int i = 1; i <= len; ++i) {
            for (int j = 1; j <= capacity; ++j) {
                if (weight[i - 1] > j)
                    dp[i][j] = dp[i - 1][j];
                else dp[i][j] = Math.max(
                        dp[i - 1][j],
                        values[i - 1] + dp[i - 1][j - weight[i - 1]]
                );
            }
        }

        return dp[len][capacity];
    }

    public static int bagProblemReverse(int[] values, int[] weight, int capacity) {
        int len = values.length;
        int[][] dp = new int[capacity + 1][len + 1];

        for (int i = 1; i <= capacity; ++i) {
            for (int j = 1; j <= len; ++j) {
                if (weight[j - 1] > i)
                    dp[i][j] = dp[i][j - 1];
                else
                    dp[i][j] = Math.max(
                            dp[i][j - 1],
                            dp[i - weight[j - 1]][j - 1] + values[j - 1]
                    );
            }
        }

        return dp[capacity][len];
    }

    public static int bagProblemOp(int[] values, int[] weight, int capacity) {
        int[] dp = new int[capacity + 1];
        int len = values.length;

        for (int i = 1; i <= len; ++i) {
            for (int j = capacity; j >= 0; --j) {
                if (weight[i - 1] <= j)
                    dp[j] = Math.max(
                            dp[j],
                            dp[j - weight[i - 1]] + values[i - 1]
                    );
            }
        }

        return dp[capacity];
    }

    /**
     * 根据当前的物品信息和背包的容量信息得到能够得到的最大收益
     *
     * @param values   ： 当前可选物品的价值信息列表
     * @param weight   ： 当前可选物品的重量信息列表
     * @param capacity ：当前背包的可用容量
     * @param index    ： 当前待选择的物品的位置索引，类似游标
     * @return ：当前条件下能够得到的最大收益
     */
    public static int knapSack(int[] values, int[] weight, int capacity, int index) {
        // 边界条件，对于背包容量为 0 或者当前无可选物品时，达到终止条件
        if (0 == capacity || 0 > index)
            return 0;

        // 如果当前选取的物品的重量大于背包当前的可用容量，那么就不能添加该物品了
        if (weight[index] > capacity)
            return knapSack(values, weight, capacity, index - 1);

        return Math.max(
                // 假设当前选取的物品是在最优子结构中的，那么把它放入背包然后与不放入背包的情况后的结果进行比较
                values[index]
                        + knapSack(values, weight, capacity - weight[index], index - 1),
                knapSack(values, weight, capacity, index - 1) // 当前的物品不放入背包
        );
    }

    /* ############################# 0-1 Bag Problem End ##################################### */

    /* ############################# EggDrop End ##################################### */

    /**
     * 通过递归的方式来获取需要丢鸡蛋的此时
     *
     * @param n ： 手上可用的鸡蛋个数
     * @param k ： 当前需要处理的楼层的高度
     * @return ： 至少需要丢鸡蛋的次数
     */
    public static int eggDropRecur(int n, int k) {
        /*
            如果当前需要考虑的楼层数量为 1 或 0，如果为 0 的情况，那么就不需要再丢鸡蛋了，因此直接返回 0；
            而如果楼层的数量为 1，那么只需要再丢一次即可得到确切的结果。
            因此直接返回 k 的楼层数
        */
        if (1 == k || 0 == k) return k;

        /*
         如果手上只有一枚鸡蛋可以使用了，那么就只能从上往下一层一层地丢了，因此也是返回楼层的数量 k。
        */
        if (1 == n) return k;

        int min = Integer.MAX_VALUE;

        /*
         * 从第一层开始不断地尝试，考虑出现的所有情况，得到需要的最小尝试次数
         *
         * x 是当前的试验楼层
         */
        for (int x = 1; x <= k; ++x) {
            min = Math.min( // 在不同的楼层丢鸡蛋可能会有不同的结果，这里取最小值即可
                    min,
                    Math.max( // 这里的 max 是因为只有得到最大值才能确保能够得到最终的结果
                            eggDropRecur(n - 1, x - 1), // 当前楼层丢的鸡蛋破了的情况
                            eggDropRecur(n, k - x) // 当前楼层丢的鸡蛋没有破的情况
                    )
            );
        }

        return min + 1; // +1 是因为当前在当前楼层丢了一次鸡蛋
    }

    /**
     * 使用动态规划的方式解决鸡蛋掉落问题
     *
     * @param n : 给予测试的鸡蛋总数
     * @param k : 当前测试的建筑物的高度
     */
    public static int eggDropDP(int n, int k) {
        // 存储状态的二维数组
        int[][] dp = new int[n + 1][k + 1];

        /*
         * 初始化状态数组的边界值，
         *
         * 对于 0 个鸡蛋的情况，无法通过丢鸡蛋得到确切的临界楼层，因此无须考虑这种情况
         *
         * 对于建筑物的楼层为 0 的情况，无须通过丢鸡蛋得到确切的临界楼层，因此实验次数为 0
         *
         * 对于只有一个鸡蛋的情况，要得到确切的临界楼层只能从第一层依次向上不断丢鸡蛋得到临界楼层
         */
        for (int i = 1; i <= n; ++i) {
            dp[i][1] = 1;
            dp[i][0] = 0;
        }

        for (int j = 1; j <= k; ++j) {
            dp[1][j] = j;
            dp[0][j] = 0;
        }
        // 初始化状态二维数组结束

        int ans; // 存储计算过程中的最小值，即为最终的结果

        /*
          i 代表鸡蛋的个数，j 代表当前要测试的楼层总高度，x 代表丢鸡蛋时出现结果的测试楼层高度
        */
        for (int i = 2; i <= n; ++i) {
            for (int j = 2; j <= k; ++j) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int x = 1; x <= j; ++x) {
                    ans = 1 + Math.max(dp[i - 1][x - 1], dp[i][j - x]);

                    if (ans < dp[i][j]) dp[i][j] = ans;
                }
            }
        }

        // 由于状态转换的关系，最终可以得到有 n 个鸡蛋、k 层楼的情况下需要实验的最下次数
        return dp[n][k];
    }

    private static int binomial(int x, int n, int k) {
        int val = 1, sum = 0; // 二项式系数总和

        /*
         * 由于在这个问题中只需要判断是否能够覆盖整层楼，
         * 因此无需计算整个二项式系数总和的具体值
         */
        for (int i = 1; i <= n && sum < k; ++i) {
            val *= x - i + 1;
            val /= i;

            sum += val;
        }

        return sum;
    }

    /**
     * 使用二项式系数和二分查找解决鸡蛋掉落问题
     * <p>
     * 不管手上由多少个鸡蛋，总的实验次数不会超过楼层的总高度
     * 同样的，不可能一次实验都不做就直接得到实验总数，
     * 由于楼层的高度是一个有序的，因此可以采用二分查找的方式解决
     *
     * @param n : 能够实验的鸡蛋的总个数
     * @param k : 试验的建筑物的总层数
     */
    int eggDropBinomial(int n, int k) {
        int lo = 1, hi = k;
        int mid = 0;

        while (lo < hi) {
            mid = (lo + hi) / 2;
            if (binomial(mid, n, k) < k) lo = mid + 1; // 当前的试验次数不能覆盖整个楼层，因此必须加一
            else hi = mid; // 当前的试验次数可以覆盖整个楼层，因此进一步细化试验次数范围
        }

        return lo;
    }

    /* ############################# EggDrop End ##################################### */

    /* ############################# minPalPartition ##################################### */
    public static int minPalPartitionRecur(String str, int i, int j) {
        if (i == j) return 0;
        if (isPalindrome(str, i, j)) return 0;

        int ans = Integer.MAX_VALUE;
        for (int k = i; k < j; ++k) {
            ans = Math.min(
                    ans,
                    minPalPartitionRecur(str, i, k)
                            + minPalPartitionRecur(str, k + 1, j)
                            + 1
            );
        }

        return ans;
    }

    public static int minPalPartitionDp(String str) {
        int len = str.length();

        int[][] dp = new int[len][len];
        boolean[][] mark = new boolean[len][len];

        for (int i = 0; i < len; ++i)
            mark[i][i] = true;

        for (int L = 2; L <= len; ++L) {
            for (int i = 0; i < len - L + 1; ++i) {
                int j = i + L - 1;
                final boolean compare = str.charAt(i) == str.charAt(j);
                if (L == 2)
                    mark[i][j] = compare;
                else
                    mark[i][j] = (compare && mark[i + 1][j - 1]);

                if (mark[i][j]) {
                    dp[i][j] = 0;
                    continue;
                }

                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; ++k) {
                    dp[i][j] = Math.min(
                            dp[i][j],

                            dp[i][k]
                                    + dp[k + 1][j]
                                    + 1
                    );
                }
            }
        }

        return dp[0][len - 1];
    }

    public static int minPalPartitionDpOp(String str) {
        int len = str.length();
        int[] dp = new int[len];
        boolean[][] mark = new boolean[len][len];

        for (int i = 0; i < len; ++i) {
            int minCut = i;
            for (int j = 0; j <= i; ++j) {
                if (
                        str.charAt(i) == str.charAt(j) &&
                                (i - j < 2 || mark[j + 1][i - 1])
                ) {
                    mark[j][i] = true;
                    minCut = Math.min(minCut, j == 0 ? 0 : (dp[j - 1] + 1));
                }
            }
            dp[i] = minCut;
        }

        return dp[len - 1];
    }


    private static boolean isPalindrome(String str, int i, int j) {
        if (i == j) return true;

        while (i <= j) {
            if (str.charAt(i) != str.charAt(j))
                return false;
            i++;
            j--;
        }

        return true;
    }

    private static int minCut(String str) {
        int len = str.length();
        char[] strArray = str.toCharArray();
        int[] minCuts = new int[len];

        for (int i = 0; i < len; ++i)
            minCuts[i] = i;

        for (int i = 0; i < len; ++i) {
            expansionCenter(strArray, i, i, minCuts);
            expansionCenter(strArray, i, i + 1, minCuts);
        }

        return minCuts[len - 1];
    }

    private static void expansionCenter(char[] strArray, int left, int right, int[] minCuts) {
        while (left >= 0 && right < strArray.length && strArray[left] == strArray[right]) {
            int i = left--, j = right++;

            if (i == 0)
                minCuts[j] = 0;
            else
                minCuts[j] = Math.min(minCuts[j], minCuts[i - 1] + 1);
        }
    }

    public boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        int[] count = new int[101];
        int sum = 0;
        int max = 0;
        for (int v : nums) {
            sum += v;
            if (v > max) {
                max = v;
            }
            ++count[v];
        }
        if ((sum & 1) == 1 || max > (sum >>= 1)) {
            return false;
        }
        int index = 0;
        for (int i = 1; i <= max; i++) {
            for (int j = 0; j < count[i]; j++) {
                nums[index++] = i;
            }
            if (index >= n) {
                break;
            }
        }

        int first = sum;
        int second = sum;

        for (int i = n - 1; i >= 0; --i) {
            if (first < nums[i]) {
                second -= nums[i];
            } else {
                first -= nums[i];
            }
        }
        if (first == second) {
            return true;
        }

        first = second = 0;
        for (int i = n - 1; i >= 0; --i) {
            if (first < second) {
                first += nums[i];
            } else {
                second += nums[i];
            }
        }

        return first == second;
    }
    /* ############################# minPalPartition End ##################################### */


    /* ############################# Partition problem ##################################### */
    public static boolean isSubsetSum(int[] arr, int n, int target) {
        if (target == 0) return true;
        if (n < 0) return false;

        if (arr[n] > target)
            return isSubsetSum(arr, n - 1, target);

        return isSubsetSum(arr, n - 1, target) || isSubsetSum(arr, n - 1, target - arr[n]);
    }

    public static boolean findPartition(int[] arr) {
        int sum = 0;
        for (int v : arr) sum += v;

        if ((sum & 1) != 0)
            return false;

        return isSubsetSum(arr, arr.length - 1, sum / 2);
    }

    public static boolean findPartitionDp(int[] arr) {
        int sum = 0;
        for (int v : arr) sum += v;

        if ((sum & 1) != 0)
            return false;

        int len = arr.length;
        int target = sum / 2;
        boolean[][] dp = new boolean[len + 1][target + 1];

        for (int i = 0; i <= len; ++i)
            dp[i][0] = true;

        for (int i = 1; i <= len; ++i) {
            for (int j = 1; j <= target; ++j) {
                dp[i][j] = dp[i - 1][j];
                if (arr[i - 1] <= j) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - arr[i - 1]];
                }
            }
        }

        return dp[len][target];
    }
    /* ############################# Partition problem End ##################################### */

    public int solveWordWrap(int[] nums, int k) {
        // Code here
        int n = nums.length;
        int i, j;

        // Variable to store
        // number of characters
        // in given line.
        int currlen;

        // Variable to store
        // possible minimum
        // cost of line.
        int cost;

        // DP table in which
        // dp[i] represents
        // cost of line starting
        // with word arr[i].
        int dp[] = new int[n];

        // Array in which ans[i]
        // store index of last
        // word in line starting
        // with word arr[i].
        int ans[] = new int[n];

        // If only one word is present
        // then only one line is required.
        // Cost of last line is zero.
        // Hence cost of this line is zero.
        // Ending point is also n-1 as
        // single word is present.
        dp[n - 1] = 0;
        ans[n - 1] = n - 1;

        // Make each word first
        // word of line by iterating
        // over each index in arr.
        for (i = n - 2; i >= 0; i--) {
            currlen = -1;
            dp[i] = Integer.MAX_VALUE;

            // Keep on adding words in
            // current line by iterating
            // from starting word upto
            // last word in arr.
            for (j = i; j < n; j++) {

                // Update number of characters
                // in current line. arr[j] is
                // number of characters in
                // current word and 1
                // represents space character
                // between two words.
                currlen += (nums[j] + 1);

                // If limit of characters
                // is violated then no more
                // words can be added to
                // current line.
                if (currlen > k)
                    break;

                // If current word that is
                // added to line is last
                // word of arr then current
                // line is last line. Cost of
                // last line is 0. Else cost
                // is square of extra spaces
                // plus cost of putting line
                // breaks in rest of words
                // from j+1 to n-1.
                if (j == n - 1)
                    cost = 0;
                else
                    cost = (k - currlen) *
                            (k - currlen) +
                            dp[j + 1];

                // Check if this arrangement
                // gives minimum cost for
                // line starting with word
                // arr[i].
                if (cost < dp[i]) {
                    dp[i] = cost;
                    ans[i] = j;
                }
            }
        }
        int res = 0;
        i = 0;
        while (i < n) {
            int pos = 0;
            for (j = i; j < ans[i] + 1; j++) {
                pos += nums[j];
            }
            int x = ans[i] - i;
            if (ans[i] + 1 != n)
                res += (k - x - pos) * (k - x - pos);
            i = ans[i] + 1;
        }
        return res;
    }

    public static long count(int n) {
        long[] dp = new long[n + 1];
        Arrays.fill(dp, 0);
        dp[0] = 1;

        // Add your code here.
        for (int i = 3; i <= n; ++i)
            dp[i] += dp[i - 3];
        for (int j = 5; j <= n; ++j)
            dp[j] += dp[j - 5];
        for (int k = 10; k <= n; ++k)
            dp[k] += dp[k - 10];

        System.out.println(Arrays.toString(dp));

        return dp[n];
    }

    public static int coinChange(int[] coins, int amount) {
        return coinChange(coins, amount, 0);
    }

    public static int coinChange(int[] coins, int amount, int num) {
        int[] dp = new int[amount + 1];

        for (int i = 1; i <= amount; ++i) {
            dp[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i >= coin) {
                    if (dp[i - coin] != Integer.MAX_VALUE) {
                        dp[i] = Math.min(dp[i - coin] + 1, dp[i]);
                    }
                }
            }
        }

        System.out.println(Arrays.toString(dp));

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    public static int minFallingPathSum(int[][] matrix) {
//        return minFallingPathSum(matrix, 0, 0);
        int R = matrix.length;
        int C = matrix[0].length;

        int[][] dp = new int[R + 1][C + 2];

        for (int i = 0; i <= R; ++i) {
            dp[i][0] = dp[i][C + 1] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= R; ++i) {
            for (int j = 1; j <= C; ++j) {
                dp[i][j] = Math.min(
                        dp[i - 1][j],
                        Math.min(
                                dp[i - 1][j - 1],
                                dp[i - 1][j + 1]
                        )
                ) + matrix[i - 1][j - 1];
            }
        }

        for (int i = 0; i <= R; ++i) {
            for (int j = 0; j <= C + 1; ++j) {
                System.out.print(dp[i][j] + "\t");
            }
            System.out.println();
        }

        int ans = Integer.MAX_VALUE;
        for (int j = 0; j <= C + 1; ++j) {
            ans = Math.min(dp[R][j], ans);
        }

        return ans;
    }

    private static int minFallingPathSum(int[][] matrix, int row, int col) {
        int R = matrix.length, C = matrix[0].length;

        if (col < 0 || col >= C) return 1000000;
        if (row == R - 1) return matrix[row][col];

        int tmp = Math.min(
                minFallingPathSum(matrix, row + 1, col - 1),
                Math.min(
                        minFallingPathSum(matrix, row + 1, col),
                        minFallingPathSum(matrix, row + 1, col + 1)
                )
        ) + matrix[row][col];

        return Math.min(tmp, minFallingPathSum(matrix, row, col + 1));
    }

    public static int rob(int[] nums) {
        int N = nums.length;
        if (0 == N) return 0;
        if (1 == N) return nums[0];
        if (2 == N) return Math.max(nums[0], nums[1]);

        int[] dp = new int[N];
        dp[0] = Math.min(nums[0], nums[1]);
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < N; ++i) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        System.out.println(Arrays.toString(dp));

        return dp[N - 1];
    }

    public static int superEggDrop(int k, int n) {
        if (k == 1 || n <= 1) return n;
        final double result = Math.log(n) / Math.log(2);

        if ((n & (n - 1)) == 0) return 1 + (int) (result);

        return (int) Math.ceil(result);
    }

    public int networkDelayTime(int[][] times, int n, int k) {
        final int INF = Integer.MAX_VALUE / 2;
        List<int[]>[] g = new ArrayList[n];

        for (int i = 0; i < n; ++i) {
            g[i] = new ArrayList<>();
        }
        for (int[] t : times) {
            g[t[0] - 1].add(new int[]{t[1] - 1, t[2]});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[k - 1] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>(
                (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]
        );
        queue.offer(new int[]{0, k - 1});

        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            int time = p[0], x = p[1];

            if (dist[x] < time) {
                continue;
            }

            for (int[] e : g[x]) {
                int y = e[0], d = dist[x] + e[1];
                if (d < dist[y]) {
                    dist[y] = d;
                    queue.offer(new int[]{d, y});
                }
            }
        }

        int ans = Arrays.stream(dist).max().getAsInt();
        return ans == INF ? -1 : ans;
    }

    private static int differStr(String s1, String s2) {
        int result = 0;
        for (int i = 0; i < s1.length(); ++i) {
            if (s1.charAt(i) != s2.charAt(i)) result++;
        }

        return result;
    }

    private final static Set<String> set = new HashSet<>();

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        set.addAll(wordList);

        if (!set.contains(endWord)) return 0;

        int ans = bfs(beginWord, endWord);

        return ans == -1 ? 0 : ans + 1;
    }

    private static int bfs(final String start, final String end) {
        Deque<String> d1 = new ArrayDeque<>();
        Deque<String> d2 = new ArrayDeque<>();
        d1.offer(start);
        d2.offer(end);

        Map<String, Integer> m1 = new HashMap<>();
        Map<String, Integer> m2 = new HashMap<>();
        m1.put(start, 0);
        m2.put(end, 0);

        while (!d1.isEmpty() && !d2.isEmpty()) {
            int t;
            if (d1.size() <= d2.size()) {
                t = update(d1, m1, m2);
            } else {
                t = update(d2, m2, m1);
            }

            if (t != -1) return t;
        }

        return -1;
    }

    private final List<List<Integer>> ans = new ArrayList<>();

    private void permute(int[] nums, int index, List<Integer> list) {
        int n = nums.length;

        if (index == n - 1) {
            System.out.println();
            ans.add(new ArrayList<>(list));
            return;
        }

        for (int num : nums) {
            if (list.contains(num))
                continue;
            list.add(num);
            permute(nums, index + 1, list);

            list.remove(Integer.valueOf(num));
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int n = nums.length;
        for (int num : nums) {
            list.add(num);
            permute(nums, 0, list);
            list.remove(Integer.valueOf(num));
        }

        return ans;
    }

    private static int update(Deque<String> deque, Map<String, Integer> cur, Map<String, Integer> other) {
        String e = deque.poll();

        assert e != null;
        int n = e.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < 26; ++j) {
                sb.delete(0, sb.length());
                sb.append(e, 0, i).append((char) ('a' + j)).append(e, i + 1, n);

                if (!set.contains(sb.toString())) continue;

                if (cur.containsKey(sb.toString())) continue;

                if (other.containsKey(sb.toString()))
                    return cur.get(e) + 1 + other.get(sb.toString());

                deque.offer(sb.toString());
                cur.put(sb.toString(), cur.get(e) + 1);
            }
        }

        return -1;
    }

    private final List<List<String>> result = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                board[i][j] = '.';
            }
        }
        backTrace(board, 0, n);

        return result;
    }

    private void backTrace(char[][] board, int row, final int n) {
        if (row == n) {
            List<String> tmp = new ArrayList<>();
            for (char[] rowInfo : board)
                tmp.add(String.valueOf(rowInfo));

            result.add(tmp);
            return;
        }

        for (int col = 0; col < n; ++col) {
            if (!isValid(board, row, col, n))
                continue;

            board[row][col] = 'Q';
            backTrace(board, row + 1, n);
            board[row][col] = '.';
        }
    }

    public String minWindow(String s, String t) {
        final char[] source = s.toCharArray();
        final char[] target = t.toCharArray();

        final int[] map = new int[256];
        final int[] tmpMap = new int[256];

        for (char ch : target)
            map[ch]++;

        int left = 0, right = 0;
        int len = Integer.MAX_VALUE;

        int[] ans = new int[]{-1, 100001};
        while (right < source.length) {
            if (map[source[right]] != 0)
                tmpMap[source[right]]++;

            while (equal(map, tmpMap) && left <= right) {
                if (right - left < ans[1] - ans[0]) {
                    ans[0] = left;
                    ans[1] = right;
                }

                if (map[source[left]] != 0) {
                    tmpMap[source[left]]--;
                }
                left++;
            }

            right++;
        }

        if (ans[0] < 0) return "";
        return s.substring(ans[0], ans[1] + 1);
    }

    private boolean equal(int[] map, int[] tmpMap) {
        for (int i = 0; i < map.length; ++i) {
            if (map[i] > tmpMap[i]) return false;
        }
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, int n) {
        // check row and col
        for (int i = 0; i < n; ++i) {
            if (board[i][col] == 'Q')
                return false;
        }

        // check upper right corner
        for (int i = row - 1, j = col + 1;
             i >= 0 && j < n; ++j, --i) {
            if (board[i][j] == 'Q') return false;
        }

        // check upper left corner
        for (int i = row - 1, j = col - 1;
             i >= 0 && j >= 0; --i, --j) {
            if (board[i][j] == 'Q') return false;
        }

        return true;
    }

    public static void main(String[] args) {
//        System.out.println(Integer.parseInt("12345"));
//        System.out.println(lbs(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15}));
        DemoApplicationTests application = new DemoApplicationTests();
//        System.out.println(editDistanceDp("sunday", "saturday"));
//        int[][] cost = {
//                {1, 2, 3},
//                {4, 8, 2},
//                {1, 5, 3}
//        };
//        System.out.print(minCostPathDP(cost, 2, 2));

//        System.out.println(coinExchange(new int[]{1, 2, 3}, 4));
//        System.out.println(minMultiMatrix(new int[]{10, 30, 5, 60}, 4));
//        System.out.println(nCr(778, 116));
//        System.out.println(knapSack(new int[]{60, 100, 120}, new int[]{10, 20, 30}, 50, 2));
//        String str = "abaedaba";
//        System.out.println(minPalPartitionDpOp(str));

//        System.out.println(findPartitionDp(new int[]{1, 5, 11, 5}));
//        System.out.println(coinChange(new int[]{2, 5, 10, 1}, 27));
//        System.out.println(minFallingPathSum(new int[][]{
//                {100, -42, -46, -41},
//                {31, 97, 10, -10},
//                {-58, -51, 82, 89},
//                {51, 81, 69, -51}
//        }));

//        System.out.println(ladderLength("hog", "cog", Collections.singletonList("cog")));
//        System.out.println(Integer.toUnsignedString(Integer.parseInt("077", 8), 10));
        System.out.println(application.minWindow("ADOBECODEBANC", "ABC"));
    }
}
