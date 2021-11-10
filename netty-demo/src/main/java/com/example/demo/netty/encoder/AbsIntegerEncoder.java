package com.example.demo.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author xhliu2
 * @create 2021-10-28 16:58
 **/
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void
    encode(
            ChannelHandlerContext channelHandlerContext,
            ByteBuf in,
            List<Object> list
    ) throws Exception {
        while (in.readableBytes() >= 4) {
            int value = Math.abs(in.readInt());
            list.add(value);
        }
    }
}
