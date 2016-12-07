package com.mysql.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.application.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class SequenceServiceTest {
	private static final Logger	logger			= LoggerFactory.getLogger(SequenceServiceTest.class);

	@Autowired
	private SequenceService		sequenceService	= null;

	private List<Long>			data			= new LinkedList<Long>();

	@Test
	public void thread() throws IOException {

		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 10; j++) {
						Long position = sequenceService.getNextSequence("file_data").getCurrentposition();
						data.add(position);
						sequenceService.statics(position);
						logger.info("当前线程:" + Thread.currentThread().getName() + "   位置:" + String.valueOf(position));

						//						try {
						//							Thread.sleep(1);
						//						} catch (InterruptedException e) {
						//							logger.error(e.getMessage());
						//						}
					}

				}
			}, "线程" + i);

			thread.start();
		}

		System.in.read();

		Map<String, Integer> statics = this.sequenceService.getStatics();
		Iterator<Entry<String, Integer>> iterator = statics.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			logger.warn("线程：" + entry.getKey() + "  失败数：" + entry.getValue());
		}

		for (Long l : data) {
			logger.warn("取得的数据：" + l);
		}
	}
}
