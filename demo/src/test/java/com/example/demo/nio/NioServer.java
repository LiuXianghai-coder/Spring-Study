package com.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    static void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(5431));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ);
                }

                if (selectionKey.isWritable()) {
                    sendMsg(selectionKey);
                }

                if (selectionKey.isReadable()) {
                    readMsg(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    static void readMsg(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        try {
            socketChannel.read(buffer);
            buffer.flip();
            System.out.println("client=" + new String(buffer.array(), buffer.position(), buffer.limit()));
            buffer.clear();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void sendMsg(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            int r = socketChannel.read(buffer);
            if (r < 0) {
                socketChannel.close();
            } else {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws IOException {
        start();
    }

}
