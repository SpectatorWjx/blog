package com.wang.blog.service;

import com.wang.blog.vo.FavoriteVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收藏记录
 * @author wjx
 */
public interface FavoriteService {
    /**
     * 查询用户收藏记录
     * @param pageable
     * @param userId
     * @return
     */
    Page<FavoriteVO> pagingByUserId(Pageable pageable, String userId);

    void add(String userId, String postId);
    void delete(String userId, String postId);
    void deleteByPostId(String postId);
}
