package com.wang.blog.service.impl;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.repository.UserRepository;
import com.wang.blog.service.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

/**
 * 用户事件操作, 用于统计用户信息
 * @author wjx
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserEventServiceImpl implements UserEventService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void identityPost(String userId, boolean plus) {
        userRepository.updatePosts(userId, (plus) ? Consts.IDENTITY_STEP : Consts.DECREASE_STEP);
    }

    @Override
    public void identityComment(String userId, boolean plus) {
        userRepository.updateComments(Collections.singleton(userId), (plus) ? Consts.IDENTITY_STEP : Consts.DECREASE_STEP);
    }

    @Override
    public void identityComment(Set<String> userIds, boolean plus) {
        userRepository.updateComments(userIds, (plus) ? Consts.IDENTITY_STEP : Consts.DECREASE_STEP);
    }

}
