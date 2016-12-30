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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class FileSequenceServiceTest {

	private static final Logger	logger			= LoggerFactory.getLogger(FileSequenceServiceTest.class);

	@Autowired
	private FileSequenceService	sequenceService	= null;

	@Autowired
	private SequenceService		statics			= null;

	@Test
	public void thread() throws IOException {
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 100; j++) {

						long start = System.currentTimeMillis();

						Long next = sequenceService.next();

						logger.info("耗时:" + (System.currentTimeMillis() - start));
						//						logger.info("取得的序列号:" + next);

						statics.statics(next);
					}

				}
			}, "线程" + i);

			thread.start();
		}

		System.in.read();

	}

	@Test
	public void single() throws IOException {
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 100; j++) {

						long start = System.currentTimeMillis();

						Long next = sequenceService.next();

						logger.info("耗时:" + (System.currentTimeMillis() - start));
						//						logger.info("取得的序列号:" + next);

						statics.statics(next);
					}

				}
			}, "线程" + i);

			thread.start();
		}

		System.in.read();

	}

}
