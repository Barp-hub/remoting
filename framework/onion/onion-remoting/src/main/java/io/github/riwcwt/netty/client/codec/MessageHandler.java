package io.github.riwcwt.netty.client.codec;

import org.springframework.stereotype.Component;

import io.github.riwcwt.constant.MessageType;
import io.github.riwcwt.entity.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Component
public class MessageHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Response response = Response.class.cast(msg);
		if (response.getType() == MessageType.HEART_BEAT) {

		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}

}
