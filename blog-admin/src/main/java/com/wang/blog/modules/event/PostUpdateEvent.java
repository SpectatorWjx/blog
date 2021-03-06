package com.wang.blog.modules.event;

import lombok.Data;
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
@Data
public class PostUpdateEvent extends ApplicationEvent {
    public final static int ACTION_PUBLISH = 1;
    public final static int ACTION_DELETE = 2;

    private String postId;
    private String userId;
    private int action = ACTION_PUBLISH;

    public PostUpdateEvent(Object source) {
        super(source);
    }
}
