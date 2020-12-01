package com.wang.blog.service.impl;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.vo.MessageVO;
import com.wang.blog.vo.PostVO;
import com.wang.blog.vo.UserVO;
import com.wang.blog.repository.MessageRepository;
import com.wang.blog.service.MessageService;
import com.wang.blog.service.PostService;
import com.wang.blog.service.UserService;
import com.wang.common.common.utils.StringUtil;
import com.wang.common.entity.blog.MessageEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wjx
 * @date 2019/08/13
 */
@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public Page<MessageVO> pagingByUserId(Pageable pageable, String userId) {
        Page<MessageEntity> page = messageRepository.findAllByUserId(pageable, userId);
        List<MessageVO> rets = new ArrayList<>();

        Set<String> postIds = new HashSet<>();
        Set<String> fromUserIds = new HashSet<>();

        // 筛选
        page.getContent().forEach(po -> {
            MessageVO no = BeanMapUtils.copy(po);

            rets.add(no);

            if (!StringUtil.isEmpty(no.getPostId())) {
                postIds.add(no.getPostId());
            }
            if (!StringUtil.isEmpty(no.getFromId())) {
                fromUserIds.add(no.getFromId());
            }

        });

        // 加载
        Map<String, PostVO> posts = postService.findMapByIds(postIds);
        Map<String, UserVO> fromUsers = userService.findMapByIds(fromUserIds);

        rets.forEach(n -> {
            if (!StringUtil.isEmpty(n.getPostId())) {
                n.setPost(posts.get(n.getPostId()));
            }
            if (!StringUtil.isEmpty(n.getFromId())) {
                n.setFrom(fromUsers.get(n.getFromId()));
            }
        });

        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void send(MessageVO message) {
        if (message == null || StringUtil.isEmpty(message.getUserId()) || StringUtil.isEmpty(message.getFromId())) {
            return;
        }

        MessageEntity po = new MessageEntity();
        BeanUtils.copyProperties(message, po);

        messageRepository.save(po);
    }

    @Override
    public int unread4Me(String userId) {
        return messageRepository.countByUserIdAndStatus(userId, Consts.UNREAD);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void readed4Me(String userId) {
        messageRepository.updateReadedByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteByPostId(String postId) {
        return messageRepository.deleteByPostId(postId);
    }
}
