package com.wang.blog.repository;

import com.wang.common.entity.blog.PostEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author wjx
 */
public interface PostRepository extends BaseJpa<PostEntity> {
    /**
     * 查询指定用户
     *
     * @param pageable
     * @param authorId
     * @return
     */
    Page<PostEntity> findAllByAuthorId(Pageable pageable, String authorId);

    @Query("select coalesce(max(weight), 0) from PostEntity ")
    int maxWeight();

    @Modifying
    @Query("update PostEntity set views = views + :increment where id = :id")
    void updateViews(@Param("id") String id, @Param("increment") int increment);

    @Modifying
    @Query("update PostEntity set favors = favors + :increment where id = :id")
    void updateFavors(@Param("id") String id, @Param("increment") int increment);

    @Modifying
    @Query("update PostEntity set comments = comments + :increment where id = :id")
    void updateComments(@Param("id") String id, @Param("increment") int increment);

}
