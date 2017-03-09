package io.github.riwcwt.variable.serial;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class Serialization {
	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
	private static Objenesis objenesis = new ObjenesisStd(true);

	@SuppressWarnings("unchecked")
	public static <T> byte[] serialize(T object) {
		Class<T> clazz = (Class<T>) object.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			Schema<T> schema = getSchema(clazz);
			return ProtostuffIOUtil.toByteArray(object, schema, buffer);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Schema<T> getSchema(Class<T> clazz) {
		Schema<T> schema = Schema.class.cast(cachedSchema.get(clazz));
		if (schema == null) {
			schema = RuntimeSchema.createFrom(clazz);
			cachedSchema.put(clazz, schema);
		}
		return schema;
	}

	public static <T> T deserialize(byte[] data, Class<T> clazz) {
		try {
			T message = objenesis.newInstance(clazz);
			Schema<T> schema = getSchema(clazz);
			ProtostuffIOUtil.mergeFrom(data, message, schema);
			return message;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
