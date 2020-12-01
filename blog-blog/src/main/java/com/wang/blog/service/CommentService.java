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
 * @date 2019/08/13
 */
public interface CommentService {

	/**
	 * 根据作者分页
	 * @param pageable
	 * @param authorId
	 * @return
	 */
	Page<CommentVO> pagingByAuthorId(Pageable pageable, String authorId);

	/**
	 * 查询评论列表
	 * @param pageable
	 * @param postId
	 */
	Page<CommentVO> pagingByPostId(Pageable pageable, String postId);

	/**
	 * ids查询
	 * @param ids
	 * @return
	 */
	Map<String, CommentVO> findByIds(Set<String> ids);

	/**
	 * id查询
	 * @param id
	 * @return
	 */
	CommentEntity findById(String id);
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	String post(CommentVO comment);

	/**
	 * 删除
	 * @param id
	 * @param authorId
	 */
	void delete(String id, String authorId);

	/**
	 * 根据文章删除
	 * @param postId
	 */
	void deleteByPostId(String postId);

	/**
	 * 查询数量
	 * @param authorId
	 * @param postId
	 * @return
	 */
	long countByAuthorIdAndPostId(String authorId, String postId);
}
