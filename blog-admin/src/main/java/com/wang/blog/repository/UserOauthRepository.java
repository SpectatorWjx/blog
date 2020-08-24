package com.wang.blog.repository;

import com.wang.common.entity.user.UserOauthEntity;
import com.wang.common.repository.BaseJpa;

/**
 * 第三方开发授权登录
 *
 * @author wjx on 2015/8/12.
 */
public interface UserOauthRepository extends BaseJpa<UserOauthEntity> {
    UserOauthEntity findByAccessToken(String accessToken);
    UserOauthEntity findByOauthUserId(String oauthUserId);
    UserOauthEntity findByUserId(String userId);
}
