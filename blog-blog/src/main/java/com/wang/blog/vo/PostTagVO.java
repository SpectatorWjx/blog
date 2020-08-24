package com.wang.blog.vo;

import com.wang.common.entity.blog.PostTagEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
public class PostTagVO implements Serializable {
    private static final long serialVersionUID = 73354108587481371L;

    private String id;

    private String postId;

    private String tagId;

    private Long weight;

    private PostVO post;
}
