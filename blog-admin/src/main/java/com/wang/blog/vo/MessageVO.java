package com.wang.blog.vo;

import com.wang.common.entity.blog.MessageEntity;

/**
 * @author wjx on 2015/8/31.
 */
public class MessageVO extends MessageEntity {
    private UserVO from;
    private PostVO post;

    public UserVO getFrom() {
        return from;
    }

    public void setFrom(UserVO from) {
        this.from = from;
    }

    public PostVO getPost() {
        return post;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
