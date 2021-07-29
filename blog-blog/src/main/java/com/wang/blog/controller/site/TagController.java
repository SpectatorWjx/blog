package com.wang.blog.controller.site;

import com.wang.blog.service.TagService;
import com.wang.blog.vo.PostTagVO;
import com.wang.blog.vo.TagVO;
import com.wang.blog.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标签
 * @author wjx
 * @date 2019/08/13
 */
@Controller
public class TagController extends BaseController {
    @Autowired
    private TagService tagService;

    @RequestMapping("/tags")
    public String index(ModelMap model) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "updated"));
        Page<TagVO> page = tagService.pagingQueryTags(pageable);
        model.put("results", page);
        return view(Views.TAG_INDEX);
    }

    @RequestMapping("/tag/{name}")
    public String tag(@PathVariable String name, ModelMap model) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "weight"));
        Page<PostTagVO> page = tagService.pagingQueryPosts(pageable, name);
        model.put("results", page);

        model.put("name", name);
        return view(Views.TAG_VIEW);
    }

}
