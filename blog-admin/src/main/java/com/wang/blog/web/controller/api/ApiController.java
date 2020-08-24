package com.wang.blog.web.controller.api;

import com.wang.blog.base.lang.Consts;
import com.wang.common.common.base.Result;
import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.vo.CommentVO;
import com.wang.blog.vo.PostVO;
import com.wang.blog.service.CommentService;
import com.wang.blog.service.PostService;
import com.wang.blog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 侧边栏数据加载
 *
 * @author wjx
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/login")
    public Result login(String username, String password) {
        return executeLogin(username, password, false);
    }

    @RequestMapping("/posts")
    public Page<PostVO> posts(HttpServletRequest request) {
        String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
        String channelId = ServletRequestUtils.getStringParameter(request, "channelId", "");
        return postService.paging(wrapPageable(Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order))), channelId, null);
    }

    @RequestMapping("/latest_comments")
    public List<CommentVO> getLastComment(){
        return commentService.findLatestComments(2);
    }
}
