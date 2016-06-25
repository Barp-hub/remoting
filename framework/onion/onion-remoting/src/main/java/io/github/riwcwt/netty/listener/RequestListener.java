package io.github.riwcwt.netty.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RequestListener implements ChannelFutureListener {

	private static final Logger logger = LoggerFactory.getLogger(RequestListener.class);

	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		logger.info("channel operation complete!");
	}

}
