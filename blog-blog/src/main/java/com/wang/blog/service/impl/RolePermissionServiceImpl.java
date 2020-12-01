package com.wang.blog.service.impl;

import com.wang.blog.repository.PermissionRepository;
import com.wang.blog.repository.RolePermissionRepository;
import com.wang.blog.service.RolePermissionService;
import com.wang.common.entity.user.PermissionEntity;
import com.wang.common.entity.user.RolePermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  wjx
 * @date 2018/5/18
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PermissionEntity> findPermissions(String roleId) {
        List<RolePermissionEntity> rps = rolePermissionRepository.findAllByRoleId(roleId);

        List<PermissionEntity> rets = null;
        if (rps != null && rps.size() > 0) {
            Set<String> pids = new HashSet<>();
            rps.forEach(rp -> pids.add(rp.getPermissionId()));
            rets = permissionRepository.findAllById(pids);
        }
        return rets;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteByRoleId(String roleId) {
        rolePermissionRepository.deleteByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void add(Set<RolePermissionEntity> rolePermissions) {
        rolePermissionRepository.saveAll(rolePermissions);
    }
}
