package com.wang.blog.service;

import com.wang.blog.base.lang.Consts;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Set;

/**
 * @author wjx
 * @date 2015/8/6
 */
public interface UserEventService {
    /**
     * 自增发布文章数
     * @param userId
     * @param plus
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityPost(String userId, boolean plus);

    /**
     * 自增评论数
     * @param userId
     * @param plus
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityComment(String userId, boolean plus);

    /**
     * 批量自动评论数
     * @param userIds
     * @param plus
     */
    @CacheEvict(value = {Consts.CACHE_USER, Consts.CACHE_POST}, allEntries = true)
    void identityComment(Set<String> userIds, boolean plus);
}
