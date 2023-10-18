package com.wanda.epc.device;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.springframework.stereotype.Component;

/**
 * netty编解码逻辑
 *
 * @author 孙率众
 */
@Component
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        ByteBuf buf = Unpooled.copiedBuffer(new byte[]{0x00});
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, buf));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new MyEncoder());
        //客户端的逻辑
        pipeline.addLast(new NioClientHandler());
    }
}
