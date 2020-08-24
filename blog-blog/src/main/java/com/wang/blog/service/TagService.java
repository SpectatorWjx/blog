package com.wang.blog.service;

import com.wang.blog.vo.PostTagVO;
import com.wang.blog.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author : wjx
 */
public interface TagService {
    /**
     * 分页
     * @param pageable
     * @return
     */
    Page<TagVO> pagingQueryTags(Pageable pageable);

    /**
     * 根据名称分页
     * @param pageable
     * @param tagName
     * @return
     */
    Page<PostTagVO> pagingQueryPosts(Pageable pageable, String tagName);

    /**
     * 更新
     * @param names
     * @param latestPostId
     */
    void batchUpdate(String names, String latestPostId);

    /**
     * 删除
     * @param postId
     */
    void deleteMappingByPostId(String postId);
}
