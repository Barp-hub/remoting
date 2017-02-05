package io.github.riwcwt.netty;

import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

	private static final int DEAFAULT_PORT = 502;

	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private Channel channel = null;

	private int port;

	public NettyServer() {
		this(DEAFAULT_PORT);
	}

	public NettyServer(int port) {
		this.port = port;
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();
	}

	public void start() throws InterruptedException {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> NettyServer.this.stop()));

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 100);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel channel) throws Exception {
				channel.pipeline().addLast(new LoggingHandler(NettyServer.class, LogLevel.DEBUG));
				channel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
				channel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
				channel.pipeline().addLast(new ServerHandler());
			}
		});

		// Start the server.
		channel = bootstrap.bind(port).sync().channel();
	}

	public void stop() {

		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();

		channel.closeFuture().syncUninterruptibly();
	}
}
