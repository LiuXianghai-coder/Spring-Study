package com.example.demo.netty.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author xhliu2
 * @create 2021-10-29 10:49
 **/
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {
    @Override
    protected void decode(
            ChannelHandlerContext channelHandlerContext,
            Integer in,
            List<Object> list
    ) throws Exception {
        list.add(String.valueOf(in));
    }
}
