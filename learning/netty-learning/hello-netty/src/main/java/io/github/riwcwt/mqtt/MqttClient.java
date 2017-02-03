package io.github.riwcwt.mqtt;

import io.github.riwcwt.hello.NettyClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by michael on 2017-02-03.
 */
public class MqttClient {

    private static final Logger logger = LoggerFactory.getLogger(MqttClient.class);

    public static void main(String[] args) throws InterruptedException, IOException {

        InternalLoggerFactory.setDefaultFactory(Log4JLoggerFactory.INSTANCE);


        MqttClient client = new MqttClient("localhost", 12321);
        client.start();

        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(MqttMessageType.PUBLISH, false, MqttQoS.AT_LEAST_ONCE, true, 0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader("/abc", 1234);
        ByteBuf payload = PooledByteBufAllocator.DEFAULT.buffer();
        payload.writeBytes("这里是杭州！".getBytes(CharsetUtil.UTF_8));
        MqttPublishMessage message = new MqttPublishMessage(mqttFixedHeader, mqttPublishVariableHeader, payload);

        client.send(message);

        System.in.read();

        client.stop();
    }

    public MqttClient(String host, int port) {
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
                channel.pipeline().addLast(MqttEncoder.INSTANCE);
                channel.pipeline().addLast(new MqttDecoder());
                channel.pipeline().addLast(new MqttClientHandler());
            }
        });

        channel = bootstrap.connect(host, port).sync().channel();
    }

    public void send(MqttMessage message) {
        this.channel.writeAndFlush(message);
    }

    public void stop() {
        channel.close().awaitUninterruptibly();

        group.shutdownGracefully();
    }
}
