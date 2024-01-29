package com.example.demo;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

public class FileReadTest {

    private final static Logger log = LoggerFactory.getLogger(FileReadTest.class);

    @Test
    public void commonReadTest() {
        try (FileInputStream in = new FileInputStream("D:/tmp/random.txt")) {
            int ch;
            Stopwatch stopwatch = Stopwatch.createStarted();
            StringBuilder sb = new StringBuilder();
            while ((ch = in.read()) > 0) {
                byte b1 = (byte) (ch >> 24 & 0xff);
                byte b2 = (byte) (ch >> 16 & 0xff);
                byte b3 = (byte) (ch >> 8 & 0xff);
                byte b4 = (byte) (ch & 0xff);
                if (b1 != 0) {
                    sb.append(new String(new byte[]{b1, b2, b3, b4}));
                } else if (b2 != 0) {
                    sb.append(new String(new byte[]{b2, b3, b4}));
                } else if (b3 != 0) {
                    sb.append(new String(new byte[]{b3, b4}));
                } else if (b4 != 0) {
                    sb.append(new String(new byte[]{b4}));
                }
            }
            System.out.println(sb);
            log.info("common read take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void nioReadReadTest() {
        File file = new File("D:/tmp/random.txt");
        try (
                FileInputStream in = new FileInputStream(file);
                FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.READ)
        ) {
            int ch;
            Stopwatch stopwatch = Stopwatch.createStarted();
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            StringBuilder sb = new StringBuilder();
            while (channel.read(buffer) > 0) {
                buffer.flip();
                sb.append(new String(buffer.array(), buffer.position(), buffer.limit()));
                buffer.compact();
            }
            System.out.println(sb);
            log.info("nio read take {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
