package io.github.riwcwt.netty.client.codec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.riwcwt.entity.Request;
import io.github.riwcwt.netty.serialize.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


@Sharable
@Component("client-encoder")
public class MessageEncoder extends MessageToByteEncoder<Request> {

	@Autowired
	private Serialization serial = null;

	@Override
	protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
		byte[] data = this.serial.serialize(msg);
		out.writeInt(data.length);
		out.writeBytes(data);
	}

}
