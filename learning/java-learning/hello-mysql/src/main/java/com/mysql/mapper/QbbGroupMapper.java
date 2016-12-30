package com.mysql.mapper;

import com.mysql.entity.QbbGroup;

public interface QbbGroupMapper {
    int deleteByPrimaryKey(Long gid);

    int insert(QbbGroup record);

    int insertSelective(QbbGroup record);

    QbbGroup selectByPrimaryKey(Long gid);

    int updateByPrimaryKeySelective(QbbGroup record);

    int updateByPrimaryKey(QbbGroup record);
}