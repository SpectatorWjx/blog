/**
 *
 */package com.wang.blog.web.controller.site.comment;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.modules.event.MessageEvent;
import com.wang.blog.service.CommentService;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.CommentVO;
import com.wang.blog.web.controller.BaseController;
import com.wang.common.common.base.Result;
import com.wang.common.common.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wjx
 */
@RestController
@RequestMapping("/comment")
@ConditionalOnProperty(name = "site.controls.comment", havingValue = "true", matchIfMissing = true)
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/list/{toId}")
    public Page<CommentVO> viewComment(@PathVariable String toId) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "createTime"));
        return commentService.pagingByPostId(pageable, toId);
    }

    @RequestMapping("/submit")
    public Result post(String toId, String text, HttpServletRequest request) {
        if (!isAuthenticated()) {
            return Result.exception(500,"请先登陆在进行操作");
        }

        String pid = ServletRequestUtils.getStringParameter(request, "pid", null);

        if (StringUtils.isBlank(text)) {
            return Result.exception(500, "操作失败");
        }

        AccountProfile profile = getProfile();

        CommentVO c = new CommentVO();
        c.setPostId(toId);
        c.setContent(HtmlUtils.htmlEscape(text));
        c.setAuthorId(profile.getId());

        c.setPid(pid);

        commentService.post(c);

        sendMessage(profile.getId(), toId, pid);

        return Result.success("发表成功");
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam(name = "id") String id) {
        Result data;
        try {
            commentService.delete(id, getProfile().getId());
            data = Result.success();
        } catch (Exception e) {
            data = Result.exception(500, e.getMessage());
        }
        return data;
    }

    /**
     * 发送通知
     *
     * @param userId
     * @param postId
     */
    private void sendMessage(String userId, String postId, String pid) {
        MessageEvent event = new MessageEvent("MessageEvent");
        event.setFromUserId(userId);

        if (!StringUtil.isEmpty(pid)) {
            event.setEvent(Consts.MESSAGE_EVENT_COMMENT_REPLY);
            event.setCommentParentId(pid);
        } else {
            event.setEvent(Consts.MESSAGE_EVENT_COMMENT);
        }
        // 此处不知道文章作者, 让通知事件系统补全
        event.setPostId(postId);
        applicationContext.publishEvent(event);
    }
}