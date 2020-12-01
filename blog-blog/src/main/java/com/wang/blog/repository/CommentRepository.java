package com.wang.blog.repository;

import com.wang.common.entity.blog.CommentEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * @author wjx
 * @date 2019/08/13
 */
public interface CommentRepository extends BaseJpa<CommentEntity> {
	Page<CommentEntity> findAllByPostId(Pageable pageable, String postId);
	Page<CommentEntity> findAllByAuthorId(Pageable pageable, String authorId);
	List<CommentEntity> removeByIdIn(Collection<String> ids);
	List<CommentEntity> removeByPostId(String postId);
	long countByAuthorIdAndPostId(String authorId, String postId);
}
