package com.wang.blog.service;

import com.wang.common.entity.blog.OptionsEntity;
import org.springframework.core.io.Resource;

import java.util.List;


/**
 * @author wjx
 *
 */
public interface OptionsService {
	/**
	 * 查询所有配置
	 * @return list
	 */
	List<OptionsEntity> findAll();
}
