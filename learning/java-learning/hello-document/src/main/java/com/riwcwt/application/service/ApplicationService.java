package com.riwcwt.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riwcwt.application.entity.Application;
import com.riwcwt.application.mapper.ApplicationMapper;

@Service
public class ApplicationService {

	@Autowired
	private ApplicationMapper applicationMapper = null;

	public Application getApplication(Integer id) {
		return this.applicationMapper.selectByPrimaryKey(id);
	}

}
