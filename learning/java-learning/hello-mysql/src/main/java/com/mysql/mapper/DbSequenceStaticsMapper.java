package com.mysql.mapper;

import com.mysql.entity.DbSequenceStatics;

public interface DbSequenceStaticsMapper {
	int deleteByPrimaryKey(Long position);

	int insert(DbSequenceStatics record);

	int insertSelective(DbSequenceStatics record);

	DbSequenceStatics selectByPrimaryKey(Long position);

	int updateByPrimaryKeySelective(DbSequenceStatics record);

	int updateByPrimaryKey(DbSequenceStatics record);

	int statics(DbSequenceStatics record);
}