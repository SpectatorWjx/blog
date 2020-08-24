package com.wang.blog.service;

import com.wang.blog.vo.OpenOauthVO;
import com.wang.blog.vo.UserVO;

/**
 * @author wjx on 2015/8/12.
 */
public interface OpenOauthService {
    //通过 oauth_token 查询 user
    UserVO getUserByOauthToken(String oauth_token);

    OpenOauthVO getOauthByToken(String oauth_token);
    
    OpenOauthVO getOauthByOauthUserId(String oauthUserId);

    OpenOauthVO getOauthByUid(String userId);

    boolean checkIsOriginalPassword(String userId);

    void saveOauthToken(OpenOauthVO oauth);

}
