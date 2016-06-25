package io.github.riwcwt.netty.server.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.github.riwcwt.constant.MessageType;
import io.github.riwcwt.entity.Request;
import io.github.riwcwt.entity.Response;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
@Component("server-handler")
public class MessageHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
		Request request = Request.class.cast(message);
		if (request.getType() == MessageType.HEART_BEAT) {
			Thread.sleep(5000);
			Response response = new Response();
			response.setRequestId(request.getRequestId());
			response.setType(MessageType.HEART_BEAT);
			ctx.writeAndFlush(response);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("exception:" + cause.getMessage());
		ctx.channel().close();
	}

}
