package com.wang.blog.service.impl;

import com.wang.blog.vo.OpenOauthVO;
import com.wang.blog.repository.UserOauthRepository;
import com.wang.blog.service.OpenOauthService;
import com.wang.common.common.utils.BeanCopyUtils;
import com.wang.common.entity.user.UserOauthEntity;
import com.wang.common.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 第三方登陆授权管理
 * @author wjx
 * @date 2015/8/12
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OpenOauthServiceImpl extends AbstractServiceImpl<UserOauthEntity, UserOauthRepository> implements OpenOauthService {

    @Override
    public void saveOauthToken(OpenOauthVO oauth) {
        UserOauthEntity po = new UserOauthEntity();
        BeanCopyUtils.copyProperties(oauth, po);
        save(po);
    }

    @Override
    public void updateAuthToken(OpenOauthVO openOauth) {
        UserOauthEntity example = new UserOauthEntity();
        example.setOauthUserId(openOauth.getOauthUserId());
        UserOauthEntity entity = findOne(example);
        if (Optional.ofNullable(entity).isPresent()) {
            BeanCopyUtils.copyPropertiesIgnoreNull(openOauth, entity);
            save(entity);
        }
    }

    @Override
	public OpenOauthVO getOauthByOauthUserId(String oauthUserId) {
        UserOauthEntity example = new UserOauthEntity();
        example.setOauthUserId(oauthUserId);
		UserOauthEntity entity = findOne(example);
        if (Optional.ofNullable(entity).isPresent()) {
            OpenOauthVO vo = new OpenOauthVO();
            BeanCopyUtils.copyProperties(entity, vo);
            return vo;
        }
        return null;
	}

}
