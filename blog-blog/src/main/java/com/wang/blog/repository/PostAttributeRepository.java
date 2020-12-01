package com.wang.blog.repository;

import com.wang.common.entity.blog.PostAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author  wjx
 * @date 2019/08/13
 */
public interface PostAttributeRepository extends JpaRepository<PostAttributeEntity, String>, JpaSpecificationExecutor<PostAttributeEntity> {
    /**
     * 根据postId查询
     * @param postId
     * @return
     */
    PostAttributeEntity findByPostId(String postId);

    /**
     * 根据postId删除
     * @param postId
     * @return
     */
    void deleteByPostId(String postId);
}
