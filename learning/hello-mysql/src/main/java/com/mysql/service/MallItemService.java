package com.mysql.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.entity.ItemDetailWithBLOBs;
import com.mysql.mapper.ItemDetailMapper;

@Service
public class MallItemService {
	private static final Logger	logger				= LoggerFactory.getLogger(MallItemService.class);

	@Autowired
	private ItemDetailMapper	itemDetailMapper	= null;

	@Transactional(value = "transactionManager")
	public void copy(Long id, int count) {
		logger.info("拷贝商品ID：" + id + "    数量：" + count);

		ItemDetailWithBLOBs item = this.itemDetailMapper.selectByPrimaryKey(id);
		if (item == null) {
			logger.error("改商品不存在！");
			return;
		}
		item.setNumIId(null);
		String title = item.getTitle();
		for (int i = 0; i < count; i++) {
			item.setTitle(title + "-" + i);
			this.itemDetailMapper.insert(item);
		}
	}
}
