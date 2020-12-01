package com.wang.blog.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author wjx
 * @date 2019/12/10
 */
@Data
public class MessageVO{

    private String id;

    private Date createTime;

    private String userId;

    private String fromId;

    /**
     * 事件
     */
    private Integer event;

    /**
     * 关联文章ID
     */
    private String postId;

    private Integer status;

    private UserVO from;

    private PostVO post;
}
