package com.mysql.mapper;

import com.mysql.entity.GroupBaby;

public interface GroupBabyMapper {
    int deleteByPrimaryKey(Long gbid);

    int insert(GroupBaby record);

    int insertSelective(GroupBaby record);

    GroupBaby selectByPrimaryKey(Long gbid);

    int updateByPrimaryKeySelective(GroupBaby record);

    int updateByPrimaryKey(GroupBaby record);
}