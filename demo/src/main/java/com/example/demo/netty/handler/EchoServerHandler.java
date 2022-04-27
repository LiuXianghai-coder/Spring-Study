package com.example.demo.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author xhliu2
 * @create 2021-10-20 17:33
 **/
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private final static Logger log = LoggerFactory.getLogger(EchoServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf in = (ByteBuf) msg;
            log.info("Server Receive: " + in.toString(StandardCharsets.UTF_8));
            ChannelFuture future = ctx.write(Unpooled.copiedBuffer("Remote Server Message", CharsetUtil.UTF_8));
            future.addListener((ChannelFutureListener) channelFuture -> {
                log.info("Write msg success");
            });
            return;
        }

        throw new IllegalArgumentException("msg must is ByteBuf Object...");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
