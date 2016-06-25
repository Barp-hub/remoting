package io.github.riwcwt.netty.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.List;

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

	private List<Channel> channels = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	private Channel getChannel(InetSocketAddress socketAddress) {
		for (Channel channel : channels) {
			SocketAddress remoteAddress = channel.remoteAddress();
			if (remoteAddress instanceof InetSocketAddress) {
				if (InetSocketAddress.class.cast(remoteAddress).equals(socketAddress)) {
					return channel;
				}
			}
		}
		return null;
	}

	public Channel connect(InetSocketAddress socketAddress) {
		Channel channel = this.getChannel(socketAddress);
		if (channel == null) {
			channel = bootstrap.connect(socketAddress.getAddress().getHostAddress(), socketAddress.getPort())
					.syncUninterruptibly().channel();
			this.channels.add(channel);
		}
		return channel;
	}

	public Response send(InetSocketAddress socketAddress, Request request) {
		Channel channel = this.connect(socketAddress);
		ChannelFuture future = channel.writeAndFlush(request);
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel channel) throws Exception {
						channel.pipeline().addLast(context.getBean(MessageDecoder.class));
						channel.pipeline().addLast(context.getBean(MessageEncoder.class));
						channel.pipeline().addLast(context.getBean(MessageHandler.class));
					}
				});
		channels = new LinkedList<Channel>();
	}

	@Override
	public void destroy() throws Exception {
		group.shutdownGracefully();
		for (Channel channel : channels) {
			channel.closeFuture().syncUninterruptibly();
			channel = null;
		}
		group = null;
	}
}
