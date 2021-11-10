package com.example.demo.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xhliu2
 * @create 2021-11-02 16:32
 **/
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    private final static Logger log = LoggerFactory.getLogger(WebSocketServerInitializer.class);

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(64*1024),
                new WebSocketServerProtocolHandler("/websocket"),
                new TextFrameHandler(),
                new BinaryFrameHandler(),
                new ContinuationFrameHandler()
        );
    }

    private static final class TextFrameHandler
            extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        protected void
        channelRead0(
                ChannelHandlerContext ctx,
                TextWebSocketFrame msg
        ) throws Exception {
        }
    }

    private static final class BinaryFrameHandler extends
            SimpleChannelInboundHandler<BinaryFrameHandler> {

        @Override
        protected void
        channelRead0(
                ChannelHandlerContext ctx,
                BinaryFrameHandler msg
        ) throws Exception {
        }
    }

    private static final class ContinuationFrameHandler
            extends SimpleChannelInboundHandler<ContinuationFrameHandler> {
        @Override
        protected void channelRead0(
                ChannelHandlerContext ctx,
                ContinuationFrameHandler msg
        ) throws Exception {
        }
    }
}
