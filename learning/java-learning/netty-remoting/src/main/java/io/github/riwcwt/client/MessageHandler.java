package io.github.riwcwt.client;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.riwcwt.variable.entity.Request;
import io.github.riwcwt.variable.entity.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageHandler extends ChannelInboundHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	private ChannelHandlerContext context = null;;

	private Timer timer = null;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		context = ctx;

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				logger.info("发送定时请求！");
				Request request = new Request();
				request.setRequestId(UUID.randomUUID().toString());
				context.writeAndFlush(request);
			}
		}, 1000, 20000);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Response response = Response.class.cast(msg);
		logger.info("客户端收到返回数据:" + response.getRequestId());
	}

}
