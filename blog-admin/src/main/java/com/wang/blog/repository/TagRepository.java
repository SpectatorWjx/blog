package com.wang.blog.repository;

import com.wang.common.entity.blog.TagEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.stereotype.Repository;

/**
 * @author : wjx
 */
@Repository
public interface TagRepository extends BaseJpa<TagEntity> {
    TagEntity findByName(String name);
}
