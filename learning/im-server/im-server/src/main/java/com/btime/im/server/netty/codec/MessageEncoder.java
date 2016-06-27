package com.btime.im.server.netty.codec;

import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
@Component
public class MessageEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

	}

}
