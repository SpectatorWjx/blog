package com.wang.blog.modules.template.directive;

import com.wang.blog.vo.TagVO;
import com.wang.blog.service.CommentService;
import com.wang.blog.service.PostService;
import com.wang.blog.service.TagService;
import com.wang.blog.modules.template.DirectiveHandler;
import com.wang.blog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * method: [latest_posts, hottest_posts, latest_comments]
 * on 2019/3/12
 * @author wjx
 */
@Component
public class SidebarDirective extends TemplateDirective {
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @Override
    public String getName() {
        return "sidebar";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        int size = handler.getInteger("size", 5);
        String method = handler.getString("method", "post_latests");
        switch (method) {
            case "latest_posts":
                handler.put(RESULTS, postService.findLatestPosts(size));
                break;
            case "hottest_posts":
                handler.put(RESULTS, postService.findHottestPosts(size));
                break;
            case "hottest_tags":
                Page<TagVO> page = tagService.pagingQueryTags(PageRequest.of(0,10));
                List<TagVO> list = page.getContent();
                handler.put(RESULTS, list);
                break;
            default:
                    break;
        }
        handler.render();
    }
}
