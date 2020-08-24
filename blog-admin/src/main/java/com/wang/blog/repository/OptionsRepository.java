package com.wang.blog.repository;

import com.wang.common.entity.blog.OptionsEntity;
import com.wang.common.repository.BaseJpa;

/**
 * @author wjx
 */
public interface OptionsRepository extends BaseJpa<OptionsEntity> {
	OptionsEntity findByKey(String key);
}
