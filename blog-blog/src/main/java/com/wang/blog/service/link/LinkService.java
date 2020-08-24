package com.wang.blog.service.link;


import com.wang.blog.vo.LinkVo;

import java.util.List;
import java.util.Map;

public interface LinkService {
    /**
     * 返回友链页面所需的所有数据
     *
     * @return
     */
    Map<Integer, List<LinkVo>> getLinksForLinkPage();
}
