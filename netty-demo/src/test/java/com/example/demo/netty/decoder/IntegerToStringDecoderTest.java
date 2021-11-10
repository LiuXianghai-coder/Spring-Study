package com.example.demo.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerToStringDecoderTest {

    @Test
    public void byteToIntTest() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i <= 12; ++i)
            buf.writeByte((byte) i);

        EmbeddedChannel channel = new EmbeddedChannel(
                new ByteToIntegerDecoder(),
                new IntegerToStringDecoder()
        );

        assertTrue(channel.writeInbound(buf));
        assertTrue(channel.finish());

        for (int i = 0; i < 3; ++i) {
            System.out.println((String) channel.readInbound());
        }
    }
}
