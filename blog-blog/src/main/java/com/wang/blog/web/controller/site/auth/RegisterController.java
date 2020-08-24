/**
 * 
 */package com.wang.blog.web.controller.site.auth;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.param.UserParam;
import com.wang.blog.service.MailService;
import com.wang.blog.service.UserService;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.UserVO;
import com.wang.blog.web.controller.BaseController;
import com.wang.blog.web.controller.site.Views;
import com.wang.common.common.base.Result;
import com.wang.common.common.utils.StringUtil;
import com.wang.common.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wjx
 *
 */
@Controller
@ConditionalOnProperty(name = "site.controls.register", havingValue = "true", matchIfMissing = true)
public class RegisterController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	MailService mailService;

	@GetMapping("/register")
	public String view() {
		AccountProfile profile = getProfile();
		if (null == profile || StringUtil.isEmpty(profile.getId())){
			return view(Views.REGISTER);
		}else{
			return String.format(Views.REDIRECT_USER_HOME, profile.getId());
		}
	}

	@PostMapping("/register")
	@ResponseBody
	public Result register(UserParam param) {
		try {
			UserEntity user = userService.register(param, false);

			// 发送激活邮件
			String url = "https://delpast.com/activation/" + user.getId()+"?state="+user.getState();
			Map<String, Object> map = new HashMap<>(2);
			map.put("email", user.getEmail());
			map.put("url", url);
			mailService.sendTemplateEmail(user.getEmail(), "激活账号", Consts.EMAIL_TEMPLATE_ACTIVE, map);
		} catch (Exception e) {
			return Result.failure(e.getMessage());
		}
		return Result.success();
	}

	@RequestMapping(value = "/activation/{userId}", method = RequestMethod.GET)
	public String activation(@PathVariable("userId") String userId, String state, ModelMap model) {
		String view = view(Views.REGISTER_ACTIVE_SUCCESS);
		// 用 Integer 类型可以接收空值null，避免使用 int 报错
		if (userId == null || state == null) {
			model.addAttribute("msg", "参数错误");
			model.addAttribute("target", "/login");
		}
		UserEntity user = userService.activation(userId, state);
		if (user == null) {
			model.addAttribute("msg", "激活失败(用户不存在),请注册");
			model.addAttribute("target", "/register");
		} else{
			model.addAttribute("msg", "激活成功，请登录");
			model.addAttribute("target", "/login");
		}
		return view;
	}

	@GetMapping("auth/register_active")
	public String registerActive(){
		return view(Views.REGISTER_ACTIVE);
	}
}