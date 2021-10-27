package com.example.demo.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ProxyInitializer extends ChannelInitializer<SocketChannel> {
    private final String remoteHost;
    private final int port;

    public ProxyInitializer(String remoteHost, int port) {
        this.remoteHost = remoteHost;
        this.port = port;
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new ProxyFrontHandler(remoteHost, port)
        );
    }
}
