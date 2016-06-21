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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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

	public void connect(InetSocketAddress socketAddress) {
		Channel channel = this.getChannel(socketAddress);
		if (channel == null) {
			channel = bootstrap.connect(socketAddress.getAddress().getHostAddress(), socketAddress.getPort())
					.syncUninterruptibly().channel();
			this.channels.add(channel);
		}
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
	public void destroy() throws Exception {
		group.shutdownGracefully();
		for (Channel channel : channels) {
			channel.closeFuture().syncUninterruptibly();
			channel = null;
		}
		group = null;
	}
}
