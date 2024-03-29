package com.wang.blog.modules.hook.interceptor;

import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.PostVO;
import com.wang.blog.service.CommentService;
import com.wang.blog.controller.site.ChannelController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wjx
 * @date 2018/01/15
 */
@Component
public class HidenContentPugin extends InterceptorHookSupport {
    @Autowired
    private CommentService commentService;

    private static final String SHOW = "<blockquote>隐藏内容，请回复后查看</blockquote>";

    @Override
    public String[] getInterceptor() {
        //说明要拦截的controller
        return new String[]{ChannelController.class.getName()};
    }

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception {
        PostVO ret = (PostVO) modelAndView.getModelMap().get("view");
        Object editing = modelAndView.getModel().get("editing");
        if (null == editing && ret != null) {
            PostVO post = new PostVO();
            BeanUtils.copyProperties(ret, post);
            if (check(ret.getId(), ret.getAuthor().getId())) {
                String c = post.getContent().replaceAll("\\[hide\\]([\\s\\S]*)\\[\\/hide\\]", SHOW);
                post.setContent(c);
            } else {
                String c = post.getContent().replaceAll("\\[hide\\]([\\s\\S]*)\\[\\/hide\\]", "$1");
                post.setContent(c);
            }
            modelAndView.getModelMap().put("view", post);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }

    private boolean check(String id, String userId) {
        Subject subject = SecurityUtils.getSubject();
        AccountProfile profile = (AccountProfile) subject.getPrincipal();
        if (profile != null) {
            if (profile.getId().equals(userId)) {
                return false;
            }
            return commentService.countByAuthorIdAndPostId(profile.getId(), id) <= 0;
        }
        return true;
    }
}
