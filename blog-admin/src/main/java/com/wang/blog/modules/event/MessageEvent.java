package com.wang.blog.modules.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author wjx on 2015/8/31.
 */
@Data
public class MessageEvent extends ApplicationEvent {
	private static final long serialVersionUID = -4261382494171476390L;

    /**
     * 消息生产者Id
     */
	private String fromUserId;

    /**
     * 消息消费者Id
     */
    private String toUserId;

    /**
     * 消息类型
     */
    private int event;

    /**
     * 相关文章Id
     */
    private String postId;

    /**
     * 如果是回复评论 回复的评论Id
     */
    private String commentParentId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public MessageEvent(Object source) {
        super(source);
    }
}
