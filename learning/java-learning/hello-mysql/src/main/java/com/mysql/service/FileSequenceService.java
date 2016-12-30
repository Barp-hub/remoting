package com.mysql.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.entity.FileDataSequence;
import com.mysql.mapper.FileDataSequenceMapper;

@Service
public class FileSequenceService {
	private static final Logger		logger			= LoggerFactory.getLogger(FileSequenceService.class);

	private static final Long		STEP			= Long.valueOf(10);

	private static final Long		FROM			= Long.valueOf(1000);

	private Long					current			= Long.valueOf(0);

	private Long					max				= Long.MIN_VALUE;

	@Autowired
	private FileDataSequenceMapper	sequenceMapper	= null;

	public synchronized Long next() {
		if (current >= max) {
			Long id = this.getId();

			logger.info("数据库取得的ID:" + id);

			max = FROM + id * STEP;
			current = max - STEP;
		}
		return current++;
	}

	private Long getId() {
		FileDataSequence sequence = new FileDataSequence();
		this.sequenceMapper.increase(sequence);
		return sequence.getId();
	}
}
