package com.wang.blog.service;

import com.wang.common.entity.blog.ChannelEntity;

import java.util.List;

/**
 * 栏目管理
 * 
 * @author wjx
 *
 */
public interface ChannelService {
	/**
	 * 查询所有
	 * @param status
	 * @return
	 */
	List<ChannelEntity> findAll(int status);

	/**
	 * 根据Id查询
	 * @param id
	 * @return
	 */
	ChannelEntity getById(String id);
}
