package com.example.demo.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class ByteToIntegerDecoderTest {
    private final static Logger log = LoggerFactory.getLogger(ByteToIntegerDecoderTest.class);

    @Test
    public void byteToIntTest() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i <= 12; ++i)
            buf.writeByte((byte) i);

        EmbeddedChannel channel = new EmbeddedChannel(
                new ByteToIntegerDecoder()
        );

        assertTrue(channel.writeInbound(buf));
        assertTrue(channel.finish());

        for (int i = 0; i < 3; ++i) {
            System.out.println((Integer) channel.readInbound());
        }
    }
}
