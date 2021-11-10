package com.example.demo.netty.server;

import com.example.demo.netty.handler.ProxyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author xhliu2
 * @create 2021-10-27 15:10
 **/
public class ProxyServer {
    private final static Logger log = LoggerFactory.getLogger(ProxyServer.class);

    private final String remoteHost;
    private final int port;

    public ProxyServer(String remoteHost, int port) {
        this.remoteHost = remoteHost;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 每个服务端 “主” Channel 创建的一个 Channel 连接，都会进行 ProxyInitializer 对应的处理
                    .childHandler(new ProxyInitializer("127.0.0.1", 8081))
                    .childOption(ChannelOption.AUTO_READ, false);

            ChannelFuture future = serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ProxyServer proxyServer = new ProxyServer("127.0.0.1", 8080);
        proxyServer.start();
    }
}
