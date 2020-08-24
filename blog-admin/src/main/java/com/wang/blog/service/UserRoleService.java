package com.wang.blog.service;

import com.wang.common.entity.user.RoleEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author - wjx on 2018/2/11
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

    Map<String, List<RoleEntity>> findMapByUserIds(List<String> userIds);

    /**
     * 修改用户角色
     * @param userId 用户ID
     * @param roleIds 要授权的角色ID
     */
    void updateRole(String userId, Set<String> roleIds);
}
