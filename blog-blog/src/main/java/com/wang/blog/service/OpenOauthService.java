package com.wang.blog.service;

import com.wang.blog.vo.OpenOauthVO;
import com.wang.blog.vo.UserVO;
import com.wang.common.entity.user.UserOauthEntity;
import com.wang.common.service.AbstractService;

/**
 * @author wjx on 2015/8/12.
 */
public interface OpenOauthService extends AbstractService<UserOauthEntity> {

    /**
     * 根据用户授权Id查询授权信息
     * @param oauthUserId
     * @return
     */
    OpenOauthVO getOauthByOauthUserId(String oauthUserId);

    /**
     * 保存授权信息
     * @param oauth
     */
    void saveOauthToken(OpenOauthVO oauth);

    /**
     * 更新授权信息
     * @param openOauth
     */
    void updateAuthToken(OpenOauthVO openOauth);
}
