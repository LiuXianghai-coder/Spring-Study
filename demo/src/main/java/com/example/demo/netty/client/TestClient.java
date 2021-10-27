package com.example.demo.netty.client;

import com.example.demo.netty.handler.ProxyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author xhliu2
 * @create 2021-10-27 15:21
 **/
public class TestClient {
    private final static Logger log = LoggerFactory.getLogger(TestClient.class);

    public void start(final int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ProxyClientHandler());

            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", port));
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.info("Connection established");
                } else {
                    log.info("Connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }).sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestClient client = new TestClient();
        client.start(8080);
    }
}
