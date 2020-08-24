package com.wang.blog.service;

import com.wang.common.entity.blog.ChannelEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 栏目管理
 * 
 * @author wjx
 *
 */
public interface ChannelService {
	List<ChannelEntity> findAll(int status);
	Map<String, ChannelEntity> findMapByIds(Collection<String> ids);
	ChannelEntity getById(String id);
	void update(ChannelEntity channel);
	void updateWeight(String id, int weighted);
	void delete(String id);
	long count();
}
