package com.example.demo.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xhliu2
 * @create 2021-10-29 10:15
 **/
public class ByteToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.readableBytes() >= 4)
            list.add(byteBuf.readInt());
    }
}
