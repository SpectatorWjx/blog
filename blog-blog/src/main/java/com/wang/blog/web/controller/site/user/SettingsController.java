package com.wang.blog.web.controller.site.user;

import com.wang.blog.base.lang.Consts;
import com.wang.blog.base.utils.FileKit;
import com.wang.blog.base.utils.image.ImageUrlUtil;
import com.wang.blog.service.SecurityCodeService;
import com.wang.blog.service.UserService;
import com.wang.blog.service.mongo.MongoService;
import com.wang.blog.vo.AccountProfile;
import com.wang.blog.vo.ImageVo;
import com.wang.blog.vo.UserVO;
import com.wang.blog.web.controller.BaseController;
import com.wang.blog.web.controller.site.Views;
import com.wang.blog.web.controller.site.posts.UploadController;
import com.wang.common.common.base.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wjx
 * @date 2019/08/13
 */
@Controller
@RequestMapping("/settings")
public class SettingsController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MongoService mongoService;
    @Autowired
    private SecurityCodeService securityCodeService;

    @GetMapping(value = "/profile")
    public String view(ModelMap model) {
        AccountProfile profile = getProfile();
        UserVO view = userService.get(profile.getId());
        view.setAvatar(profile.getAvatar());
        model.put("view", view);
        return view(Views.SETTINGS_PROFILE);
    }

    @GetMapping(value = "/email")
    public String email() {
        return view(Views.SETTINGS_EMAIL);
    }

    @GetMapping(value = "/avatar")
    public String avatar() {
        return view(Views.SETTINGS_AVATAR);
    }

    @GetMapping(value = "/password")
    public String password() {
        return view(Views.SETTINGS_PASSWORD);
    }

    @PostMapping(value = "/profile")
    @ResponseBody
    public Result updateProfile(String name, String signature, ModelMap model) {
        AccountProfile profile = getProfile();
        try {
            UserVO user = new UserVO();
            user.setId(profile.getId());
            user.setName(name);
            user.setSignature(signature);
            putProfile(userService.update(user));
            // put 最新信息
            UserVO view = userService.get(profile.getId());
            model.put("view", view);
            return Result.success();
        } catch (Exception e) {
            return Result.exception(500, e.getMessage());
        }
    }

    @PostMapping(value = "/email")
    @ResponseBody
    public Result updateEmail(String email, String code) {
        AccountProfile profile = getProfile();
        try {
            Assert.hasLength(email, "请输入邮箱地址");
            Assert.hasLength(code, "请输入验证码");
            securityCodeService.verify(String.valueOf(profile.getId()), Consts.CODE_BIND, code);
            // 先执行修改，判断邮箱是否更改，或邮箱是否被人使用
            AccountProfile p = userService.updateEmail(profile.getId(), email);
            putProfile(p);

            return Result.success();
        } catch (Exception e) {
            return Result.exception(500, e.getMessage());
        }
    }

    @PostMapping(value = "/password")
    @ResponseBody
    public Result updatePassword(String oldPassword, String password) {
        try {
            AccountProfile profile = getProfile();
            userService.updatePassword(profile.getId(), oldPassword, password);
            SecurityUtils.getSubject().logout();
            return Result.success();
        } catch (Exception e) {
            return Result.exception(500, e.getMessage());
        }
    }

    @PostMapping("/avatar")
    @ResponseBody
    public UploadController.UploadResult updateAvatar(@RequestParam(value = "file", required = false) MultipartFile file){
        UploadController.UploadResult result = new UploadController.UploadResult();
        AccountProfile profile = getProfile();

        // 检查空
        if (null == file || file.isEmpty()) {
            return result.error(UploadController.errorInfo.get("NOFILE"));
        }

        String fileName = file.getOriginalFilename();

        // 检查类型
        if (!FileKit.checkFileType(fileName)) {
            return result.error(UploadController.errorInfo.get("TYPE"));
        }

        // 保存图片
        try {
            ImageVo imageVo = mongoService.saveFile(file, null, null);
            String path = ImageUrlUtil.getImageUrl(imageVo.getId());

            AccountProfile user = userService.updateAvatar(profile.getId(), path);
            putProfile(user);

            result.ok(UploadController.errorInfo.get("SUCCESS"));
            result.setName(fileName);
            result.setPath(path);
            result.setSize(file.getSize());
        } catch (Exception e) {
            result.error(UploadController.errorInfo.get("UNKNOWN"));
        }
        return result;
    }
}
