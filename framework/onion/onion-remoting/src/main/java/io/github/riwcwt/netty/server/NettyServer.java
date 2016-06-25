package io.github.riwcwt.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

	private ApplicationContext context = null;

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private ServerBootstrap bootstrap;
	private Channel channel;

	@Override
	public void destroy() throws Exception {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		bossGroup = null;
		workerGroup = null;
		if (channel != null) {
			channel.closeFuture().syncUninterruptibly();
			channel = null;
		}
	}

	public void start(int port) throws InterruptedException {
		logger.info("###########################start netty server, port : " + port + "###########################");
		channel = bootstrap.bind(port).sync().channel();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(10);
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
