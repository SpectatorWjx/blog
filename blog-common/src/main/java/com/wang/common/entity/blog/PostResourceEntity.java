package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 文章图片
 *
 * @author saxing 2019/4/3 bilibili-22:39
 */
@Data
@Entity
@Table(name = "blog_post_resource", indexes = {
        @Index(name = "IK_POST_ID", columnList = "post_id")
})
@TableIdPrefix("POSTRE")
@org.hibernate.annotations.Table(appliesTo = "blog_post_resource",comment="文章资源类")
public class PostResourceEntity extends BaseEntity {

    @Column(name = "post_id")
    private String postId;

    private String resourceId;

    private String path;

    @Column(name = "sort", columnDefinition = "int(11) NOT NULL DEFAULT '0'")
    private Integer sort = 0;

}
