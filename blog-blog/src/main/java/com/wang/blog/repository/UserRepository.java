package com.wang.blog.repository;

import com.wang.common.entity.user.UserEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * @author wjx
 */
public interface UserRepository extends BaseJpa<UserEntity> {
    UserEntity findByUsername(String username);

    UserEntity findByIdAndState(String id, String state);

    UserEntity findByEmail(String email);

    @Modifying
    @Query("update UserEntity set posts = posts + :increment where id = :id")
    int updatePosts(@Param("id") String id, @Param("increment") int increment);

    @Modifying
    @Query("update UserEntity set comments = comments + :increment where id in (:ids)")
    int updateComments(@Param("ids") Collection<String> ids, @Param("increment") int increment);

    @Modifying
    @Query("update UserEntity set status = :status where id = :id")
    int updateStatus(@Param("id") String id, @Param("status") int status);

}
