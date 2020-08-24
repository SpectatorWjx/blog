package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : wjx
 */
@Data
@Entity
@Table(name = "blog_tag")
@TableIdPrefix("TAGTAB")
@org.hibernate.annotations.Table(appliesTo = "blog_tag",comment="标签类")
public class TagEntity extends BaseEntity {
    @Column(unique = true, nullable = false, updatable = false, length = 32)
    private String name;

    @Column(length = 128)
    private String thumbnail;

    private String description;

    private String latestPostId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updated;

    private Integer posts = 1;
}
