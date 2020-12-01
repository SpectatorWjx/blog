package com.wang.blog.service;

import com.wang.blog.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author wjx
 * @date 2019/1/18
 */
public interface PostSearchService {
    /**
     * 根据关键字搜索
     * @param pageable 分页
     * @param term 关键字
     * @return
     */
    Page<PostVO> search(Pageable pageable, String term);
}
