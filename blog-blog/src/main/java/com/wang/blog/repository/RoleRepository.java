package com.wang.blog.repository;

import com.wang.common.entity.user.RoleEntity;
import com.wang.common.repository.BaseJpa;

import java.util.List;

public interface RoleRepository extends BaseJpa<RoleEntity> {
    List<RoleEntity> findAllByStatus(int status);
}
