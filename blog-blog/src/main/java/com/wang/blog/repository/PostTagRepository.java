package com.wang.blog.repository;

import com.wang.common.entity.blog.PostTagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : wjx
 */
@Repository
public interface PostTagRepository extends JpaRepository<PostTagEntity, String>, JpaSpecificationExecutor<PostTagEntity> {
    Page<PostTagEntity> findAllByTagId(Pageable pageable, String tagId);
    PostTagEntity findByPostIdAndTagId(String postId, String tagId);
    int deleteByPostId(String postId);
    List<PostTagEntity> findAllByPostId(String postId);

    List<PostTagEntity> findAllByPostIdNotAndTagId(String postId, String tagId);
}
