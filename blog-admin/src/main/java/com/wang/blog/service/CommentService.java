package com.wang.blog.service;

import com.wang.blog.vo.CommentVO;
import com.wang.common.entity.blog.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wjx
 *
 */
public interface CommentService {
	Page<CommentVO> paging4Admin(Pageable pageable);

	Page<CommentVO> pagingByAuthorId(Pageable pageable, String authorId);

	/**
	 * 查询评论列表
	 * @param pageable
	 * @param postId
	 */
	Page<CommentVO> pagingByPostId(Pageable pageable, String postId);

	List<CommentVO> findLatestComments(int maxResults);

	Map<String, CommentVO> findByIds(Set<String> ids);

	CommentEntity findById(String id);
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	String  post(CommentVO comment);
	
	void delete(List<String> ids);

	void delete(String id, String authorId);

	void deleteByPostId(String postId);

	long count();

	long countByAuthorIdAndPostId(String authorId, String postId);
}
