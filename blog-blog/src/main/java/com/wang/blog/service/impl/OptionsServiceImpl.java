package com.wang.blog.service.impl;

import com.wang.blog.repository.OptionsRepository;
import com.wang.blog.service.OptionsService;
import com.wang.common.entity.blog.OptionsEntity;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wjx
 * @date 2019/08/13
 */
@Service
public class OptionsServiceImpl implements OptionsService {
	@Autowired
	private OptionsRepository optionsRepository;
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public List<OptionsEntity> findAll() {
		List<OptionsEntity> list = optionsRepository.findAll();
		List<OptionsEntity> rets = new ArrayList<>();
		
		for (OptionsEntity po : list) {
			OptionsEntity r = new OptionsEntity();
			BeanUtils.copyProperties(po, r);
			rets.add(r);
		}
		return rets;
	}
}
