package io.github.riwcwt.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by michael on 2017-01-24.
 */
public class NettyClientPool {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientPool.class);


    public static void main(String[] args) throws InterruptedException, IOException {

        InternalLoggerFactory.setDefaultFactory(Log4JLoggerFactory.INSTANCE);

        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 12321);

        NettyClientPool client = new NettyClientPool();
        client.start();

        for (int i = 0; i < 50000; i++) {
            client.send(String.valueOf(i), socketAddress);
        }

        System.in.read();

        client.stop();
    }

    private EventLoopGroup group = null;
    private Bootstrap bootstrap = null;
    private AbstractChannelPoolMap<InetSocketAddress, ChannelPool> channelPoolMap = null;

    public void start() throws InterruptedException {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(this.group);
        this.bootstrap.channel(NioSocketChannel.class);
        this.bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        this.bootstrap.option(ChannelOption.TCP_NODELAY, true);
        this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        this.bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark.DEFAULT);
        this.channelPoolMap = new AbstractChannelPoolMap<InetSocketAddress, ChannelPool>() {
            @Override
            protected ChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(bootstrap.remoteAddress(key), new ChannelPoolHandler() {

                    @Override
                    public void channelReleased(Channel channel) throws Exception {
                        logger.info("释放连接通道！");
                    }

                    @Override
                    public void channelCreated(Channel channel) throws Exception {
                        logger.info("创建连接通道！");
                        channel.pipeline().addLast(new LoggingHandler(NettyClientPool.class, LogLevel.DEBUG));
                        channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 4));
                        channel.pipeline().addLast(new LengthFieldPrepender(4));
                        channel.pipeline().addLast(new StringDecoder());
                        channel.pipeline().addLast(new StringEncoder());
                        channel.pipeline().addLast(new ClientHandler());
                    }

                    @Override
                    public void channelAcquired(Channel channel) throws Exception {
                        logger.info("获取连接通道！");
                    }
                }, 5);
            }
        };
    }

    public void send(String content, InetSocketAddress socketAddress) {
        ChannelPool pool = channelPoolMap.get(socketAddress);
        //Channel channel = null;
        //try {
        //channel = pool.acquire().sync().get();

        pool.acquire().addListener((FutureListener<Channel>) future -> {
            if (future.isSuccess()) {
                Channel channel = future.getNow();
                try {
                    if (channel.isActive() && channel.isWritable()) {
                        channel.writeAndFlush(content);
                    }
                } finally {
                    pool.release(channel, channel.voidPromise());
                }
            }
        });
        //channel.writeAndFlush(content);
        //} catch (InterruptedException e) {
        //    logger.error(e.getMessage(), e);
        //} catch (ExecutionException e) {
        //    logger.error(e.getMessage(), e);
        //} finally {
        //    //pool.release(channel);
        //}
    }

    public void stop() {
        this.channelPoolMap.close();
        this.group.shutdownGracefully();
    }
}
