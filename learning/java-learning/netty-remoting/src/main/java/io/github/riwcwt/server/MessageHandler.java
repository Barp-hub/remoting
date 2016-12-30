package io.github.riwcwt.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.variable.entity.Request;
import io.github.riwcwt.variable.entity.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Request request = Request.class.cast(msg);

		logger.info("服务端收到请求:" + request.getRequestId());

		Response response = new Response();
		response.setRequestId(request.getRequestId());
		ctx.writeAndFlush(response);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("channel active : " + ctx.channel().remoteAddress().toString());
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}

}
