package com.wang.blog.service;

import com.wang.common.entity.user.RoleEntity;

import java.util.Map;
import java.util.Set;

/**
 * @author wjx
 * @date 2018/2/11
 */
public interface RoleService {

    /**
     * 根据ids查询
     * @param ids
     * @return
     */
    Map<String, RoleEntity> findByIds(Set<String> ids);
}
