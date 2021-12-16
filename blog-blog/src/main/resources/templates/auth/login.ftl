<#include "/inc/layout.ftl"/>
<@layout "登陆" "用户登陆" "用户登陆，注册，第三方注册登录">

<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="hidden">
                <img src="<@resource src=options['site_favicon']/>" alt="站点图"/>
            </div>
            <div class="panel-body">
                <form id="loginForm" method="POST" action="#" onsubmit="return false;" accept-charset="UTF-8">
                    <div class="form-group">
                        <label class="control-label" for="username">账号</label>
                        <input class="form-control" name="username" type="text" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password">密码</label>
                        <input class="form-control" name="password" type="password" required>
                    </div>
                    <div class="form-group">
                        <span class="pull-right">
                            <a class="forget-password" href="${base}/forgot">忘记密码？</a>
                        </span>
                    </div>
                    <div class="form-group">
                        <div id="captcha">
                            <p id="wait">正在加载验证码......</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <button type="submit" id="submit" class="btn btn-primary btn-block"> 登陆 </button>
                    </div>
                    <@controls name="register">
                        <fieldset class="form-group">
                            <a href="${base}/register" class="btn btn-default btn-block">注册</a>
<#--                            <p class="register-third-login">-->
<#--                                <span>快速登陆：</span>-->
<#--                                <#if site.hasValue("qq_app_id")>-->
<#--                                    <a href="${base}/oauth/callback/call_qq" class="third-login" target="_blank">-->
<#--                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/qq.png" alt="三方图片"/>-->
<#--                                    </a>-->
<#--                                </#if>-->
<#--                                <#if site.hasValue("weibo_client_id")>-->
<#--                                    <a href="${base}/oauth/callback/call_weibo" class="third-login" target="_blank">-->
<#--                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/weibo.png" alt="三方图片"/>-->
<#--                                    </a>-->
<#--                                </#if>-->
<#--                                <#if site.hasValue("github_client_id")>-->
<#--                                    <a href="${base}/oauth/callback/call_github" class="third-login wwwHidden" target="_blank">-->
<#--                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/github.jpg" alt="三方图片"/>-->
<#--                                    </a>-->
<#--                                </#if>-->
<#--                                <#if site.hasValue("alipay_client_id")>-->
<#--                                    <a href="${base}/oauth/callback/call_alipay" class="third-login wwwHidden" target="_blank">-->
<#--                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/alipay.jpg" alt="三方图片"/>-->
<#--                                    </a>-->
<#--                                </#if>-->
<#--                                <#if site.hasValue("gitee_client_id")>-->
<#--                                    <a href="${base}/oauth/callback/call_gitee"  class="third-login wwwHidden" target="_blank">-->
<#--                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/gitee.jpg" alt="三方图片"/>-->
<#--                                    </a>-->
<#--                                </#if>-->
<#--                            </p>-->
                        </fieldset>
                    </@controls>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://static.geetest.com/static/tools/gt.js"></script>
<script type="text/javascript">
    // $(function(){
    //     if(window.location.host != "delpast.com"){
    //         $('.wwwHidden').css('display','none');
    //     }
    // });
    $.ajax({
        url: "captcha/register?t=" + (new Date()).getTime(),
        type: "get",
        dataType: "json",
        success: function (data) {
            initGeetest({
                gt:data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha,
                offline: !data.success,
                product: "float",
                width: "100%"
            }, handler);
        }
    });
    seajs.use('validate', function (validate) {
        validate.login('#loginForm');
    });
    let handler = function (captchaObj) {
        $("#submit").click(function (e) {
            if(!$("#loginForm").validate().form()){
                return;
            }
            let result = captchaObj.getValidate();
            if (!result) {
                layer.msg("请点击验证");
                e.preventDefault()
            }else{
                // 符合规则进入后台 ajax处理
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/login" ,
                    data: $('#loginForm').serialize(),
                    success: function (result) {
                        if (result.code === 200) {
                            window.location.href = result.data;
                        }else{
                            layer.msg(result.message, {icon: 2});
                            captchaObj.reset();
                        }
                    },
                    error : function() {
                        layer.msg("系统错误", {icon: 2});
                        captchaObj.reset();
                    }
                });
            }
        });
        captchaObj.appendTo("#captcha");
        captchaObj.onReady(function () {
            $("#wait").hide();
        });
    }
</script>
</@layout>

