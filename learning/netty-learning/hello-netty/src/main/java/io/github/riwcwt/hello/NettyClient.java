package io.github.riwcwt.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by michael on 2017-01-23.
 */
public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    public static void main(String[] args) throws InterruptedException, IOException {

        InternalLoggerFactory.setDefaultFactory(Log4JLoggerFactory.INSTANCE);


        NettyClient client = new NettyClient("localhost", 12321);
        client.start();

        StringBuffer content = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            content.append("正");
        }

        for (int i = 0; i < 5; i++) {
            client.send(content.toString());
        }

        System.in.read();

        client.stop();
    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel = null;

    private String host;
    private int port;

    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new LoggingHandler(NettyClient.class, LogLevel.DEBUG));
                channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 4));
                channel.pipeline().addLast(new LengthFieldPrepender(4));
                channel.pipeline().addLast(new StringDecoder());
                channel.pipeline().addLast(new StringEncoder());
                channel.pipeline().addLast(new ClientHandler());
            }
        });

        channel = bootstrap.connect(host, port).sync().channel();
    }

    public void send(String content) {
        channel.writeAndFlush(content, channel.voidPromise());
    }

    public void stop() {
        channel.close().awaitUninterruptibly();

        group.shutdownGracefully();
    }
}
