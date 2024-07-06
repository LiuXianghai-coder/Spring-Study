package com.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(5431));
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        buffer.put("Hello From client".getBytes());
        socketChannel.write(buffer);
        buffer.clear();
        socketChannel.read(buffer);
        buffer.flip();
        System.out.println("res=" + new String(buffer.array(), buffer.position(), buffer.limit()));
        buffer.clear();
    }
}
