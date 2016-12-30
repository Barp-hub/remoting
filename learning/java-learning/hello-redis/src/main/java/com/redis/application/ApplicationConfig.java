package com.redis.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource(value = { "classpath:redis.properties" }, ignoreResourceNotFound = true)
public class ApplicationConfig {

	@Autowired
	private Environment env = null;

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(90);
		executor.setMaxPoolSize(150);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(60);
		return executor;
	}

	@Bean(name = "redis-connection-factory")
	public RedisConnectionFactory jedisConnectionFactory() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(env.getProperty("redis.maxIdle", Integer.class));
		config.setMaxWaitMillis(env.getProperty("redis.maxWait", Long.class));
		config.setTestOnBorrow(env.getProperty("redis.testOnBorrow", Boolean.class));

		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(config);
		connectionFactory.setHostName(env.getProperty("redis.host"));
		connectionFactory.setPort(env.getProperty("redis.port", Integer.class));
		connectionFactory.setPassword(env.getProperty("redis.password"));
		connectionFactory.setUsePool(env.getProperty("redis.use.pool", Boolean.class));
		connectionFactory.setDatabase(env.getProperty("redis.database.index", Integer.class));
		return connectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(@Qualifier(value = "redis-connection-factory") RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		template.setDefaultSerializer(new StringRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		template.setHashKeySerializer(new GenericToStringSerializer<Object>(Object.class));
		template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));

		return template;
	}

}
