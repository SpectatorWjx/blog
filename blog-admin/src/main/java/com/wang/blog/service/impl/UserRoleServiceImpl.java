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
 * @author - wjx on 2018/2/11
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

    @Override
    public Map<String, List<RoleEntity>> findMapByUserIds(List<String> userIds) {
        List<UserRoleEntity> list = userRoleRepository.findAllByUserIdIn(userIds);
        Map<String, Set<String>> map = new HashMap<>();

        list.forEach(po -> {
            Set<String> roleIds = map.computeIfAbsent(po.getUserId(), k -> new HashSet<>());
            roleIds.add(po.getRoleId());
        });

        Map<String, List<RoleEntity>> ret = new HashMap<>();
        map.forEach((k, v) -> {
            ret.put(k, new ArrayList<>(roleService.findByIds(v).values()));
        });
        return ret;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateRole(String userId, Set<String> roleIds) {
        // 判断是否清空已授权角色
        if (null == roleIds || roleIds.isEmpty()) {
            userRoleRepository.deleteByUserId(userId);
        } else {
            List<UserRoleEntity> list = userRoleRepository.findAllByUserId(userId);
            List<String> exitIds = new ArrayList<>();

            // 如果已有角色不在 新角色列表中, 执行删除操作
            if (null != list) {
                list.forEach(po -> {
                    if (!roleIds.contains(po.getRoleId())) {
                        userRoleRepository.delete(po);
                    } else {
                        exitIds.add(po.getRoleId());
                    }
                });
            }

            // 保存不在已有角色中的新角色ID
            roleIds.stream().filter(id -> !exitIds.contains(id)).forEach(roleId -> {
                UserRoleEntity po = new UserRoleEntity();
                po.setUserId(userId);
                po.setRoleId(roleId);

                userRoleRepository.save(po);
            });
        }


    }
}
