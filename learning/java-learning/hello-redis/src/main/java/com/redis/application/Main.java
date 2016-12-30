package com.redis.application;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = null;
		try {
			context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
			context.registerShutdownHook();

			RedisTemplate<String, Object> template = context.getBean(RedisTemplate.class);
			//			ThreadPoolTaskExecutor executor = context.getBean(ThreadPoolTaskExecutor.class);

			//			add(template, executor);
			//
			//			keys(template);
			//
			//			delete(template);
			//
			//			operate(template);

			//			database(template, executor);

			connection(template);
		} finally {
			if (context != null) {
				context.close();
			}
		}

	}

	public static void connection(RedisTemplate<String, Object> template) {
		String key = String.valueOf((long) (Math.random() * 100000000));

		logger.info("生成key：" + key);

		String value = String.valueOf(Math.random());

		logger.info("生成value：" + value);

		logger.info("set之前的值:" + template.opsForValue().get(key));
		template.opsForValue().set(key, value);
		logger.info("set之后的值:" + template.opsForValue().get(key));
		template.delete(key);
		logger.info("删除之后的值:" + template.opsForValue().get(key));
	}

	public static void database(RedisTemplate<String, Object> template, ThreadPoolTaskExecutor executor) throws IOException {
		String key = "test:database:key";
		String set = "test:database:set";

		template.opsForValue().set(key, 0);

		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					long start = System.currentTimeMillis();
					for (int i = 0; i < 100000; i++) {
						long n = template.opsForValue().increment(key, 1);
						logger.info("当前数字:" + n);
						template.opsForSet().add(set, n);
					}
					logger.info("耗时:" + (System.currentTimeMillis() - start));
				}
			});
		}
		template.expire(key, 300, TimeUnit.SECONDS);
		template.expire(set, 300, TimeUnit.SECONDS);
		System.in.read();

	}

	public static void operate(RedisTemplate<String, Object> template) {
		String setKey = "test:set";
		template.opsForSet().add(setKey, "a", "b", "c", "a");
		template.expire(setKey, 60, TimeUnit.SECONDS);

		String listKey = "test:list";
		for (int i = 0; i < 10; i++) {
			template.opsForList().leftPush(listKey, i);
		}
		for (int i = 0; i < 3; i++) {
			template.opsForList().rightPop(listKey);
		}
		template.expire(listKey, 60, TimeUnit.SECONDS);

		String hashKey = "test:hash";
		for (int i = 0; i < 10; i++) {
			template.opsForHash().put(hashKey, i, i);
		}
		template.expire(hashKey, 60, TimeUnit.SECONDS);
	}

	public static void keys(RedisTemplate<String, Object> template) {
		Set<String> keys = template.keys("*");
		for (String key : keys) {
			logger.info(key);
		}
	}

	public static void delete(RedisTemplate<String, Object> template) {
		Set<String> keys = template.keys("test:*");
		for (String key : keys) {
			logger.info(key);
			template.delete(key);
		}
	}

	public static void add(RedisTemplate<String, Object> template, ThreadPoolTaskExecutor executor) {
		Runnable task = new Runnable() {

			@Override
			public void run() {

				long start = System.currentTimeMillis();

				for (int i = 0; i < 10; i++) {
					String key = "test:" + ((long) (Math.random() * 100000));

					template.opsForValue().set(key, Math.random());

					template.expire(key, 3, TimeUnit.SECONDS);

					Object data = template.opsForValue().get(key);
					if (data == null) {
						logger.info("未查询到数据！");
					} else {
						logger.info(data.toString());
					}

				}

				logger.info("线程 : " + Thread.currentThread().getName() + "   --   耗时 : " + (System.currentTimeMillis() - start));
			}
		};

		for (int i = 0; i < 10; i++) {
			executor.execute(task);
		}

	}

}
