package io.github.riwcwt.server;

import io.github.riwcwt.variable.entity.Response;
import io.github.riwcwt.variable.serial.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Response> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
		byte[] data = Serialization.serialize(msg);
		out.writeInt(data.length);
		out.writeBytes(data);
	}

}
