package com.example.demo.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class ProxyFrontHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(ProxyFrontHandler.class);

    private final String remoteHost;
    private final int port;

    private Channel outBoundChannel;

    public ProxyFrontHandler(String remoteHost, int port) {
        this.remoteHost = remoteHost;
        this.port = port;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        final Channel inboundChannel = ctx.channel();

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
                log.info("Proxy Server write to Client: " + in.toString(StandardCharsets.UTF_8));
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
