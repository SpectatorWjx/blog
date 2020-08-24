package com.wang.blog.service;

import com.wang.blog.vo.MessageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author wjx
 */
public interface MessageService {

    /**
     * 查询用户分页
     * @param pageable
     * @param userId
     * @return
     */
    Page<MessageVO> pagingByUserId(Pageable pageable, String userId);

    /**
     * 发送通知
     * @param message
     */
    void send(MessageVO message);

    /**
     * 未读消息数量
     * @param userId
     * @return
     */
    int unread4Me(String userId);

    /**
     * 标记为已读
     * @param userId
     */
    void readed4Me(String userId);

    /**
     * 根据文章ID清理消息
     * @param postId
     * @return
     */
    int deleteByPostId(String postId);
}
