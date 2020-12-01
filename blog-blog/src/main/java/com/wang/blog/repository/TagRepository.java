package com.wang.blog.repository;

import com.wang.common.entity.blog.TagEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.stereotype.Repository;

/**
 * @author wjx
 * @date 2020/01/12
 */
@Repository
public interface TagRepository extends BaseJpa<TagEntity> {
    TagEntity findByName(String name);
}
