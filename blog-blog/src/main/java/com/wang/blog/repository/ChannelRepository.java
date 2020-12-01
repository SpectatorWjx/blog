package com.wang.blog.repository;

import com.wang.common.entity.blog.ChannelEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wjx
 * @date 2019/08/13
 */
public interface ChannelRepository extends BaseJpa<ChannelEntity> {
	/**
	 * 根据状态查询
	 * @param status
	 * @param sort
	 * @return
	 */
	List<ChannelEntity> findAllByStatus(int status, Sort sort);

	/**
	 * 置顶
	 * @return
	 */
	@Query("select coalesce(max(weight), 0) from ChannelEntity")
	int maxWeight();
}
