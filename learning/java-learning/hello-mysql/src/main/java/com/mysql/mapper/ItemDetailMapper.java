package com.mysql.mapper;

import com.mysql.entity.ItemDetail;
import com.mysql.entity.ItemDetailWithBLOBs;

public interface ItemDetailMapper {
    int deleteByPrimaryKey(Long numIId);

    int insert(ItemDetailWithBLOBs record);

    int insertSelective(ItemDetailWithBLOBs record);

    ItemDetailWithBLOBs selectByPrimaryKey(Long numIId);

    int updateByPrimaryKeySelective(ItemDetailWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ItemDetailWithBLOBs record);

    int updateByPrimaryKey(ItemDetail record);
}