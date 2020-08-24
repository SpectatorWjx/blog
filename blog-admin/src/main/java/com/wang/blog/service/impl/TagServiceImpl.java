package com.wang.blog.service.impl;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.repository.PostTagRepository;
import com.wang.blog.repository.TagRepository;
import com.wang.blog.service.TagService;
import com.wang.common.entity.blog.PostTagEntity;
import com.wang.common.entity.blog.TagEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : wjx
 */
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostTagRepository postTagRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(String names, String latestPostId) {
        if (StringUtils.isBlank(names.trim())) {
            return;
        }

        String[] ns = names.split(Consts.SEPARATOR);
        List<String> tagNames = Arrays.asList(ns);
        List<PostTagEntity> postTags = postTagRepository.findAllByPostId(latestPostId);
        postTags.forEach(pt -> {
            Optional<TagEntity> optional = tagRepository.findById(pt.getTagId());
            if(optional.isPresent()){
                TagEntity tag = optional.get();
                if(!tagNames.contains(tag.getName())){
                    updateTagPosts(latestPostId, tag);
                    postTagRepository.delete(pt);
                }
            }
        });
        Date current = new Date();
        tagNames.forEach(n -> {
            String name = n.trim();
            if (StringUtils.isBlank(name)) {
                return;
            }

            TagEntity po = tagRepository.findByName(name);
            if (po != null) {
                PostTagEntity pt = postTagRepository.findByPostIdAndTagId(latestPostId, po.getId());
                if (null != pt) {
                    pt.setWeight(System.currentTimeMillis());
                    postTagRepository.save(pt);
                    return;
                }
                po.setPosts(po.getPosts() + 1);
                po.setUpdated(current);
            } else {
                po = new TagEntity();
                po.setName(name);
                po.setUpdated(current);
                po.setPosts(1);
            }

            po.setLatestPostId(latestPostId);
            tagRepository.save(po);

            PostTagEntity pt = new PostTagEntity();
            pt.setPostId(latestPostId);
            pt.setTagId(po.getId());
            pt.setWeight(System.currentTimeMillis());
            postTagRepository.save(pt);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deteleMappingByPostId(String postId) {
        List<PostTagEntity> postTags = postTagRepository.findAllByPostId(postId);
        List<String> tagIds = postTags.stream().map(PostTagEntity::getTagId).collect(Collectors.toList());
        tagIds.forEach(t -> {
            TagEntity tag = tagRepository.getOne(t);
            if(Optional.ofNullable(tag).isPresent()){
                updateTagPosts(postId, tag);
            }
        });
        postTagRepository.deleteByPostId(postId);
    }


    private void updateTagPosts(String postId, TagEntity tag){
        if(tag.getPosts()>1){
            tag.setPosts(tag.getPosts()-1);
            if(postId.equals(tag.getLatestPostId())){
                List<PostTagEntity> postTagList = postTagRepository.findAllByPostIdNotAndTagId(postId,tag.getId());
                tag.setLatestPostId(CollectionUtils.isEmpty(postTagList)?"":postTagList.get(0).getPostId());
            }
            tagRepository.save(tag);
        } else {
            tagRepository.delete(tag);
        }
    }
}
