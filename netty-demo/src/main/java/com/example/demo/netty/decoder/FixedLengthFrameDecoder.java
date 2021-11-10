package com.example.demo.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xhliu2
 * @create 2021-10-28 14:37
 **/
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException("Frame length must is a positive val");
        }

        this.frameLength = frameLength;
    }

    @Override
    protected void
    decode(
            ChannelHandlerContext channelHandlerContext,
            ByteBuf byteBuf,
            List<Object> list
    ) {
        while (byteBuf.readableBytes() >= frameLength) {
            ByteBuf buf = byteBuf.readBytes(frameLength);
            list.add(buf);
        }
    }
}
