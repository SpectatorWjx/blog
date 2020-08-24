package com.wang.common.entity.user;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 第三方开发授权登陆
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
@Entity
@Table(name = "shiro_user_oauth")
@TableIdPrefix("USEROA")
@org.hibernate.annotations.Table(appliesTo = "shiro_user_oauth",comment="oauth类")
public class UserOauthEntity extends BaseEntity {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "oauth_type")
    private Integer oauthType;

    @Column(name = "oauth_user_id", length = 128)
    private String oauthUserId;

    @Column(name = "oauth_code", length = 128)
    private String oauthCode;

    @Column(name = "access_token", length = 128)
    private String accessToken;

    @Column(name = "expire_in", length = 128)
    private String expireIn;

    @Column(name = "refresh_token", length = 128)
    private String refreshToken;
}
