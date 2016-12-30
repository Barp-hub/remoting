package com.mysql.mapper;

import com.mysql.entity.GroupCategory;

public interface GroupCategoryMapper {
    int deleteByPrimaryKey(Long cid);

    int insert(GroupCategory record);

    int insertSelective(GroupCategory record);

    GroupCategory selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(GroupCategory record);

    int updateByPrimaryKey(GroupCategory record);
}