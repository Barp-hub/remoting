package io.github.riwcwt.netty.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.github.riwcwt.netty.server.codec.MessageDecoder;
import io.github.riwcwt.netty.server.codec.MessageEncoder;
import io.github.riwcwt.netty.server.codec.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class NettyServer implements InitializingBean, DisposableBean, ApplicationContextAware {

	private ApplicationContext context = null;

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private ServerBootstrap bootstrap;
	private Channel channel;

	@Override
	public void destroy() throws Exception {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		channel.closeFuture().syncUninterruptibly();
		bossGroup = null;
		workerGroup = null;
		channel = null;
	}

	public void start(int port) throws InterruptedException {
		channel = bootstrap.bind(port).sync().channel();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		bootstrap = new ServerBootstrap();

		bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true).childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel channel) throws Exception {
						channel.pipeline().addLast(context.getBean(MessageDecoder.class));
						channel.pipeline().addLast(context.getBean(MessageEncoder.class));
						channel.pipeline().addLast(context.getBean(MessageHandler.class));
					}
				});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
