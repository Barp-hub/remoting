package com.mysql.mapper;

import com.mysql.entity.GroupJoinRequest;

public interface GroupJoinRequestMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(GroupJoinRequest record);

    int insertSelective(GroupJoinRequest record);

    GroupJoinRequest selectByPrimaryKey(Long rid);

    int updateByPrimaryKeySelective(GroupJoinRequest record);

    int updateByPrimaryKey(GroupJoinRequest record);
}