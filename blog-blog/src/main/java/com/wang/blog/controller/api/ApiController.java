package com.wang.blog.controller.api;

import com.wang.blog.base.config.ContextStartup;
import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.shiro.LoginType;
import com.wang.blog.base.utils.BeanMapUtils;
import com.wang.blog.service.PostService;
import com.wang.blog.vo.PostVO;
import com.wang.blog.controller.BaseController;
import com.wang.common.common.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 侧边栏数据加载
 *
 * @author wjx
 * @date 2019/08/13
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ContextStartup contextStartup;

    @PostMapping(value = "/login")
    public Result login(String username, String password) {
        return executeLogin(username, password, false, LoginType.USER.toString());
    }

    @RequestMapping("/posts")
    public Page<PostVO> posts(HttpServletRequest request) {
        String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
        String channelId = ServletRequestUtils.getStringParameter(request, "channelId", null);
        return postService.paging(wrapPageable(Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order))), channelId);
    }

    @RequestMapping("/reset/cache")
    @ResponseBody
    @CacheEvict(value = Consts.CACHE_POST,allEntries = true)
    public Result resetCache(){
        contextStartup.reloadOptions(true);
        contextStartup.resetChannels();
        return Result.success();
    }
}
