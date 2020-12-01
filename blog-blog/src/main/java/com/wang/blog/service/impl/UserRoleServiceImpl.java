package com.wang.blog.service.impl;

import com.wang.blog.repository.UserRoleRepository;
import com.wang.blog.service.RoleService;
import com.wang.blog.service.UserRoleService;
import com.wang.common.entity.user.RoleEntity;
import com.wang.common.entity.user.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wjx
 * @date 2018/2/11
 */
@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleService roleService;

    @Override
    public List<String> listRoleIds(String userId) {
        List<UserRoleEntity> list = userRoleRepository.findAllByUserId(userId);
        List<String> roleIds = new ArrayList<>();
        if (null != list) {
            list.forEach(po -> roleIds.add(po.getRoleId()));
        }
        return roleIds;
    }

    @Override
    public List<RoleEntity> listRoles(String userId) {
        List<String> roleIds = listRoleIds(userId);
        return new ArrayList<>(roleService.findByIds(new HashSet<>(roleIds)).values());
    }
}
