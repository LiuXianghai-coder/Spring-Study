package com.example.demo.netty.codec;

import com.example.demo.netty.decoder.ByteToIntegerDecoder;
import com.example.demo.netty.encoder.IntegerToByteEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author xhliu2
 * @create 2021-10-29 11:26
 **/
public class CombineByteIntegerCodec extends CombinedChannelDuplexHandler<ByteToIntegerDecoder, IntegerToByteEncoder> {
    protected CombineByteIntegerCodec() {
        super(new ByteToIntegerDecoder(), new IntegerToByteEncoder());
    }
}
