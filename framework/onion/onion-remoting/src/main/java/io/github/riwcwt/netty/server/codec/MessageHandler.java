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
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Request request = Request.class.cast(msg);
		if (request.getType() == MessageType.HEART_BEAT) {
			Response response = new Response();
			response.setType(MessageType.HEART_BEAT);
			ctx.writeAndFlush(response);
		}
	}

}
