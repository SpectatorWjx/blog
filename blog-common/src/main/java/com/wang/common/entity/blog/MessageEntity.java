package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 通知
 * @author wjx on 2015/8/31.
 */
@Data
@Entity
@Table(name = "blog_message")
@TableIdPrefix("MESAGE")
@org.hibernate.annotations.Table(appliesTo = "blog_message",comment="消息类")
public class MessageEntity extends BaseEntity {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "from_id")
    private String fromId;

    /**
     * 事件
     */
    private Integer event;

    /**
     * 关联文章ID
     */
    @Column(name = "post_id")
    private String postId;

    /**
     *  阅读状态
     */
    private Integer status = 0;
}
