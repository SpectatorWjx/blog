package com.wang.blog.vo;

import com.wang.common.entity.blog.PostTagEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author : wjx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostTagVO extends PostTagEntity implements Serializable {
    private static final long serialVersionUID = 73354108587481371L;

    private PostVO post;
}
