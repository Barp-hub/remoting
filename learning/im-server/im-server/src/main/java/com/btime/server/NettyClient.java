package com.btime.server;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

@Component
public class NettyClient implements InitializingBean, DisposableBean, ApplicationContextAware {

	private ApplicationContext context = null;
	private EventLoopGroup group = null;
	private Bootstrap bootstrap = null;
	private List<Channel> channels = null;

	@Override
	public void destroy() throws Exception {
		group.shutdownGracefully();
		for (Channel channel : channels) {
			channel.closeFuture().syncUninterruptibly();
			channel = null;
		}
		group = null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel channel) throws Exception {
					}
				});
		channels = new LinkedList<Channel>();

	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

}
