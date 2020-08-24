package com.wang.blog.web.controller.site.auth;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.oauth.*;
import com.wang.blog.base.oauth.utils.OpenOauthBean;
import com.wang.blog.base.oauth.utils.TokenUtil;
import com.wang.blog.base.shiro.LoginType;
import com.wang.blog.base.utils.MD5;
import com.wang.blog.param.UserParam;
import com.wang.blog.service.OpenOauthService;
import com.wang.blog.service.UserService;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.OpenOauthCheckVO;
import com.wang.blog.vo.OpenOauthVO;
import com.wang.blog.vo.UserVO;
import com.wang.blog.web.controller.BaseController;
import com.wang.blog.web.controller.site.Views;
import com.wang.common.common.base.BaseException;
import com.wang.common.common.base.Result;
import com.wang.common.common.utils.BeanCopyUtils;
import com.wang.common.entity.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 第三方登陆回调
 *
 * @author wjx
 */
@Slf4j
@Controller
@RequestMapping("/oauth/callback")
@ConditionalOnProperty(name = "site.controls.register", havingValue = "true", matchIfMissing = true)
public class CallbackController extends BaseController {
    private static final String QQ_SESSION_STATE = "QQ_SESSION_STATE_";
    private static final String WEI_BO_SESSION_STATE = "WEI_BO_SESSION_STATE_";
    private static final String GITHUB_SESSION_STATE = "GITHUB_SESSION_STATE_";
    private static final String ALI_PAY_SESSION_STATE = "ALI_PAY_SESSION_STATE_";
    private static final String GIT_EE_SESSION_STATE = "GIT_EE_SESSION_STATE_";

    @Autowired
    private OpenOauthService openOauthService;
    @Autowired
    private UserService userService;

    /**
     * 跳转到微博进行授权
     *
     * @param request
     * @param session
     * @param response
     * @author A蛋壳  2015年9月12日 下午3:05:54
     */
    @RequestMapping("/call_weibo")
    public void callWeiBo(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String serverName = "https://" + request.getServerName();
        response.setContentType("text/html;charset=utf-8");
        try {
            APIConfig.getInstance().setOpenid_sina(siteOptions.getValue(Consts.WEIBO_CLIENT_ID));
            APIConfig.getInstance().setOpenkey_sina(siteOptions.getValue(Consts.WEIBO_CLIENT_SERCRET));
            APIConfig.getInstance().setRedirect_sina(serverName+siteOptions.getValue(Consts.WEIBO_CALLBACK));

            String state = TokenUtil.randomState();
            session.setAttribute(WEI_BO_SESSION_STATE, state);
            response.sendRedirect(OauthSina.me().getAuthorizeUrl(state));
        } catch (Exception e) {
            throw new BaseException("跳转到微博授权接口时发生异常");
        }
    }

    /**
     * 微博回调
     *
     * @param code
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/weibo")
    public String callbackWeiBo(String code, String state, HttpServletRequest request, HttpSession session, ModelMap model) {
        String sessionState = (String)session.getAttribute(WEI_BO_SESSION_STATE);
        if (StringUtils.isBlank(state) || StringUtils.isBlank(sessionState) || !state.equals(sessionState) || StringUtils.isBlank(code)) {
            throw new BaseException("缺少必要的参数");
        }
        request.getSession().removeAttribute(WEI_BO_SESSION_STATE);
        return callback(code, model, OauthSina.me());
    }

    /**
     * 跳转到QQ互联授权界面
     *
     * @param request
     * @param session
     * @param response
     * @author wjx  2015年9月12日 下午3:28:21
     */
    @RequestMapping("/call_qq")
    public void callQq(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String serverName = "https://" + request.getServerName();
        response.setContentType("text/html;charset=utf-8");
        try {
            APIConfig.getInstance().setOpenid_qq(siteOptions.getValue(Consts.QQ_APP_ID));
            APIConfig.getInstance().setOpenkey_qq(siteOptions.getValue(Consts.QQ_APP_KEY));
            APIConfig.getInstance().setRedirect_qq(serverName + siteOptions.getValue(Consts.QQ_CALLBACK));

            String state = TokenUtil.randomState();
            session.setAttribute(QQ_SESSION_STATE, state);
            response.sendRedirect(OauthQQ.me().getAuthorizeUrl(state));
        } catch (Exception e) {
            throw new BaseException("跳转到QQ授权接口时发生异常");
        }
    }

