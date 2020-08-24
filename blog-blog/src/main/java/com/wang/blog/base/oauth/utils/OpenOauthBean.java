package com.wang.blog.base.oauth.utils;


import lombok.Data;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
public class OpenOauthBean {
    private String id;

    private String userId;

    private Integer oauthType;

    private String oauthUserId;

    private String accessToken;

    private String expireIn;

    private String refreshToken;

    private String username;

    private String nickname;

    private String email;

    private String avatar;
}
