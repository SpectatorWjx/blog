package com.wang.blog.repository;

import com.wang.common.entity.blog.PostAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author wjx
 * @date 2018/9/27
 */
public interface PostAttributeRepository extends JpaRepository<PostAttributeEntity, String>, JpaSpecificationExecutor<PostAttributeEntity> {
    /**
     * 根据postId查询
     * @param postId
     * @return
     */
    PostAttributeEntity findByPostId(String postId);
}
