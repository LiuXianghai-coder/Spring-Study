package com.example.demo.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;

/**
 * @author xhliu2
 * @create 2021-10-29 11:42
 **/
public class HttpPipelineInitialize extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpPipelineInitialize(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        if (isClient) {
            pipeline.addLast(new HttpClientCodec()); // HttpClientCodec 包含了客户端应该有的编码和解码对象
            pipeline.addLast("decompressor", new HttpContentDecompressor()); // 将来自服务端的响应进行解压缩
        } else {
            pipeline.addLast("decoder", new HttpRequestDecoder()); // 将来自客户端的请求数据进行解码
            pipeline.addLast("compressor", new HttpContentCompressor()); // 将响应数据进行压缩，发送回客户端
            pipeline.addLast("encoder", new HttpResponseEncoder()); // 将处理后得响应数据经过编码之后发送回客户端
            pipeline.addLast("logging", new ServerLoggingHandler()); // 添加记录相关的请求数据
        }

        // 将缓冲的数据整合为一个 Http 请求
        pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
    }
}
