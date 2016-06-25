package io.github.riwcwt.netty.listener;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

@Component("server-decoder")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RequestListener implements ChannelFutureListener {

	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		// TODO Auto-generated method stub

	}

}
