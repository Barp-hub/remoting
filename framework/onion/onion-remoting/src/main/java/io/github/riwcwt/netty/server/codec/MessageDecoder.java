package io.github.riwcwt.netty.server.codec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.riwcwt.entity.Request;
import io.github.riwcwt.netty.serialize.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

@Component("server-decoder")
public class MessageDecoder extends ByteToMessageDecoder {

	@Autowired
	private Serialization serial = null;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// int类型数据长度为4
		if (in.readableBytes() < 4) {
			return;
		}
		// 我们标记一下当前的readIndex的位置
		in.markReaderIndex();

		// 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
		int length = in.readInt();
		// 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
		if (length < 0) {
			ctx.close();
		}

		// 读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex.
		// 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
		if (in.readableBytes() < length) {
			in.resetReaderIndex();
			return;
		}

		// 嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
		byte[] body = new byte[length];
		in.readBytes(body); //
		// 将byte数据转化为我们需要的对象。伪代码，用什么序列化，自行选择
		out.add(this.serial.deserialize(body, Request.class));
	}

}
