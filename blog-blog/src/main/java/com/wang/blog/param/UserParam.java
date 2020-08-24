package com.wang.blog.param;

import lombok.Data;

/***
 * @classname UserParam
 * @description
 * @author wjx
 * @date 2020/6/15 17:05
 */
@Data
public class UserParam {
    private String username;

    private String password;

    private String openId;


    /**
     * 头像
     */
    private String avatar;

    private String name;

    private String state;

    private String email;

    private Integer posts;

    private Integer comments;
}
