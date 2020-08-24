package com.wang.blog.service.impl;

import com.wang.blog.vo.PermissionTree;
import com.wang.blog.repository.PermissionRepository;
import com.wang.blog.service.PermissionService;
import com.wang.common.common.utils.StringUtil;
import com.wang.common.entity.user.PermissionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author - wjx on 2018/2/11
 */
@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    private Sort sort = Sort.by(
            new Sort.Order(Sort.Direction.DESC, "weight"),
            new Sort.Order(Sort.Direction.ASC, "id")
    );

    @Override
    public Page<PermissionEntity> paging(Pageable pageable, String name) {
        Page<PermissionEntity> page = permissionRepository.findAll((root, query, builder) -> {
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
    public List<PermissionTree> tree() {
        List<PermissionEntity> data = permissionRepository.findAll(sort);
        List<PermissionTree> results = new LinkedList<>();
        Map<String, PermissionTree> map = new LinkedHashMap<>();

        for (PermissionEntity po : data) {
            PermissionTree m = new PermissionTree();
            BeanUtils.copyProperties(po, m);
            map.put(po.getId(), m);
        }

        for (PermissionTree m : map.values()) {
            if (StringUtil.isEmpty(m.getParentId())) {
                results.add(m);
            } else {
                PermissionTree p = map.get(m.getParentId());
                if (p != null) {
                    p.addItem(m);
                }
            }
        }

        return results;
    }

    @Override
    public List<PermissionTree> tree(String parentId) {
        List<PermissionEntity> list = permissionRepository.findAllByParentId(parentId, sort);
        List<PermissionTree> results = new ArrayList<>();

        list.forEach(po -> {
            PermissionTree menu = new PermissionTree();
            BeanUtils.copyProperties(po, menu);
            results.add(menu);
        });
        return results;
    }

    @Override
    public List<PermissionEntity> list() {
        return permissionRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    @Override
    public PermissionEntity get(String id) {
        return permissionRepository.findById(id).get();
    }

}
