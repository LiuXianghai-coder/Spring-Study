package com.example.demo;

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
        for (int i = 0; i < N;) {
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


    public static void main(String[] args) throws InterruptedException {
//        Application application = new Application();
//        application.start(9096);
        String s1 = "great";
        System.out.println("left=" + s1.substring(1, s1.length()));
//        System.out.println("right=" + right);

        /*
            s2_left = r     s2_right=geat

            s1_left = g     s1_right=reat
         */
    }
}