    /**
     * QQ回调
     *
     * @param code
     * @param request
     * @return
     */
    @RequestMapping("/qq")
    public String callbackQq(String code, String state, HttpServletRequest request, HttpSession session, ModelMap model) {
        String sessionState = (String)session.getAttribute(QQ_SESSION_STATE);
        if (StringUtils.isBlank(state) || StringUtils.isBlank(sessionState) || !state.equals(sessionState) || StringUtils.isBlank(code)) {
            throw new BaseException("缺少必要的参数");
        }
        request.getSession().removeAttribute(QQ_SESSION_STATE);
        return callback(code, model, OauthQQ.me());
    }

    /**
     * 跳转到github授权页面
     *
     * @param request
     * @param session
     * @param response
     */
    @RequestMapping("/call_github")
    public void callGithub(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String serverName = "https://" + request.getServerName();
        //设置github的相关
        APIConfig.getInstance().setOpenid_github(siteOptions.getValue(Consts.GITHUB_CLIENT_ID));
        APIConfig.getInstance().setOpenkey_github(siteOptions.getValue(Consts.GITHUB_SECRET_KEY));
        APIConfig.getInstance().setRedirect_github(serverName + siteOptions.getValue(Consts.GITHUB_CALLBACK));
        try {
            response.setContentType("text/html;charset=utf-8");
            String state = TokenUtil.randomState();
            session.setAttribute(GITHUB_SESSION_STATE, state);
            response.sendRedirect(OauthGithub.me().getAuthorizeUrl(state));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * github回调
     *
     * @param code
     * @param state
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/github")
    public String callback4Github(String code, String state, HttpServletRequest request, HttpSession session, ModelMap model){
        String sessionState = (String)session.getAttribute(GITHUB_SESSION_STATE);
        if (StringUtils.isBlank(state) || StringUtils.isBlank(sessionState) || !state.equals(sessionState) || StringUtils.isBlank(code)) {
            throw new BaseException("缺少必要的参数");
        }
        request.getSession().removeAttribute(GITHUB_SESSION_STATE);
        return callback(code, model, OauthGithub.me());
    }

    /**
     * 跳转到gitee授权页面
     *
     * @param request
     * @param session
     * @param response
     */
    @RequestMapping("/call_gitee")
    public void callGitee(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String serverName = "https://" + request.getServerName();
        //设置github的相关
        APIConfig.getInstance().setOpenid_gitee(siteOptions.getValue(Consts.GITEE_CLIENT_ID));
        APIConfig.getInstance().setOpenkey_gitee(siteOptions.getValue(Consts.GITEE_SECRET_KEY));
        APIConfig.getInstance().setRedirect_gitee(serverName + siteOptions.getValue(Consts.GITEE_CALLBACK));

        try {
            response.setContentType("text/html;charset=utf-8");
            String state = TokenUtil.randomState();
            session.setAttribute(GIT_EE_SESSION_STATE, state);
            response.sendRedirect(OauthGitee.me().getAuthorizeUrl(state));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * gitee回调
     *
     * @param code
     * @param state
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/gitee")
    public String callbackGitEe(String code, String state, HttpServletRequest request, HttpSession session, ModelMap model){
        String sessionState = (String)session.getAttribute(GIT_EE_SESSION_STATE);
        if (StringUtils.isBlank(state) || StringUtils.isBlank(sessionState) || !state.equals(sessionState) || StringUtils.isBlank(code)) {
            throw new BaseException("缺少必要的参数");
        }
        request.getSession().removeAttribute(GIT_EE_SESSION_STATE);
        return callback(code, model, OauthGitee.me());
    }


    /**
     * 跳转到支付宝授权页面
     *
     * @param request
     * @param session
     * @param response
     */
    @RequestMapping("/call_alipay")
    public void callAliPay(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String serverName = "https://" + request.getServerName();
        //设置github的相关
        APIConfig.getInstance().setOpenid_alipay(siteOptions.getValue(Consts.ALIPAY_CLIENT_ID));
        APIConfig.getInstance().setOpenkey_alipay(siteOptions.getValue(Consts.ALIPAY_SECRET_KEY));
        APIConfig.getInstance().setRedirect_alipay(serverName + siteOptions.getValue(Consts.ALIPAY_CALLBACK));

        try {
            response.setContentType("text/html;charset=utf-8");
            String state = TokenUtil.randomState();
            session.setAttribute(ALI_PAY_SESSION_STATE, state);
            response.sendRedirect(OauthAliPay.me().getAuthorizeUrl(state));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付宝回调
     *
     * @param auth_code
     * @param state
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/alipay")
    public String callbackAliPay(String auth_code, String state, HttpServletRequest request, HttpSession session, ModelMap model){
        String sessionState = (String)session.getAttribute(ALI_PAY_SESSION_STATE);
        if (StringUtils.isBlank(state) || StringUtils.isBlank(sessionState) || !state.equals(sessionState) || StringUtils.isBlank(auth_code)) {
            throw new BaseException("缺少必要的参数");
        }
        request.getSession().removeAttribute(ALI_PAY_SESSION_STATE);
        return callback(auth_code, model, OauthAliPay.me());
    }

    /**
     * 执行第三方注册绑定
     *
     * @param openOauth
     * @return
     * @throws Exception
     */
    @RequestMapping("/bind_oauth")
    public String bindOauth(OpenOauthVO openOauth, ModelMap model){
        OpenOauthVO thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());
        String username = openOauth.getUsername();

        // 已存在：提取用户信息，登陆
        if (thirdToken != null) {
            username = userService.get(thirdToken.getUserId()).getUsername();
        } else { // 不存在：注册新用户，并绑定此token，登陆
            UserVO user = userService.getByUsername(username);
            if (user == null) {
                UserEntity u = userService.register(wrapUser(openOauth),true);
                userService.updateAvatar(u.getId(), openOauth.getAvatar());

                thirdToken = new OpenOauthVO();
                BeanUtils.copyProperties(openOauth, thirdToken);
                thirdToken.setUserId(u.getId());
                openOauthService.saveOauthToken(thirdToken);
            } else {
                model.put("open", openOauth);
                model.put("check", "check");
                return view(Views.OAUTH_REGISTER);
            }
        }
        return login(username, openOauth.getOauthUserId());
    }

    /**
     * 执行第三方注册绑定验证
     *
     * @param openOauthCheckVO
     * @return
     * @throws Exception
     */
    @RequestMapping("/bind/check")
    @ResponseBody
    public Result bindOauthCheck(OpenOauthCheckVO openOauthCheckVO) throws Exception {
        String username = openOauthCheckVO.getUsername();
        UserEntity user = userService.findByUsername(username);
        String password = MD5.md5(openOauthCheckVO.getPassword());
        if(!password.equals(user.getPassword())){
            return Result.exception(500, "密码验证失败");
        }
        if(StringUtils.isEmpty(user.getOpenId())) {
            user.setOpenId(MD5.md5(openOauthCheckVO.getOauthUserId()));
        }else{
            String openId = user.getOpenId()+","+ MD5.md5(openOauthCheckVO.getOauthUserId());
            user.setOpenId(openId);
        }
        userService.updateUser(user);
        OpenOauthVO thirdToken = new OpenOauthVO();
        BeanUtils.copyProperties(openOauthCheckVO, thirdToken);
        thirdToken.setUserId(user.getId());
        openOauthService.saveOauthToken(thirdToken);

        return executeLogin(username, openOauthCheckVO.getPassword(), false, LoginType.USER.toString());
    }

    /**
     * 执行登陆请求
     *
     * @param username
     * @param accessToken
     * @return
     */
    private String login(String username, String accessToken) {
        String view = view(Views.LOGIN);

        if (StringUtils.isNotBlank(username)) {
            Result<AccountProfile> result = executeLogin(username, accessToken, false, LoginType.OTHER.toString());
            if (result.getCode()==200) {
                view = String.format(Views.REDIRECT_USER_HOME, result.getData().getId());
            }
            return view;
        }
        throw new BaseException("登陆失败");
    }

    private UserParam wrapUser(OpenOauthVO openOauth) {
        UserParam user = new UserParam();
        user.setUsername(openOauth.getUsername());
        user.setName(openOauth.getNickname());
        user.setPassword("123456");
        user.setOpenId(MD5.md5(openOauth.getOauthUserId()));
        if (StringUtils.isNotBlank(openOauth.getAvatar())) {
            user.setAvatar(openOauth.getAvatar());
        } else {
            user.setAvatar(Consts.AVATAR);
        }
        return user;
    }

    /**
     * 回调
     *
     * @param code
     * @param model
     * @return
     */
    public <T extends Oauth> String callback(String code,ModelMap model, T t) {
        OpenOauthBean openOauthBean = null;
        try {
            openOauthBean = t.getUserBeanByCode(code);
        } catch (Exception e) {
            throw new BaseException("解析信息时发生错误");
        }
        OpenOauthVO openOauth = new OpenOauthVO();
        BeanCopyUtils.copyProperties(openOauthBean, openOauth);
        OpenOauthVO thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());
        if (thirdToken == null) {
            model.put("open", openOauth);
            return view(Views.OAUTH_REGISTER);
        }
        openOauthService.updateAuthToken(openOauth);
        String username = userService.get(thirdToken.getUserId()).getUsername();
        return login(username, thirdToken.getOauthUserId());
    }

}