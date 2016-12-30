package com.mysql.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
public class FileServiceTest {
	private static final Logger	logger			= LoggerFactory.getLogger(FileServiceTest.class);

	@Autowired
	private FileService			fileService		= null;

	@Autowired
	private SequenceUtil		sequenceUtil	= null;

	@Test
	public void addFile() {
		for (int i = 1; i <= 6; i++) {
			FileData file = new FileData();
			file.setFid(Long.valueOf(i));
			this.fileService.addFile(file);
		}
	}

	@Test
	public void deleteFile() {
		for (int i = 1; i <= 6; i++) {
			FileData file = new FileData();
			file.setFid(Long.valueOf(i));
			this.fileService.deleteFile(file);
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
}
