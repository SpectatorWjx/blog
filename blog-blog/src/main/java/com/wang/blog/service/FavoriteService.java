package com.wang.blog.service;

import com.wang.blog.vo.FavoriteVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收藏记录
 * @author wjx
 * @date 2019/08/13
 */
public interface FavoriteService {
    /**
     * 查询用户收藏记录
     * @param pageable
     * @param userId
     * @return
     */
    Page<FavoriteVO> pagingByUserId(Pageable pageable, String userId);

    /**
     * 添加收藏
     * @param userId
     * @param postId
     */
    void add(String userId, String postId);

    /**
     * 删除
     * @param userId
     * @param postId
     */
    void delete(String userId, String postId);

    /**
     * 根据文章删除
     * @param postId
     */
    void deleteByPostId(String postId);
}
