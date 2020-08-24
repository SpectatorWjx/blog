<#include "/classic/inc/layout.ftl"/>

<@layout "注册" "用户邮箱注册" "使用邮箱注册界面">
<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">注册</h3>
            </div>
            <div class="panel-body">
                <form id="registerForm" method="POST" action="##" onsubmit="return false;" accept-charset="UTF-8">
                    <div class="form-group ">
                        <label class="control-label" for="username">用户名</label>
                        <input class="form-control" id="username" name="username" type="text" placeholder="字母和数字的组合, 不少于5位" required>
                    </div>
                    <@controls name="register_email_validate">
                        <div class="form-group">
                            <label class="control-label" for="username">邮箱</label>
                                <input type="text" class="form-control" name="email" maxlength="64" placeholder="请输入邮箱地址" required>
                        </div>
                    </@controls>
                    <div class="form-group ">
                        <label class="control-label" for="username">密码</label>
                        <input class="form-control" id="password" name="password" type="password" maxlength="18" placeholder="请输入密码" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="username">确认密码</label>
                        <input class="form-control" id="password2" name="password2" type="password" placeholder="请再一次输入密码" maxlength="18">
                    </div>
                    <button type="button" onclick="sendEmail();" class="btn btn-primary btn-block">
                        提交
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    seajs.use('validate', function (validate) {
        validate.register('#submitForm');
    });
    function sendEmail() {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/register" ,
            data: $('#registerForm').serialize(),
            success: function (result) {
                if (result.code === 200) {
                    window.location.href ="/auth/register_active";
                }else{
                    layer.msg(result.message, {icon: 2});
                }
            },
            error : function() {
                layer.msg("系统错误", {icon: 2});
            }
        });
    }
</script>

</@layout>