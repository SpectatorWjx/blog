package com.wang.blog.repository;

import com.wang.common.entity.blog.PostResourceEntity;
import com.wang.common.repository.BaseJpa;

import java.util.Collection;
import java.util.List;

/**
 * @author wjx
 * @date 2019/08/13
 */
public interface PostResourceRepository extends BaseJpa<PostResourceEntity> {

    int deleteByPostId(String postId);

    int deleteByPostIdAndResourceIdIn(String postId, Collection<String> resourceId);

    List<PostResourceEntity> findByResourceId(String resourceId);

    List<PostResourceEntity> findByPostId(String postId);

}
