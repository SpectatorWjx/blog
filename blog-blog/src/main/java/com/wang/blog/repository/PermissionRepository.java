package com.wang.blog.repository;

import com.wang.common.entity.user.PermissionEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author wjx on 2018/8/12.
 */
public interface PermissionRepository extends BaseJpa<PermissionEntity> {
    List<PermissionEntity> findAllByParentId(String parentId, Sort sort);

    @Query(value = "select count(role_id) from shiro_role_permission where permission_id=:permId", nativeQuery = true)
    int countUsed(@Param("permId") String permId);

    @Query("select coalesce(max(weight), 0) from PermissionEntity")
    int maxWeight();
}
