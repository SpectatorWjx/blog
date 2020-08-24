package com.wang.blog.service;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.vo.PostVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文章管理
 * @author wjx
 *
 */
@CacheConfig(cacheNames = Consts.CACHE_POST)
public interface PostService {
	/**
	 * 分页查询所有文章
	 * @param pageable
	 * @param channelId 分组Id
	 * @return
	 */
	@Cacheable
	Page<PostVO> paging(Pageable pageable, String channelId);

	/**
	 * 查询个人发布文章
	 * @param userId
	 * @return
	 */
	@Cacheable
	List<PostVO> listByAuthorId(String userId);

	/**
	 * 查询最近更新 - 按发布时间排序
	 * @param maxResults
	 * @return
	 */
	@Cacheable(key = "'latest_' + #maxResults")
	List<PostVO> findLatestPosts(int maxResults);

	/**
	 * 查询热门文章 - 按浏览次数排序
	 * @param maxResults
	 * @return
	 */
	@Cacheable(key = "'hottest_' + #maxResults")
	List<PostVO> findHottestPosts(int maxResults);
	
	/**
	 * 根据Ids查询
	 * @param ids
	 * @return <id, 文章对象>
	 */
	Map<String, PostVO> findMapByIds(Set<String> ids);

	/**
	 * 发布文章
	 * @param post
	 * @return
	 */
	@CacheEvict(allEntries = true)
	String post(PostVO post);
	
	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	@Cacheable(key = "'post_' + #id")
    PostVO get(String id);

	/**
	 * 更新文章
	 * @param p
	 */
	@CacheEvict(allEntries = true)
	void update(PostVO p);
	
	/**
	 * 带作者验证的删除 - 验证是否属于自己的文章
	 * @param id
	 * @param authorId
	 */
	@CacheEvict(allEntries = true)
	void delete(String id, String authorId);
	
	/**
	 * 自增浏览数
	 * @param id
	 */
	@CacheEvict(key = "'post_' + #id")
	void identityViews(String id);
	
	/**
	 * 自增评论数
	 * @param id
	 */
	@CacheEvict(key = "'post_' + #id")
	void identityComments(String id);

	/**
	 * 喜欢文章
	 * @param userId
	 * @param postId
	 */
	@CacheEvict(key = "'post_' + #postId")
	void favor(String userId, String postId);

	/**
	 * 取消喜欢文章
	 * @param userId
	 * @param postId
	 */
	@CacheEvict(key = "'post_' + #postId")
	void unFavor(String userId, String postId);
}
