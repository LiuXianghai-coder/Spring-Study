package com.example.demo.netty.handler

import com.example.demo.netty.decoder.FixedLengthFrameDecoder
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import org.junit.Test

import static org.junit.Assert.*

class FixedLengthFrameDecoderTest {
    @Test
    void testFramesDecoder1() {
        ByteBuf buf = Unpooled.buffer()
        for (int i = 0; i < 9; ++i) {
            buf.writeByte(i)
        }

        ByteBuf input = buf.duplicate()
        EmbeddedChannel channel = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3)
        )

        assertTrue(channel.writeInbound(input.retain()))
        assertTrue(channel.finish())

        ByteBuf read = channel.readInbound()
        assertEquals(buf.readSlice(3), read)
        read.release()

        read = channel.readInbound()
        assertEquals(buf.readSlice(3), read)
        read.release()

        read = channel.readInbound()
        assertEquals(buf.readSlice(3), read)
        read.release()

        assertNull(channel.readInbound())
        buf.release()
    }

    @Test
    void testFrameDecoder() {
        ByteBuf buf = Unpooled.buffer()
        for (int i = 0; i < 9; ++i) {
            buf.writeByte(i)
        }

        ByteBuf input = buf.duplicate()
        EmbeddedChannel channel = new EmbeddedChannel(
                new FixedLengthFrameDecoder(3)
        )

        assertFalse(channel.writeInbound(input.readBytes(2)))
        assertTrue(channel.writeInbound(input.readBytes(7)))

        assertTrue(channel.finish())
    }
}
