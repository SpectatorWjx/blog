package com.wang.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
public class TagVO implements Serializable {
    private static final long serialVersionUID = -7787865229252467418L;

    private String id;

    private String name;

    private String thumbnail;

    private String description;

    private String latestPostId;

    private Date updated;

    private Integer posts;

    private PostVO post;
}
