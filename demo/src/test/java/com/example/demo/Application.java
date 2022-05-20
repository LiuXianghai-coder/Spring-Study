package com.example.demo;

import com.example.demo.entity.Cat;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xhliu2
 * @create 2021-09-22 16:56
 **/
public class Application {
    final Object obj;

    public Application() {
        this.obj = new Object();
    }

    public int maximumWhiteTiles(int[][] tiles, int len) {
        int n = tiles.length;
        TreeSet<int[]> ts = new TreeSet<>((a, b) -> {
            return a[0] == b[0] ? a[1] - b[1] : a[0] - b[0];
        });

        for (int[] tile : tiles) {
            int[] left = ts.floor(tile), right = ts.ceiling(tile);
            // System.out.println(Arrays.toString(left) + "\t" + Arrays.toString(tile) + "\t" + Arrays.toString(right));
            if (left == null && right == null) {
                ts.add(tile);
                continue;
            }

            boolean lc = left != null && left[1] >= tile[0] || tile[0] - left[1] == 1;
            boolean rc = right != null && tile[1] >= right[0] || right[0] - tile[1] == 1;

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
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
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


    public static void main(String[] args) throws InterruptedException {
/*
        Application application = new Application();
        application.start(9096);
        String s1 = "great";
        System.out.println("left=" + s1.substring(1, s1.length()));
        System.out.println("right=" + right);
*/
        int[][] vals = new int[][]{{1, 2}, {3, 4}};
        Object[] array = Arrays.stream(vals)
                .map(val -> val == null ? null : val.clone())
                .toArray();
        Arrays.copyOf(vals, 0);
    }
}
