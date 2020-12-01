package com.wang.blog.web.controller.site.auth;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.utils.EmailUtil;
import com.wang.blog.service.MailService;
import com.wang.blog.service.SecurityCodeService;
import com.wang.blog.service.UserService;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.UserVO;
import com.wang.blog.web.controller.BaseController;
import com.wang.common.common.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wjx
 * @date 2019/08/13
 */
@RestController
@RequestMapping("/email")
public class EmailController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private SecurityCodeService securityCodeService;

    private static final String EMAIL_TITLE = "[{0}]您正在使用邮箱安全验证服务";

    @GetMapping("/send_code")
    public Result sendCode(String email, @RequestParam(name = "type", defaultValue = "1") Integer type) {

        if(!EmailUtil.isEmail(email)){
            return Result.failure("请输入正确邮箱地址");
        }
        if(type == null){
            return Result.failure("缺少必要的参数");
        }

        String key = email;

        switch (type) {
            case Consts.CODE_BIND:
                AccountProfile profile = getProfile();
                if(profile == null){
                    return Result.failure("请先登陆后再进行此操作");
                }
                UserVO exist = userService.getByEmail(email);
                if(exist != null){
                    return Result.failure("邮箱已被使用");
                }
                key = String.valueOf(profile.getId());
                break;
            case Consts.CODE_FORGOT:
                UserVO user = userService.getByEmail(email);
                if(user == null){
                    return Result.failure("用户不存在");
                }
                key = String.valueOf(user.getId());
                break;
            case Consts.CODE_REGISTER:
                key = email;
                break;
            default:
        }

        String code = securityCodeService.generateCode(key, type, email);
        Map<String, Object> context = new HashMap<>();
        context.put("code", code);

        String title = MessageFormat.format(EMAIL_TITLE, siteOptions.getValue("site_name"));
        mailService.sendTemplateEmail(email, title, Consts.EMAIL_TEMPLATE_CODE, context);
        return Result.success("邮件发送成功");
    }

}
