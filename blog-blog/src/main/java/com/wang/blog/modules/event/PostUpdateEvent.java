package com.wang.blog.modules.event;

import org.springframework.context.ApplicationEvent;

/**
 *   文章发布事件
 * //合并文章事件, 下层多个订阅者
 * - 推送Feed给粉丝
 * - 文章发布者文章数统计
 * - 推送通知
 *
 * created by wjx at 2018/05/30
 */
public class PostUpdateEvent extends ApplicationEvent {
    public final static int ACTION_PUBLISH = 1;
    public final static int ACTION_DELETE = 2;

    private String postId;
    private String userId;
    private int action = ACTION_PUBLISH;

    public PostUpdateEvent(Object source) {
        super(source);
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
