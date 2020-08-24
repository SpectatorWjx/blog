package com.wang.blog.service;

/**
 * @author : wjx
 */
public interface TagService {
    void batchUpdate(String names, String latestPostId);
    void deteleMappingByPostId(String postId);
}
