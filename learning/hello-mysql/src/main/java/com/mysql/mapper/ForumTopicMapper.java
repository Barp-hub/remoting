package com.mysql.mapper;

import com.mysql.entity.ForumTopic;

public interface ForumTopicMapper {
    int deleteByPrimaryKey(Long tid);

    int insert(ForumTopic record);

    int insertSelective(ForumTopic record);

    ForumTopic selectByPrimaryKey(Long tid);

    int updateByPrimaryKeySelective(ForumTopic record);

    int updateByPrimaryKeyWithBLOBs(ForumTopic record);

    int updateByPrimaryKey(ForumTopic record);
}