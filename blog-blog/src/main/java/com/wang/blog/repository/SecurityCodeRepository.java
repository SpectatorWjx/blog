package com.wang.blog.repository;

import com.wang.common.entity.user.SecurityCodeEntity;
import com.wang.common.repository.BaseJpa;

/**
 * @author wjx on 2015/8/14.
 */
public interface SecurityCodeRepository extends BaseJpa<SecurityCodeEntity> {
    SecurityCodeEntity findByKeyAndType(String key, int type);
    SecurityCodeEntity findByKey(String key);
}
