package com.example.demo.netty.server;

import com.example.demo.netty.handler.ProxyClientHandler;
import com.example.demo.netty.handler.ProxyServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author xhliu2
 * @create 2021-10-27 15:10
 **/
public class ProxyServer {
    private final static Logger log = LoggerFactory.getLogger(ProxyServer.class);

    public void start(final int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ProxyServerHandler());
            ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(port)).sync();
            future.channel().closeFuture().sync();
            /*future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.info("Server Bound");
                } else {
                    log.info("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            });*/
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProxyServer proxyServer = new ProxyServer();
        proxyServer.start(8080);
    }
}
