package com.mysql.mapper;

import com.mysql.entity.FileData;

public interface FileDataMapper {
	int deleteByPrimaryKey(Long fid);

	int insert(FileData record);

	int insertSelective(FileData record);

	FileData selectByPrimaryKey(Long fid);

	int updateByPrimaryKeySelective(FileData record);

	int updateByPrimaryKey(FileData record);

	int addFile(FileData file);

	int deleteFile(FileData file);
	
	FileData getFile(FileData record);
}