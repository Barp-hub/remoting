package com.mysql.mapper;

import com.mysql.entity.DbSequence;

public interface DbSequenceMapper {
	int deleteByPrimaryKey(String tablename);

	int insert(DbSequence record);

	int insertSelective(DbSequence record);

	DbSequence selectByPrimaryKey(String tablename);

	int updateByPrimaryKeySelective(DbSequence record);

	int updateByPrimaryKey(DbSequence record);

	int increaseSequence(DbSequence record);
}