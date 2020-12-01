package com.wang.blog.repository;

import com.wang.common.entity.blog.OptionsEntity;
import com.wang.common.repository.BaseJpa;

/**
 * @author wjx
 * @date 2019/08/13
 */
public interface OptionsRepository extends BaseJpa<OptionsEntity> {
	OptionsEntity findByKey(String key);
}
