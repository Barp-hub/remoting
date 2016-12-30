package com.riwcwt.application.mapper;

import com.riwcwt.application.entity.Application;

public interface ApplicationMapper {
	int deleteByPrimaryKey(Integer appid);

	int insert(Application record);

	int insertSelective(Application record);

	Application selectByPrimaryKey(Integer appid);

	int updateByPrimaryKeySelective(Application record);

	int updateByPrimaryKeyWithBLOBs(Application record);

	int updateByPrimaryKey(Application record);
}