package com.wang.blog.repository;

import com.wang.common.entity.blog.ChannelEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wjx
 *
 */
public interface ChannelRepository extends BaseJpa<ChannelEntity> {
	List<ChannelEntity> findAllByStatus(int status, Sort sort);

	@Query("select coalesce(max(weight), 0) from ChannelEntity")
	int maxWeight();
}
