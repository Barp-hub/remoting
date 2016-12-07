package com.mysql.mapper;

import com.mysql.entity.GroupMessage;

public interface GroupMessageMapper {
    int deleteByPrimaryKey(Long mid);

    int insert(GroupMessage record);

    int insertSelective(GroupMessage record);

    GroupMessage selectByPrimaryKey(Long mid);

    int updateByPrimaryKeySelective(GroupMessage record);

    int updateByPrimaryKey(GroupMessage record);
}