package com.mysql.mapper;

import com.mysql.entity.FileDataSequence;

public interface FileDataSequenceMapper {
	int deleteByPrimaryKey(Long id);

	int insert(FileDataSequence record);

	int insertSelective(FileDataSequence record);

	FileDataSequence selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(FileDataSequence record);

	int updateByPrimaryKey(FileDataSequence record);

	Long increase(FileDataSequence record);
}