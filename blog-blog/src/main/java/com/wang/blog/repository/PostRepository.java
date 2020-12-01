package com.wang.blog.repository;

import com.wang.common.entity.blog.PostEntity;
import com.wang.common.repository.BaseJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wjx
 * @date 2019/08/13
 */
public interface PostRepository extends BaseJpa<PostEntity> {


    /**
     * 查询文章
     * @param channelId
     * @param pageable
     * @return
     */
    @Query(" select new map( " +
            " c.name as channelName, c.id as channelId, " +
            " p.id as id, p.createTime as createTime, p.title as title, p.summary as summary, p.thumbnail as thumbnail," +
            " p.authorId as authorId, p.favors as favors, p.comments as comments, p.views as views, p.featured as featured, p.weight as weight," +
            " u.id as userId, u.name as userName, u.avatar as avatar" +
            ") " +
            " from PostEntity p " +
            " inner join ChannelEntity c on c.id = p.channelId and c.status = 0 " +
            " inner join UserEntity u on u.id = p.authorId and u.status = 0 " +
            " where p.status = 0 " +
            " and (?1 is null or c.id = ?1) ")
    Page<Map<String, Object>> findPostPageByChannel(String channelId, Pageable pageable);

    /**
     * 查询指定用户
     * @param pageable
     * @param authorId
     * @return
     */
    Page<PostEntity> findAllByAuthorId(Pageable pageable, String authorId);

    /**
     * 查询指定用户
     * @param authorId
     * @return
     */
    List<PostEntity> findAllByAuthorId(String authorId, Sort sort);

    /**
     * 更新阅读数
     * @param id
     * @param increment
     */
    @Modifying
    @Query("update PostEntity set views = views + :increment where id = :id")
    void updateViews(@Param("id") String id, @Param("increment") int increment);

    /**
     * 更新收藏数
     * @param id
     * @param increment
     */
    @Modifying
    @Query("update PostEntity set favors = favors + :increment where id = :id")
    void updateFavors(@Param("id") String id, @Param("increment") int increment);

    /**
     * 更新评论数
     * @param id
     * @param increment
     */
    @Modifying
    @Query("update PostEntity set comments = comments + :increment where id = :id")
    void updateComments(@Param("id") String id, @Param("increment") int increment);

}
