package com.wang.blog.service.link;

import com.wang.blog.base.page.BasePage;
import com.wang.blog.vo.LinkVo;
import com.wang.common.entity.other.LinkEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface LinkService {
    /**
     * 查询友链的分页数据
     *
     * @param pageable
     * @return
     */
    BasePage<LinkVo> getBlogLinkPage(Pageable pageable);

    Long getTotalLinks();

    void saveLink(LinkEntity link);

    LinkEntity selectById(String id);

    void updateLink(LinkEntity tempLink);

    void deleteBatch(List<String> ids);

    void delete(String id);

    /**
     * 返回友链页面所需的所有数据
     *
     * @return
     */
    Map<Integer, List<LinkVo>> getLinksForLinkPage();

    /**
     * 查询所有个数
     * @return
     */
    Long count();
}
