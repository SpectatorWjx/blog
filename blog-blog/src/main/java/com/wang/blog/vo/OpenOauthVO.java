package com.wang.blog.vo;

import lombok.Data;

/**
 * 第三方开发授权登陆
 * @author wjx
 */
@Data
public class OpenOauthVO {

    private String id;

    /**
     * 系统中的用户ID
     */
    private String userId;

    /**
     * 认证类型：QQ、新浪等
     */
    private Integer oauthType;

    /**
     * 对应第三方用户ID, 即openid
     */
    private String oauthUserId;

    /**
     * 访问令牌
     */
    private String accessToken;

    private String expireIn;

    private String refreshToken;

    private String username;

    private String nickname;

    private String email;

    private String avatar;
}
