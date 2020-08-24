package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 评论
 *
 * @author wjx
 */
@Data
@Entity
@Table(name = "blog_comment", indexes = {
        @Index(name = "IK_POST_ID", columnList = "post_id")
})
@TableIdPrefix("COMENT")
@org.hibernate.annotations.Table(appliesTo = "blog_comment",comment="评论类")
public class CommentEntity extends BaseEntity {

    /**
     * 父评论ID
     */
    private String pid;

    /**
     * 所属内容ID
     */
    @Column(name = "post_id")
    private String postId;

    /**
     * 评论内容
     */
    @Column(name = "content")
    private String content;

    @Column(name = "author_id")
    private String authorId;

    private Integer status = 0;
}
