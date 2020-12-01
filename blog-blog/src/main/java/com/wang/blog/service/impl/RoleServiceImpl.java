package com.wang.blog.service.impl;

import com.wang.blog.repository.RoleRepository;
import com.wang.blog.service.RolePermissionService;
import com.wang.blog.service.RoleService;
import com.wang.common.entity.user.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * @author wjx
 * @date 2018/2/11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public Map<String, RoleEntity> findByIds(Set<String> ids) {
        List<RoleEntity> list = roleRepository.findAllById(ids);
        Map<String, RoleEntity> ret = new LinkedHashMap<>();
        list.forEach(po -> {
            RoleEntity vo = toVO(po);
            ret.put(vo.getId(), vo);
        });
        return ret;
    }

    private RoleEntity toVO(RoleEntity po) {
        RoleEntity r = new RoleEntity();
        r.setId(po.getId());
        r.setName(po.getName());
        r.setDescription(po.getDescription());
        r.setStatus(po.getStatus());

        r.setPermissions(rolePermissionService.findPermissions(r.getId()));
        return r;
    }
}
