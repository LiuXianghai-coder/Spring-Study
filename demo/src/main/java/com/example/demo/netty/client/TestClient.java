package com.example.demo.netty.client;

import com.example.demo.netty.handler.EchoClientHandler;
import com.example.demo.netty.handler.ProxyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

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
                    .remoteAddress(new InetSocketAddress(port))
                    .handler(new ProxyClientHandler());

            ChannelFuture future = bootstrap.connect();
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.info("Connection established");
                } else {
                    log.info("Connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            });
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestClient client = new TestClient();
        client.start(8080);
    }
}
