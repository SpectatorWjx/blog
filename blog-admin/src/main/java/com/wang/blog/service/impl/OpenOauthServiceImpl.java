package com.wang.blog.service.impl;

import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.base.utils.MD5;
import com.wang.blog.vo.OpenOauthVO;
import com.wang.blog.vo.UserVO;
import com.wang.blog.repository.UserOauthRepository;
import com.wang.blog.repository.UserRepository;
import com.wang.blog.service.OpenOauthService;
import com.wang.common.entity.user.UserEntity;
import com.wang.common.entity.user.UserOauthEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 第三方登录授权管理
 * @author wjx on 2015/8/12.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpenOauthServiceImpl implements OpenOauthService {
    @Autowired
    private UserOauthRepository userOauthRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserVO getUserByOauthToken(String oauth_token) {
        UserOauthEntity thirdToken = userOauthRepository.findByAccessToken(oauth_token);
        Optional<UserEntity> po = userRepository.findById(thirdToken.getId());
        return BeanMapUtils.copy(po.get());
    }

    @Override
    public OpenOauthVO getOauthByToken(String oauth_token) {
        UserOauthEntity po = userOauthRepository.findByAccessToken(oauth_token);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    public OpenOauthVO getOauthByUid(String userId) {
        UserOauthEntity po = userOauthRepository.findByUserId(userId);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    public boolean checkIsOriginalPassword(String userId) {
        UserOauthEntity po = userOauthRepository.findByUserId(userId);
        if (po != null) {
            Optional<UserEntity> optional = userRepository.findById(userId);

            String pwd = MD5.md5(po.getAccessToken());
            // 判断用户密码 和 登录状态
            if (optional.isPresent() && pwd.equals(optional.get().getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveOauthToken(OpenOauthVO oauth) {
        UserOauthEntity po = new UserOauthEntity();
        BeanUtils.copyProperties(oauth, po);
        userOauthRepository.save(po);
    }

	@Override
	public OpenOauthVO getOauthByOauthUserId(String oauthUserId) {
		UserOauthEntity po = userOauthRepository.findByOauthUserId(oauthUserId);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
	}

}
