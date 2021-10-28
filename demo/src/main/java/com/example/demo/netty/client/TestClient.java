package com.example.demo.netty.client;

import com.example.demo.netty.handler.ProxyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author xhliu2
 * @create 2021-10-27 15:21
 **/
public class TestClient {
    private final static Logger log = LoggerFactory.getLogger(TestClient.class);

    public void start(final int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ProxyClientHandler());

            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", port));
            future.addListener((ChannelFutureListener) future1 -> {
                if (future1.isSuccess()) {
                    log.info("Connect establish");
                    return;
                }

                log.info("Connect establish failed");
            }).channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public void bootstrap() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        final AttributeKey<Integer> id = AttributeKey.newInstance("id");
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 8080))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            Integer integer = ch.attr(id).get();
                            ch.pipeline().addLast(
                                    new ProxyClientHandler()
                            );
                        }
                    });
            ChannelFuture f = b.connect();
            new Thread(new Tool(f)).start(); // 不应当这样启动一个线程，请使用线程池的方式
            f.sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    static class Tool implements Runnable {
        private final ChannelFuture future;

        Tool(ChannelFuture future) {
            this.future = future;
        }

        @SneakyThrows
        @Override
        public void run() {
            TimeUnit.SECONDS.sleep(10);
            future.channel().close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestClient client = new TestClient();
        client.bootstrap();
    }
}
