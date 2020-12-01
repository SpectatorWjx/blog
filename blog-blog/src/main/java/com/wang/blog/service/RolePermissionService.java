package com.wang.blog.service;

import com.wang.common.entity.user.PermissionEntity;
import com.wang.common.entity.user.RolePermissionEntity;

import java.util.List;
import java.util.Set;

/**
 * @author wjx
 * @date  2018/5/18
 */
public interface RolePermissionService {
    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    List<PermissionEntity> findPermissions(String roleId);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteByRoleId(String roleId);

    /**
     * 添加
     * @param rolePermissions
     */
    void add(Set<RolePermissionEntity> rolePermissions);

}
