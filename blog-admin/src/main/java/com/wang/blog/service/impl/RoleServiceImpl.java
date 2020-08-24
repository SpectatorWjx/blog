package com.wang.blog.service.impl;

import com.wang.blog.repository.PermissionRepository;
import com.wang.blog.repository.RoleRepository;
import com.wang.blog.repository.UserRoleRepository;
import com.wang.blog.service.RolePermissionService;
import com.wang.blog.service.RoleService;
import com.wang.common.entity.user.RoleEntity;
import com.wang.common.entity.user.RolePermissionEntity;
import com.wang.common.entity.user.UserRoleEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author - wjx on 2018/2/11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Page<RoleEntity> paging(Pageable pageable, String name) {
        Page<RoleEntity> page = roleRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (StringUtils.isNoneBlank(name)) {
                predicate.getExpressions().add(
                        builder.like(root.get("name"), "%" + name + "%"));
            }

            query.orderBy(builder.desc(root.get("id")));
            return predicate;
        }, pageable);
        return page;
    }

    @Override
    public List<RoleEntity> list() {
        List<RoleEntity> list = roleRepository.findAllByStatus(RoleEntity.STATUS_NORMAL);
        return list;
    }

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

    @Override
    public RoleEntity get(String id) {
        return toVO(roleRepository.findById(id).get());
    }

    @Override
    public void update(RoleEntity r, Set<String> permissions) {
        Optional<RoleEntity> optional = roleRepository.findById(r.getId());
        RoleEntity po = optional.orElse(new RoleEntity());
            po.setName(r.getName());
        po.setDescription(r.getDescription());
        po.setStatus(r.getStatus());

        roleRepository.save(po);

        rolePermissionService.deleteByRoleId(po.getId());

        if (permissions != null && permissions.size() > 0) {
            Set<RolePermissionEntity> rps = new HashSet<>();
            String roleId = po.getId();
            permissions.forEach(p -> {
                RolePermissionEntity rp = new RolePermissionEntity();
                rp.setRoleId(roleId);
                rp.setPermissionId(p);
                rps.add(rp);
            });

            rolePermissionService.add(rps);
        }
    }

    @Override
    public boolean delete(String id) {
        List<UserRoleEntity> urs = userRoleRepository.findAllByRoleId(id);
        Assert.state(urs == null || urs.size() == 0, "该角色已经被使用,不能被删除");
        roleRepository.deleteById(id);
        rolePermissionService.deleteByRoleId(id);
        return true;
    }

    @Override
    public void activate(String id, boolean active) {
        RoleEntity po = roleRepository.findById(id).get();
        po.setStatus(active ? RoleEntity.STATUS_NORMAL : RoleEntity.STATUS_CLOSED);
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
