package com.wang.blog.modules.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wjx on 2015/8/31.
 */
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

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(String commentParentId) {
        this.commentParentId = commentParentId;
    }
}
