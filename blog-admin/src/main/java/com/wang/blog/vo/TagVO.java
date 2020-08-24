package com.wang.blog.vo;

import com.wang.common.entity.blog.TagEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author : wjx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagVO extends TagEntity implements Serializable {
    private static final long serialVersionUID = -7787865229252467418L;

    private PostVO post;
}
