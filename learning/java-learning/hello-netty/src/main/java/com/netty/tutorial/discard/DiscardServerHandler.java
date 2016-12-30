package com.netty.tutorial.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelHandlerAdapter { // (1)

	@Override
	public void channelRead(ChannelHandlerContext context, Object message) { // (2)
		ByteBuf in = (ByteBuf) message;
		try {
			while (in.isReadable()) { // (1)
				System.out.print((char) in.readByte());
				System.out.flush();
			}
		} finally {
			ReferenceCountUtil.release(message); // (2)
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		context.close();
	}
}