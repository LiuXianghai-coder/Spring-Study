package com.example.demo.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class ProxyBackendHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(ProxyBackendHandler.class);

    private final Channel inBoundChannel;

    public ProxyBackendHandler(Channel inBoundChannel) {
        this.inBoundChannel = inBoundChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.read();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf in = (ByteBuf) msg;

            log.info("Proxy Server receive remote's msg: " + in.toString(StandardCharsets.UTF_8));
            inBoundChannel.writeAndFlush(in)
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            ctx.channel().read();
                        } else {
                            future.channel().closeFuture();
                        }
                    });
            return;
        }

        throw new IllegalArgumentException("msg must is ByteBuf");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ProxyFrontHandler.closeOnFlush(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ProxyFrontHandler.closeOnFlush(ctx.channel());
    }
}