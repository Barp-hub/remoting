package io.github.riwcwt.mqtt;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by michael on 2017-02-03.
 */
@ChannelHandler.Sharable
public class MqttServerHandler extends SimpleChannelInboundHandler<MqttMessage> {

    private static final Logger logger = LoggerFactory.getLogger(MqttServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("active : " + ctx.name());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
        logger.info("server receive : " + msg.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("inactive : " + ctx.name());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
    }

}
