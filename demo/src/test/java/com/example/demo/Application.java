package com.example.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author xhliu2
 * @create 2021-09-22 16:56
 **/
public class Application {

    public int maximumWhiteTiles(int[][] tiles, int len) {
        int n = tiles.length;
        TreeSet<int[]> ts = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        for (int[] tile : tiles) {
            int[] left = ts.floor(tile), right = ts.ceiling(tile);
            // System.out.println(Arrays.toString(left) + "\t" + Arrays.toString(tile) + "\t" + Arrays.toString(right));
            if (left == null && right == null) {
                ts.add(tile);
                continue;
            }

            boolean lc = left != null && left[1] >= tile[0]
                    || tile[0] - Objects.requireNonNull(left)[1] == 1;
            boolean rc = right != null && tile[1] >= right[0]
                    || Objects.requireNonNull(right)[0] - tile[1] == 1;

            if (lc && rc) {
                ts.remove(left);
                ts.remove(right);
                ts.add(new int[]{left[0], right[1]});
                continue;
            }


            if (lc) {
                ts.remove(left);
                ts.add(new int[]{left[0], tile[1]});
            } else if (rc) {
                ts.remove(right);
                ts.add(new int[]{tile[0], right[1]});
            } else {
                ts.add(tile);
            }
        }

        int ans = 0;
        for (int[] val : ts) {
            System.out.println(Arrays.toString(val));
            int lo = val[0], hi = val[1];
            int sz = hi - lo + 1;
            if (sz < len) {
                if (sz > ans) ans = sz;
            } else {
                ans = len;
            }
        }

        return ans;
    }

    public static void addElement(Set<?> set) {
        set.add(null);
    }

    public void start(final int port) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            /* TODO */
                        }
                    });

            ChannelFuture future = bootstrap.bind().sync();
            future.syncUninterruptibly().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static int numTrees(int n) {
        if (n == 0 || n == 1) return 1;
        if (n == 2) return 2;

        int ans = 0;
        for (int i = 1; i <= n / 2 + 1; ++i) {
            ans += numTrees(i - 1) * numTrees(n - i);
        }

        return ans - 1;
    }

    private static class Node implements Comparable<Node> {
        private final int vertex;
        private final double distance;

        Node(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node o) {
            if (this.vertex == o.vertex)
                return 0;

            return Double.compare(this.distance, o.distance);
        }
    }

    public static int shipWithinDays(int[] weights, int days) {
        int lo = 1, hi = 50_000;

        while (lo < hi) {
            int mid = lo + ((hi - lo) >> 1);
            System.out.println("mid=" + mid + "\tlo=" + lo + "\thi=" + hi);
            if (!cloudLoad(weights, days, mid))
                lo = mid + 1;
            else
                hi = mid;
        }

        return lo;
    }

    private static boolean cloudLoad(int[] weights, int days, int capacity) {
        int times = 0;
        final int N = weights.length;
        for (int i = 0; i < N; ) {
            int sum = 0;
            if (weights[i] > capacity) return false;
            while (i < N && sum + weights[i] <= capacity) {
                sum += weights[i];
                i++;
            }


            times++;
        }

        return times <= days;
    }

    static void printTable(int m) {
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= i; ++j) {
                System.out.print(j + "*" + i + "=" + j * i + "\t");
            }
            System.out.println();
        }
    }

    public static String fractionAddition(String exp) {
        Deque<int[]> deque = new LinkedList<>();
        long[] ans = new long[]{1, 0, 1};
        if (exp.charAt(0) != '-') exp = "+" + exp;
        char[] arr = exp.toCharArray();
        int n = arr.length;
        for (int i = 0; i < n; ) {
            int flag = arr[i] == '-' ? -1 : 1;
            int j = i + 1;
            while (j < n && arr[j] != '+' && arr[j] != '-') ++j;
            String[] fra = exp.substring(i + 1, j).split("/");
            int[] tmp = new int[]{flag, Integer.parseInt(fra[0]), Integer.parseInt(fra[1])};
            deque.offer(tmp);
            i = j;
        }

        while (!deque.isEmpty()) {
            int[] val = deque.poll();
            long a1 = ans[1] * ans[0], b1 = ans[2];
            long a2 = (long) val[1] * val[0], b2 = val[2];
            a1 *= b2;
            a2 *= b1;

            long up = a1 + a2, down = b1 * b2;
            ans[1] = up;
            ans[2] = down;
        }

        long gcd = BigInteger.valueOf(ans[1]).gcd(BigInteger.valueOf(ans[2])).longValue();
        return (ans[1] >= 0 ? "" : "-") + (ans[1] / gcd) + "/" + (ans[2] / gcd);
    }

    public static int maxPoints(int[][] points) {
        int n = points.length;
        Set<Set<int[]>> set = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == j) continue;
                Set<int[]> ts = new TreeSet<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
                ts.add(points[i]);
                ts.add(points[j]);
                set.add(ts);
            }
        }

        for (int[] point : points) {
            int x = point[0], y = point[1];
            for (Set<int[]> list : set) {
                int xt = -1, yt = -1;
                boolean flag = true;
                for (int[] t : list) {
                    if (x == t[0] && y == t[1]) continue;
                    if (xt == -1 && yt == -1) {
                        xt = t[0];
                        yt = t[1];
                        continue;
                    }

                    long a = (long) (y - yt) * (x - t[0]);
                    long b = (long) (y - t[1]) * (x - xt);
                    if (a != b) {
                        flag = false;
                        break;
                    }
                }
                if (flag) list.add(new int[]{x, y});
            }
        }

        int ans = 2;
        for (Set<int[]> list : set) {
            if (list.size() > ans) {
                ans = list.size();
            }
        }
        return ans;
    }

    int[] tree;

    void init(int n) {
        tree = new int[n + 10];
    }

    int lowbit(int x) {
        return x & (-x);
    }

    void add(int u, int x) {
        for (int i = u; i < tree.length; i += lowbit(i)) {
            tree[i] += x;
        }
    }

    int query(int x) {
        int ans = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            ans += tree[i];
        }
        return ans;
    }

    public int numTimesAllBlue(int[] flips) {
        int n = flips.length;
        init(n);
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            add(flips[i], 1);
            int sum = query(i) - query(0);
            System.out.println("sum=" + sum);
            if (sum == i + 1) ans++;
        }
        return ans;
    }

    public boolean checkValidString(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; ++i) {
            if (s.charAt(i - 1) == '*') {
                dp[i][i] = true;
            }
        }
        for (int i = n - 1; i >= 1; --i) {
            char left = s.charAt(i - 1);
            for (int j = i + 1; j <= n; ++j) {
                char right = s.charAt(j - 1);
                if (left != ')' && right != '(') {
                    if (j - i == 1) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                for (int k = i; k < j; ++k) {
                    if (dp[i][k] && dp[k + 1][j]) {
                        dp[i][j] = true;
                        break;
                    }
                }
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Application app = new Application();
        System.out.println(app.checkValidString("(*())"));
        double val = Math.pow(8, 25);
        System.out.println(val);
    }
}
