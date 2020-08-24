package com.wang.blog.service.impl;

import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.vo.FavoriteVO;
import com.wang.blog.vo.PostVO;
import com.wang.blog.repository.FavoriteRepository;
import com.wang.blog.service.FavoriteService;
import com.wang.blog.service.PostService;
import com.wang.common.entity.blog.FavoriteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author wjx on 2015/8/31.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private PostService postService;

    @Override
    public Page<FavoriteVO> pagingByUserId(Pageable pageable, String userId) {
        Page<FavoriteEntity> page = favoriteRepository.findAllByUserId(pageable, userId);

        List<FavoriteVO> rets = new ArrayList<>();
        Set<String> postIds = new HashSet<>();
        for (FavoriteEntity po : page.getContent()) {
            rets.add(BeanMapUtils.copy(po));
            postIds.add(po.getPostId());
        }

        if (postIds.size() > 0) {
            Map<String, PostVO> posts = postService.findMapByIds(postIds);

            for (FavoriteVO t : rets) {
                PostVO p = posts.get(t.getPostId());
                t.setPost(p);
            }
        }
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void add(String userId, String postId) {
        FavoriteEntity po = favoriteRepository.findByUserIdAndPostId(userId, postId);

        Assert.isNull(po, "您已经收藏过此文章");

        // 如果没有喜欢过, 则添加记录
        po = new FavoriteEntity();
        po.setUserId(userId);
        po.setPostId(postId);

        favoriteRepository.save(po);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(String userId, String postId) {
        FavoriteEntity po = favoriteRepository.findByUserIdAndPostId(userId, postId);
        Assert.notNull(po, "还没有喜欢过此文章");
        favoriteRepository.delete(po);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteByPostId(String postId) {
        int rows = favoriteRepository.deleteByPostId(postId);
        log.info("favoriteRepository delete {}", rows);
    }

}
