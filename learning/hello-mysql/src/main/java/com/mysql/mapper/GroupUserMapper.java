package com.mysql.mapper;

import com.mysql.entity.GroupUser;

public interface GroupUserMapper {
    int deleteByPrimaryKey(Long guid);

    int insert(GroupUser record);

    int insertSelective(GroupUser record);

    GroupUser selectByPrimaryKey(Long guid);

    int updateByPrimaryKeySelective(GroupUser record);

    int updateByPrimaryKey(GroupUser record);
}