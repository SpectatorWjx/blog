package com.wang.blog.repository;

import com.wang.common.entity.user.UserRoleEntity;
import com.wang.common.repository.BaseJpa;

import java.util.Collection;
import java.util.List;

public interface UserRoleRepository extends BaseJpa<UserRoleEntity> {
    List<UserRoleEntity> findAllByUserId(String userId);

    List<UserRoleEntity> findAllByUserIdIn(Collection<String> userId);

    List<UserRoleEntity> findAllByRoleId(String roleId);

    /**
     * 清除权限
     *
     * @param userId 用户ID
     */
    int deleteByUserId(String userId);
}
