package io.github.riwcwt.mqtt;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.io.IOException;

/**
 * Created by michael on 2017-02-03.
 */
public class MqttServer {
    public static void main(String[] args) throws InterruptedException, IOException {

        InternalLoggerFactory.setDefaultFactory(Log4JLoggerFactory.INSTANCE);


        MqttServer server = new MqttServer(12321);
        server.start();

        System.in.read();

        server.stop();
    }

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel = null;

    private int port;

    public MqttServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> MqttServer.this.stop()));

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 100);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new LoggingHandler(MqttServer.class, LogLevel.DEBUG));
                channel.pipeline().addLast(MqttEncoder.INSTANCE);
                channel.pipeline().addLast(new MqttDecoder());
                channel.pipeline().addLast(new MqttServerHandler());
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
