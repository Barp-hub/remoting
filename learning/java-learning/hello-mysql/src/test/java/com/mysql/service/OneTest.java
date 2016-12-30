package com.mysql.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.application.ApplicationConfig;
import com.mysql.entity.FileData;
import com.mysql.util.SequenceUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class OneTest {
	private static final Logger	logger			= LoggerFactory.getLogger(OneTest.class);

	@Autowired
	private SequenceService		sequenceService	= null;

	private List<Long>			data			= new LinkedList<Long>();

	@Autowired
	private FileService			fileService		= null;

	@Autowired
	private SequenceUtil		sequenceUtil	= null;

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

	private AtomicLong	position	= new AtomicLong();

	@Test
	public void atomic() throws IOException {
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 100; j++) {
						sequenceService.statics(position.incrementAndGet());
					}
				}
			}, "线程" + i);

			thread.start();
		}

		System.in.read();
	}

	private Long	current	= Long.valueOf(0);

	@Test
	public void increase() throws IOException {
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 100; j++) {
						Long n = current++;
						sequenceService.statics(n);
					}
				}
			}, "线程" + i);

			thread.start();
		}

		System.in.read();
	}

	@Test
	public void build() {
		for (int i = 0; i < 512; i++) {
			System.out.println("drop table file_data_" + i + ";");
			//			System.out.println("delete from file_data_" + i + ";");

			//			System.out.println("TRUNCATE TABLE file_data_" + i + ";");

			System.out
					.println("CREATE TABLE `file_data_"
							+ i
							+ "` (   `fid` bigint(20) unsigned NOT NULL,   `owner` bigint(20) unsigned DEFAULT NULL,  `uploadTime` datetime DEFAULT NULL,   `origTime` datetime DEFAULT NULL,   `size` int(10) unsigned DEFAULT '0',   `duration` int(11) DEFAULT NULL COMMENT 'the duration in milliseconds',   `filetype` smallint(5) unsigned DEFAULT '0' COMMENT 'refer the file type table',   `width` smallint(5) unsigned DEFAULT '0',   `height` smallint(5) unsigned DEFAULT '0',  `farm` smallint(5) unsigned DEFAULT '0',   `status` tinyint(3) unsigned DEFAULT '0' COMMENT '0-normal;1-delete', `secret` varchar(12) DEFAULT NULL, `gpsInfo` varchar(1024) DEFAULT NULL,  PRIMARY KEY (`fid`)  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;");

			//			System.out.println("SELECT count(*) col FROM `file_data_" + i + "` union all ");
		}

		for (int i = 0; i < 256; i++) {
			//			System.out.println("drop table table_" + i + ";");

			//			System.out.println("delete from file_data_" + i + ";");

			//			System.out.println("TRUNCATE TABLE file_data_" + i + ";");

			//			System.out
			//					.println("CREATE TABLE `file_data_"
			//							+ i
			//							+ "` (   `fid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,   `owner` bigint(20) unsigned DEFAULT NULL,  `uploadTime` datetime DEFAULT NULL,   `origTime` datetime DEFAULT NULL,   `size` int(10) unsigned DEFAULT '0',   `duration` int(11) DEFAULT NULL COMMENT 'the duration in milliseconds',   `filetype` smallint(5) unsigned DEFAULT '0' COMMENT 'refer the file type table',   `width` smallint(5) unsigned DEFAULT '0',   `height` smallint(5) unsigned DEFAULT '0',  `farm` smallint(5) unsigned DEFAULT '0',   `status` tinyint(3) unsigned DEFAULT '0' COMMENT '0-normal;1-delete', `secret` varchar(12) DEFAULT NULL, `gpsInfo` varchar(1024) DEFAULT NULL,  PRIMARY KEY (`fid`)  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		}
	}

	@Test
	public void addAndDeleteFile() throws IOException {
		List<Long> ids = new LinkedList<Long>();

		for (int i = 0; i < 1000; i++) {
			Long id = sequenceUtil.getSequence();

			logger.info("序列：" + id);
			ids.add(id);

			FileData file = new FileData();
			file.setFid(id);
			this.fileService.addFile(file);
		}

		logger.info("写入完成，请输入：");

		System.in.read();

		for (int i = 0; i < 10; i++) {
			FileData file = new FileData();
			file.setFid(ids.get((int) (Math.floor(ids.size() * Math.random()))));
			file = this.fileService.getFile(file);
			logger.info("随机抽取到的ID：" + file.getFid());
		}

		logger.info("随机读取完成，请输入：");
		System.in.read();

		for (Long id : ids) {
			FileData file = new FileData();
			file.setFid(id);
			this.fileService.deleteFile(file);
		}
	}

	@Test
	public void addOrUpdateFile() {
		FileData file = new FileData();
		file.setFid(Long.valueOf(99841));
		file.setOwner(Long.valueOf(146));
		int n = this.fileService.addFile(file);

		logger.info("涉及的行数：" + n);
	}

}
