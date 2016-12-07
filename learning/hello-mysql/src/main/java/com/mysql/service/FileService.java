package com.mysql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.entity.FileData;
import com.mysql.mapper.FileDataMapper;

@Service
public class FileService {

	public static final String	TABLE_FILE	= "file_data";

	private String getTable(Long id) {
		return TABLE_FILE + "_" + (id % 512);
	}

	@Autowired
	private FileDataMapper	fileDataMapper	= null;

	@Transactional(value = "transactionManager")
	public int addFile(FileData file) {
		file.setTable(this.getTable(file.getFid()));
		return this.fileDataMapper.addFile(file);
	}

	@Transactional(value = "transactionManager")
	public void deleteFile(FileData file) {
		file.setTable(this.getTable(file.getFid()));
		this.fileDataMapper.deleteFile(file);
	}

	public FileData getFile(FileData file) {
		file.setTable(this.getTable(file.getFid()));
		return this.fileDataMapper.getFile(file);
	}
}
