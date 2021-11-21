package org.xhliu.springwebflux.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public void start(final int port) throws InterruptedException {
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        NioEventLoopGroup subGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(mainGroup, subGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInboundHandlerAdapter(){
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.awaitUninterruptibly().sync();
        } finally {
            mainGroup.shutdownGracefully().sync();
            subGroup.shutdownGracefully();
        }
    }
}
