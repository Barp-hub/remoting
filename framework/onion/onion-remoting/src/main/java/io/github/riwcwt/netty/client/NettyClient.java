package io.github.riwcwt.netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.github.riwcwt.entity.Request;
import io.github.riwcwt.entity.Response;
import io.github.riwcwt.netty.client.codec.MessageDecoder;
import io.github.riwcwt.netty.client.codec.MessageEncoder;
import io.github.riwcwt.netty.client.codec.MessageHandler;
import io.github.riwcwt.netty.listener.RequestListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

@Component
public class NettyClient implements ApplicationContextAware, InitializingBean, DisposableBean {

	private ApplicationContext context = null;

	private EventLoopGroup group = null;

	private Bootstrap bootstrap = null;

	private ConcurrentHashMap<InetSocketAddress, Channel> channels = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	public Channel connect(InetSocketAddress socketAddress) {
		Channel channel = this.channels.get(socketAddress);
		if (channel == null) {
			channel = this.bootstrap.connect(socketAddress.getAddress().getHostAddress(), socketAddress.getPort())
					.syncUninterruptibly().channel();
			this.channels.put(socketAddress, channel);
		}
		return channel;
	}

	public Response send(InetSocketAddress socketAddress, Request request) {
		Channel channel = this.connect(socketAddress);
		RequestListener listener = this.context.getBean(RequestListener.class);
		ChannelFuture future = channel.writeAndFlush(request);
		future.addListener(listener);
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.group = new NioEventLoopGroup(5);
		this.bootstrap = new Bootstrap();
		this.bootstrap.group(this.group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel channel) throws Exception {
						channel.pipeline().addLast(context.getBean(MessageDecoder.class));
						channel.pipeline().addLast(context.getBean(MessageEncoder.class));
						channel.pipeline().addLast(context.getBean(MessageHandler.class));
					}
				});
		this.channels = new ConcurrentHashMap<InetSocketAddress, Channel>();
	}

	@Override
	public void destroy() throws Exception {
		group.shutdownGracefully();
		for (Channel channel : channels.values()) {
			channel.closeFuture().syncUninterruptibly();
			channel = null;
		}
		group = null;
	}
}
