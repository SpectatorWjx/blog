package com.wang.blog.repository;

import com.wang.common.entity.blog.FavoriteEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author wjx
 * @date 2019/08/13
 */
public interface FavoriteRepository extends BaseJpa<FavoriteEntity> {
    FavoriteEntity findByUserIdAndPostId(String userId, String postId);
    Page<FavoriteEntity> findAllByUserId(Pageable pageable, String userId);
    int deleteByPostId(String postId);
}
