package io.github.riwcwt.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by michael on 2017-01-23.
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        NettyClient client = new NettyClient("localhost", 12321);
        client.start();

        for (int i = 0; i < 5; i++) {
            client.send(String.valueOf(i));
        }

        client.stop();
    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private EventLoopGroup group = new NioEventLoopGroup();
    private ChannelFuture channel = null;

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
                channel.pipeline().addLast(new LineBasedFrameDecoder(4096));
                channel.pipeline().addLast(new StringEncoder());
                channel.pipeline().addLast(new StringDecoder());
                channel.pipeline().addLast(new ClientHandler());
            }
        });

        channel = bootstrap.connect(host, port).sync();
    }

    private void send(String content) {
        this.channel.channel().writeAndFlush(content);
    }

    public void stop() {
        try {
            channel.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            //swallow it
        }

        group.shutdownGracefully();
    }
}
