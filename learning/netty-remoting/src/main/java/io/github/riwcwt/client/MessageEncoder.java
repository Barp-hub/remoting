package io.github.riwcwt.client;

import io.github.riwcwt.variable.entity.Request;
import io.github.riwcwt.variable.serial.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Request> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
		byte[] data = Serialization.serialize(msg);
		out.writeInt(data.length);
		out.writeBytes(data);
	}

}
