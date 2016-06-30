package io.github.riwcwt.netty.server.codec;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.riwcwt.constant.MessageType;
import io.github.riwcwt.entity.Request;
import io.github.riwcwt.entity.Response;
import io.github.riwcwt.entity.ServiceRequest;
import io.github.riwcwt.proxy.DynamicProxy;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
@Component("server-handler")
public class MessageHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	@Autowired
	private DynamicProxy proxy = null;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
		Request request = Request.class.cast(message);
		logger.info("server received message : " + request.toString());
		this.handle(request, ctx);
	}

	private void handle(Request request, ChannelHandlerContext ctx) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (request.getType() == MessageType.HEART_BEAT) {
			Response response = new Response();
			response.setRequestId(request.getRequestId());
			response.setType(MessageType.HEART_BEAT);
			ctx.writeAndFlush(response);
		} else if (request.getType() == MessageType.NORMAL_REQUEST) {
			ServiceRequest serviceRequest = ServiceRequest.class.cast(request.getData());
			Object result = this.proxy.invoke(serviceRequest);
			Response response = new Response();
			response.setRequestId(request.getRequestId());
			response.setType(MessageType.NORMAL_REQUEST);
			response.setData(result);
			ctx.writeAndFlush(response);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("exception:" + cause.getMessage());
		ctx.channel().close();
	}

}
