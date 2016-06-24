package io.github.riwcwt.netty.server.codec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.riwcwt.entity.Response;
import io.github.riwcwt.netty.serialize.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@Component("server-encoder")
public class MessageEncoder extends MessageToByteEncoder<Response> {

	@Autowired
	private Serialization serial = null;

	@Override
	protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
		byte[] data = this.serial.serialize(msg);
		out.writeInt(data.length);
		out.writeBytes(data);
	}

}
