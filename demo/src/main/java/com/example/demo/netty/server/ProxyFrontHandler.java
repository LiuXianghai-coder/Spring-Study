package com.example.demo.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * ProxyFrontHandler 是一个处理链中的节点，这个节点的作用是通过当前处理链（Pipeline）对应的 Channel，
 * 得到这个 Channel 绑定的 EventLoop，通过共享这个 EventLoop 来创建一个新的客户端 Channel，
 * 这样做可以有效地避免线程同步的问题以及避免有有线程上下文切换带来的性能损失
 */
public class ProxyFrontHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(ProxyFrontHandler.class);

    private final String remoteHost;
    private final int port;

    private Channel outBoundChannel; // outBoundChannel 是当前代理服务器与远程主机之间的连接

    public ProxyFrontHandler(String remoteHost, int port) {
        this.remoteHost = remoteHost;
        this.port = port;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        final Channel inboundChannel = ctx.channel(); // 当前与客户端之间的连接

        /*
            当当前接受的连接的 Channel 被激活时，会创建一个新的、共享这个 Channel
             的 EventLoop 的客户端，通过这个客户端向远程主机发送请求，实现代理的功能
         */
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass())
                .handler(new ProxyBackendHandler(inboundChannel))
                .option(ChannelOption.AUTO_READ, false);

        ChannelFuture future = bootstrap.connect(remoteHost, port);
        outBoundChannel = future.channel();
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                inboundChannel.read();
            } else {
                inboundChannel.close();
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf in = (ByteBuf) msg;
            if (outBoundChannel.isActive()) {
                // 通过 outBoundChannel 向远程主机发送对应的请求
                log.info("Proxy Server write to Remote: " + in.toString(StandardCharsets.UTF_8));
                outBoundChannel.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        ctx.channel().read();
                    } else {
                        future.channel().close();
                    }
                });
            }

            return;
        }

        throw new IllegalArgumentException("msg must is ByteBuf");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
