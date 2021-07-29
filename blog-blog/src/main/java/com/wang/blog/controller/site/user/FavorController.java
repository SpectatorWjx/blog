package com.wang.blog.controller.site.user;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.modules.event.MessageEvent;
import com.wang.blog.service.PostService;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.controller.BaseController;
import com.wang.common.common.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wjx
 * @date 2019/08/13
 */
@RestController
@RequestMapping("/user")
public class FavorController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 收藏文章
     * @param id
     * @return
     */
    @RequestMapping("/favor")
    public Result favor(String id) {
        Result data = Result.exception(500,"操作失败");
        if (id != null) {
            try {
                AccountProfile up = getProfile();
                postService.favor(up.getId(), id);
                sendMessage(up.getId(), id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.exception(500, e.getMessage());
            }
        }
        return data;
    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @RequestMapping("/unfavor")
    public Result unfavor(String id) {
        Result data = Result.exception(500, "操作失败");
        if (id != null) {
            try {
                AccountProfile up = getProfile();
                postService.unFavor(up.getId(), id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.exception(500, e.getMessage());
            }
        }
        return data;
    }

    /**
     * 发送通知
     * @param userId
     * @param postId
     */
    private void sendMessage(String userId, String postId) {
        MessageEvent event = new MessageEvent("MessageEvent" + System.currentTimeMillis());
        event.setFromUserId(userId);
        event.setEvent(Consts.MESSAGE_EVENT_FAVOR_POST);
        // 此处不知道文章作者, 让通知事件系统补全
        event.setPostId(postId);
        applicationContext.publishEvent(event);
    }
}
