package com.wang.blog.controller.site;

/**
 * 主题页面配置
 * @author wjx
 * @date 2019/08/13
 */
public interface Views {
    /**
     * 登陆
     */
    String LOGIN = "/auth/login";

    /**
     * 注册
     */
    String REGISTER = "/auth/register";

    /**
     * 注册待激活
     */
    String REGISTER_ACTIVE = "/auth/register_active";

    /**
     * 注册激活成功
     */
    String REGISTER_ACTIVE_SUCCESS = "/auth/register_active";

    /**
     * 三方登陆回调注册
     */
    String OAUTH_REGISTER = "/auth/oauth_register";

    /**
     * 找回密码
     */
    String FORGOT = "/auth/forgot";

    /**
     * 首页
     */
    String INDEX = "/index";

    String HOME = "/home";

    String USER_METHOD_TEMPLATE = "/user/method_%s";

    /**
     * 用户文章列表
     */
    String METHOD_POSTS = "posts";

    /**
     * 用户评论列表
     */
    String METHOD_COMMENTS = "comments";

    /**
     * 用户收藏列表
     */
    String METHOD_FAVORITES = "favorites";

    /**
     * 用户消息列表
     */
    String METHOD_MESSAGES = "messages";

    /**
     * 个人-修改头像
     */
    String SETTINGS_AVATAR = "/settings/avatar";

    /**
     * 个人-修改密码
     */
    String SETTINGS_PASSWORD = "/settings/password";

    /**
     * 个人-修改基本信息
     */
    String SETTINGS_PROFILE = "/settings/profile";

    /**
     * 个人-修改邮箱
     */
    String SETTINGS_EMAIL = "/settings/email";

    /**
     * 标签列表
     */
    String TAG_INDEX = "/tag/index";

    /**
     * 标签文章列表
     */
    String TAG_VIEW = "/tag/view";

    /**
     * 搜索
     */
    String SEARCH = "/search";

    /**
     * 标签列表
     */
    String LINK_INDEX = "/link/index";

    /**
     * 编辑文章
     */
    String POST_EDITING = "/channel/editing";

    /**
     * 文章列表
     */
    String POST_INDEX = "/channel/index";

    /**
     * 文章详情
     */
    String POST_VIEW = "/channel/view";

    String REDIRECT_USER_HOME = "redirect:/users/%s";

    String USER_HOME = "/users/%s";

    String REDIRECT_INDEX = "redirect:/index";

    String NOT_FOUND = "/404";
}
