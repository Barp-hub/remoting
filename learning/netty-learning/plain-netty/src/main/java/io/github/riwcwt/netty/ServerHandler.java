package io.github.riwcwt.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {
	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("active : " + ctx.name());
	}

	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		logger.info("server receive : " + msg);

		ctx.writeAndFlush("server time : " + System.currentTimeMillis());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("inactive : " + ctx.name());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(), cause);
	}
}
