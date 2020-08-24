package com.wang.blog.service.impl;

import com.wang.blog.service.PostSearchService;
import com.wang.common.entity.blog.PostEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * @author : wjx
 * @version : 1.0
 * @date : 2019/1/18
 */
@Slf4j
@Service
public class PostSearchServiceImpl implements PostSearchService {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void resetIndexes() {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer(PostEntity.class).start();
    }
}
