package com.redis.application;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class })
public class RedisTest {

	private static final Logger				logger		= LoggerFactory.getLogger(RedisTest.class);

	@Autowired
	private ThreadPoolTaskExecutor			executor	= null;

	@Autowired
	private RedisTemplate<String, Object>	template	= null;

	@Test
	public void thread() {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				logger.info("正常执行...");
			}
		});
	}

	@Test
	public void hash() throws IOException {
		String key = "test:hash";
		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {

				@Override
				public void run() {

					long start = System.currentTimeMillis();

					for (int i = 0; i < 1000; i++) {
						template.opsForHash().put(key, Math.random(), Math.random());
					}

					logger.info("耗时:" + (System.currentTimeMillis() - start));
				}
			});
		}
		template.expire(key, 60, TimeUnit.SECONDS);

		System.in.read();
	}

	@Test
	public void database() throws IOException {
		String key = "test:database:key";
		String set = "test:database:set";

		template.opsForValue().set(key, 0);

		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					long start = System.currentTimeMillis();
					for (int i = 0; i < 1000; i++) {
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
}
