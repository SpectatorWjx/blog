package com.wang.blog.repository;

import com.wang.common.entity.user.RolePermissionEntity;
import com.wang.common.repository.BaseJpa;

import java.util.List;

/**
 * @author - wjx
 * @create - 2018/5/18
 */
public interface RolePermissionRepository extends BaseJpa<RolePermissionEntity> {
    int deleteByRoleId(String roleId);
    List<RolePermissionEntity> findAllByRoleId(String roleId);
}
