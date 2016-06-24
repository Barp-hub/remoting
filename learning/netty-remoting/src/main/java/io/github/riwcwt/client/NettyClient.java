package io.github.riwcwt.client;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

	private EventLoopGroup group = null;
	private Bootstrap bootstrap = null;

	public void start() {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel channel) throws Exception {

						// encoders
						channel.pipeline().addLast(new MessageEncoder());

						// decoders
						channel.pipeline().addLast(new MessageDecoder());

						channel.pipeline().addLast(new MessageHandler());
					}
				});

	}

	public void connect(String host, int port) throws InterruptedException {
		// 发起异步连接操作
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
		future.channel().closeFuture().sync();
	}

	public void destroy() {
		group.shutdownGracefully();
	}
}
