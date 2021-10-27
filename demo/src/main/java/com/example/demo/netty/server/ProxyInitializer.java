package com.example.demo.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * ProxyInitializer 的主要目的是为每个子 Channel 的处理链添加相关的处理节点
 */
public class ProxyInitializer extends ChannelInitializer<SocketChannel> {
    private final String remoteHost;
    private final int port;

    public ProxyInitializer(String remoteHost, int port) {
        this.remoteHost = remoteHost;
        this.port = port;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(
                new ProxyFrontHandler(remoteHost, port)
        );
    }
}
