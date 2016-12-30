package com.mysql.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.application.ApplicationConfig;
import com.mysql.entity.ForumTopic;
import com.mysql.mapper.ForumTopicMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class NoTransactionTest {
	private static final Logger	logger		= LoggerFactory.getLogger(NoTransactionTest.class);

	@Autowired
	private ForumTopicMapper	topicMapper	= null;

	@Test
	public void delete() {
		for (int i = 2759; i < 3000; i++) {
			long start = System.currentTimeMillis();
			this.topicMapper.deleteByPrimaryKey(Long.valueOf(i));
			logger.info("耗时：" + (System.currentTimeMillis() - start));
		}
	}

	@Test
	public void add() throws IOException {
		final ForumTopic topic = this.topicMapper.selectByPrimaryKey(Long.valueOf(7));
		topic.setTid(null);
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 100; j++) {
						long start = System.currentTimeMillis();
						topicMapper.insert(topic);
						logger.info("插入耗时：" + (System.currentTimeMillis() - start));
					}
				}
			}).start();
		}
		final long id = 9632;
		for (int i = 0; i < 100; i++) {
			final int step = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 200; j++) {
						long start = System.currentTimeMillis();
						ForumTopic topic = new ForumTopic();
						topic.setTid(Long.valueOf(id + step * 200 + j));
						topic.setStatus(Short.valueOf("1"));
						topicMapper.updateByPrimaryKeySelective(topic);
						logger.info("更新耗时：" + (System.currentTimeMillis() - start));
					}
				}
			}).start();
		}

		System.in.read();
	}
}
