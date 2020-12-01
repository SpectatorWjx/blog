package com.wang.blog.service;

import com.wang.common.entity.blog.OptionsEntity;
import org.springframework.core.io.Resource;

import java.util.List;


/**
 * @author wjx
 * @date 2019/08/13
 */
public interface OptionsService {
	/**
	 * 查询所有配置
	 * @return list
	 */
	List<OptionsEntity> findAll();
}
