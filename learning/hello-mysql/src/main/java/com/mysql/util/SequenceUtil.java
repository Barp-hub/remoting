package com.mysql.util;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.entity.DbSequence;
import com.mysql.service.FileService;
import com.mysql.service.SequenceService;

@Component
public class SequenceUtil {

	@Autowired
	private SequenceService	sequenceService	= null;

	private AtomicLong		position		= null;
	private AtomicLong		max				= null;

	private synchronized void updatePosition() {

		DbSequence sequence = this.sequenceService.getNextSequence(FileService.TABLE_FILE);
		position.set(sequence.getCurrentposition());
		max.set(sequence.getCurrentposition() + 100);
	}

	public Long getSequence() {
		if (position == null) {
			position = new AtomicLong();
			max = new AtomicLong();
			this.updatePosition();
		}
		synchronized (max) {
			Long sequence = position.getAndIncrement();
			if (sequence < max.get()) {
				return sequence;
			}
		}
		this.updatePosition();
		return getSequence();
	}
}
