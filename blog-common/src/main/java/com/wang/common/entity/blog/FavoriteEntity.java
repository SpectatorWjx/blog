package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 喜欢/收藏
 * @author wjx on 2015/8/31.
 */
@Data
@Entity
@Table(name = "blog_favorite", indexes = {
        @Index(name = "IK_USER_ID", columnList = "user_id")
})
@TableIdPrefix("FAVRTE")
@org.hibernate.annotations.Table(appliesTo = "blog_favorite",comment="收藏类")
public class FavoriteEntity extends BaseEntity {

    /**
     * 所属用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 内容ID
     */
    @Column(name = "post_id")
    private String postId;

}
