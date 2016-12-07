package com.mysql.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.entity.DbSequence;
import com.mysql.entity.DbSequenceStatics;
import com.mysql.mapper.DbSequenceMapper;
import com.mysql.mapper.DbSequenceStaticsMapper;

@Service
public class SequenceService {
	private static final Logger		logger			= LoggerFactory.getLogger(SequenceService.class);

	@Autowired
	private DbSequenceMapper		sequenceMapper	= null;

	@Autowired
	private DbSequenceStaticsMapper	staticsMapper	= null;

	private Map<String, Integer>	statics			= new HashMap<String, Integer>();

	public Map<String, Integer> getStatics() {
		return statics;
	}

	public DbSequence getTableSequence(String table) {
		return this.sequenceMapper.selectByPrimaryKey(table);
	}

	private DbSequence nextSequence(String table) {
		DbSequence sequence = this.sequenceMapper.selectByPrimaryKey(table);
		logger.info("当前线程:" + Thread.currentThread().getName() + "   位置:" + sequence.getCurrentposition());

		int row = this.sequenceMapper.increaseSequence(sequence);
		logger.info("当前线程:" + Thread.currentThread().getName() + "   更新的行数:" + row);
		if (row > 0) {
			return sequence;
		}
		logger.warn("当前线程:" + Thread.currentThread().getName());
		Integer count = statics.get(Thread.currentThread().getName());
		if (count == null) {
			statics.put(Thread.currentThread().getName(), Integer.valueOf(1));
		} else {
			statics.put(Thread.currentThread().getName(), count + 1);
		}
		return nextSequence(table);
	}

	@Transactional(value = "transactionManager")
	public DbSequence getNextSequence(String table) {
		return this.nextSequence(table);
	}

	@Transactional(value = "transactionManager")
	public void statics(Long position) {
		DbSequenceStatics statics = new DbSequenceStatics();
		statics.setPosition(position);
		this.staticsMapper.statics(statics);
	}
}
