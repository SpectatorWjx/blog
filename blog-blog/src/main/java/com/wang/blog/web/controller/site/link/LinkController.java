package com.wang.blog.web.controller.site.link;

import com.wang.blog.service.link.LinkService;
import com.wang.blog.vo.LinkVo;
import com.wang.blog.web.controller.BaseController;
import com.wang.blog.web.controller.site.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/***
 * @classname LinkController
 * @description
 * @author wjx
 * @date 2020/3/5
 */
@Controller
public class LinkController   extends BaseController {

    @Autowired
    LinkService linkService;

    /**
     * 友情链接页
     *
     * @return
     */
    @RequestMapping("/link")
    public String link(ModelMap model) {
        Map<Integer, List<LinkVo>> linkMap = linkService.getLinksForLinkPage();
        if (linkMap != null) {
            //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
            if (linkMap.containsKey(0)) {
                model.put("favoriteLinks", linkMap.get(0));
            }
            if (linkMap.containsKey(1)) {
                model.put("recommendLinks", linkMap.get(1));
            }
            if (linkMap.containsKey(2)) {
                model.put("personalLinks", linkMap.get(2));
            }
        }
        return view(Views.LINK_INDEX);
    }
}
