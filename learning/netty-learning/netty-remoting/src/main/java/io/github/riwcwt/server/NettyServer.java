package io.github.riwcwt.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

	private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

	private int port = 8888;

	// 配置服务端的NIO线程组
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private ServerBootstrap bootstrap = null;
	private Channel channel;

	public NettyServer() {
	}

	public NettyServer(int port) {
		this.port = port;
	}

	public void start() {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();

		try {
			bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100).handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel channel) throws IOException {

							// encoders
							channel.pipeline().addLast(new MessageEncoder());

							// decoders
							channel.pipeline().addLast(new MessageDecoder());

							channel.pipeline().addLast(new MessageHandler());
						}
					});

			// 绑定端口，同步等待成功
			channel = bootstrap.bind(port).sync().channel();
		} catch (InterruptedException e) {
			logger.info("netty server start error!", e);
		}
	}

	public void close() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		channel.closeFuture().syncUninterruptibly();
		bossGroup = null;
		workerGroup = null;
		channel = null;
	}
}
