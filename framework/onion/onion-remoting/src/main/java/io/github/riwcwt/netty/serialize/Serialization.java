package io.github.riwcwt.netty.serialize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.stereotype.Component;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

@Component
public class Serialization implements InitializingBean {
	private Map<Class<?>, Schema<?>> cachedSchema = null;
	private Objenesis objenesis = null;

	@SuppressWarnings("unchecked")
	public <T> byte[] serialize(T object) {
		Class<T> clazz = (Class<T>) object.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			Schema<T> schema = this.getSchema(clazz);
			return ProtostuffIOUtil.toByteArray(object, schema, buffer);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	@SuppressWarnings("unchecked")
	private <T> Schema<T> getSchema(Class<T> clazz) {
		Schema<T> schema = Schema.class.cast(this.cachedSchema.get(clazz));
		if (schema == null) {
			schema = RuntimeSchema.createFrom(clazz);
			this.cachedSchema.put(clazz, schema);
		}
		return schema;
	}

	public <T> T deserialize(byte[] data, Class<T> clazz) {
		try {
			T message = this.objenesis.newInstance(clazz);
			Schema<T> schema = this.getSchema(clazz);
			ProtostuffIOUtil.mergeFrom(data, message, schema);
			return message;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
		this.objenesis = new ObjenesisStd(true);
	}

}
