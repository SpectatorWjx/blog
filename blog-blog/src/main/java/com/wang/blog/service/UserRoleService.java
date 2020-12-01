package com.wang.blog.service;

import com.wang.common.entity.user.RoleEntity;

import java.util.List;

/**
 * @author  wjx
 * @date 2018/2/11
 */
public interface UserRoleService {
    /**
     * 查询用户已有的角色Id
     * @param userId 用户ID
     * @return
     */
    List<String> listRoleIds(String userId);

    /**
     * 查询用户已有的角色 和 权限
     * @param userId 用户ID
     * @return
     */
    List<RoleEntity> listRoles(String userId);
}
