<#include "/admin/utils/login.ftl"/>
<@layout>
    <div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">后台登陆</h3>
            </div>
            <div class="panel-body">
                <form method="POST" action="login" id="login_Form" accept-charset="UTF-8">
                    <div class="form-group">
                        <label class="control-label" for="username">账号</label>
                        <input class="form-control" name="username" type="text" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password">密码</label>
                        <input class="form-control" name="password" type="password" required>
                    </div>
                    <br>
                    <div id="captcha">
                        <p id="wait">正在加载验证码......</p>
                    </div>
                    <br>
                    <p id="notice" class="hide">请先完成验证</p>
                    <div class="form-group">
                        <button type="submit" id ="submit" class="btn btn-primary btn-block">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div style="width: 100%; bottom: 0px;box-sizing: border-box;position: absolute;text-align:center">© 2019-2021  随心小记 <a href="http://www.beian.miit.gov.cn">黑ICP备17009694号-2 </a>All Rights Reserved.
    <div class="gh-foot-min-back hidden-xs hidden-sm">
        <!-- 请保留此处标识-->
        <span class="footer-nav-item">Powered by <a href="https://github.com/SpectatorWjx" target="_blank">myBlog</a></span>
    </div>
</div>
<script src="https://static.geetest.com/static/tools/gt.js"></script>
<script>
    var handler = function (captchaObj) {
        $("#submit").click(function (e) {
            var result = captchaObj.getValidate();
            if (!result) {
                layer.msg("请点击验证");
                e.preventDefault()
            }
        });
        captchaObj.appendTo("#captcha");
        captchaObj.onReady(function () {
            $("#wait").hide();
        });
    };
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
</script>

</@layout>

