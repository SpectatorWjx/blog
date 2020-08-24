package com.wang.blog.repository;

import com.wang.common.entity.blog.MessageEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author wjx
 */
public interface MessageRepository extends BaseJpa<MessageEntity> {
    Page<MessageEntity> findAllByUserId(Pageable pageable, String userId);

    /**
     * 查询我的未读消息
     *
     * @param userId
     * @return
     */
    int countByUserIdAndStatus(String userId, int status);

    /**
     * 标记我的消息为已读
     */
    @Modifying
    @Query("update MessageEntity n set n.status = 1 where n.status = 0 and n.userId = :uid")
    int updateReadedByUserId(@Param("uid") String uid);

    int deleteByPostId(String postId);
}
