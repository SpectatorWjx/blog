package com.wang.blog.vo;

import com.wang.common.entity.blog.FavoriteEntity;

/**
 * @author wjx on 2015/8/31.
 */
public class FavoriteVO extends FavoriteEntity {
    private PostVO post;

    public PostVO getPost() {
        return post;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
