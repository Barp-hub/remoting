package com.riwcwt.protostuff;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoStuffTest {

	private Gson gson = new GsonBuilder().create();

	@Test
	public void encode() throws IOException {
		Entity entity = new Entity();
		entity.setId(1);
		entity.setName("michael");

		System.out.println(gson.toJson(entity));

		Schema<Entity> schema = RuntimeSchema.getSchema(Entity.class);
		LinkedBuffer buffer = LinkedBuffer.allocate();

		byte[] data = ProtostuffIOUtil.toByteArray(entity, schema, buffer);

		buffer.clear();

		Entity two = schema.newMessage();
		System.out.println(gson.toJson(two));
		ProtostuffIOUtil.mergeFrom(data, two, schema);
		System.out.println(gson.toJson(two));
		System.out.println(gson.toJson(entity));

		FileUtils.deleteQuietly(new File("data.txt"));
		FileUtils.writeStringToFile(new File("data.txt"), new String(data));

	}

	@Test
	public void decode() throws IOException {
		Schema<EntityV2> schema = RuntimeSchema.getSchema(EntityV2.class);
		LinkedBuffer buffer = LinkedBuffer.allocate();

		byte[] data = FileUtils.readFileToByteArray(new File("data.txt"));
		EntityV2 two = schema.newMessage();
		ProtostuffIOUtil.mergeFrom(data, two, schema);
		System.out.println(gson.toJson(two));

		two.setGender("F");
		System.out.println(gson.toJson(two));

		data = ProtostuffIOUtil.toByteArray(two, schema, buffer);

		buffer.clear();
		FileUtils.deleteQuietly(new File("datav2.txt"));
		FileUtils.writeStringToFile(new File("datav2.txt"), new String(data));
	}

	@Test
	public void compatible() throws IOException {
		byte[] data = FileUtils.readFileToByteArray(new File("datav2.txt"));

		Schema<Entity> schema = RuntimeSchema.getSchema(Entity.class);

		Entity entity = schema.newMessage();
		ProtostuffIOUtil.mergeFrom(data, entity, schema);
		System.out.println(gson.toJson(entity));
	}

}
