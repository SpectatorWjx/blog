package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : wjx
 */
@Data
@Entity
@Table(name = "blog_post_tag", indexes = {
        @Index(name = "IK_TAG_ID", columnList = "tag_id")
})
@TableIdPrefix("POSTTG")
@org.hibernate.annotations.Table(appliesTo = "blog_post_tag",comment="文章标签类")
public class PostTagEntity implements Serializable {
    private static final long serialVersionUID = 9192186139010913437L;

    @Id
    @Column(name = "id",length = 50,nullable = false, columnDefinition="varchar(50) COMMENT '主键Id'")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "base-id")
    @GenericGenerator(name = "base-id", strategy = "com.wang.common.common.base.IdGenerator")
    private String id;

    @Column(name = "post_id")
    private String postId;

    @Column(name = "tag_id")
    private String tagId;

    private Long weight;
}
