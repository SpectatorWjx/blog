package com.wang.blog.service;

import com.wang.common.entity.user.PermissionEntity;
import com.wang.common.entity.user.RolePermissionEntity;

import java.util.List;
import java.util.Set;

/**
 * @author - wjx
 * @create - 2018/5/18
 */
public interface RolePermissionService {
    List<PermissionEntity> findPermissions(String roleId);
    void deleteByRoleId(String roleId);
    void add(Set<RolePermissionEntity> rolePermissions);

}
