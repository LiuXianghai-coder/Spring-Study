package com.example.demo.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author xhliu2
 * @create 2021-11-02 16:08
 **/
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext sslContext;
    private final boolean startTls;

    public SslChannelInitializer(
            SslContext sslContext,
            boolean startTls
    ) {
        this.sslContext = sslContext;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine engine = sslContext.newEngine(ch.alloc()); // 每个创建的 Channel 都需要有一个新的 SSLEngine
        ch.pipeline().addLast("ssl", new SslHandler(engine, startTls)); // 首先需要解密传来的流
    }
}
