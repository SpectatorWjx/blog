<#include "/classic/inc/layout.ftl"/>

<@layout "注册绑定" "用户注册绑定" "注册绑定界面">
<!-- Bind dialog BEGIN -->
<div id="bind_alert" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">用户已存在，请输入密码绑定</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="control-label" for="password">密码</label>
                    <input class="form-control" id="ajax_bind_password" name="password" type="password" required>
                </div>
                <div class="form-group">
                    <button id="ajax_bind_submit" class="btn btn-primary btn-block btn-sm" type="button">
                        确认
                    </button>
                </div>
                <div class="form-group">
                    <div id="ajax_bind_message" class="text-danger"></div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- Bind dialog END -->

<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">如果已存在账号，可输入绑定</h3>
            </div>
            <div class="panel-body">
                <form id="submitForm" method="POST" action="${base}/oauth/callback/bind_oauth" accept-charset="UTF-8">
                    <input type="hidden" name="oauthType" id="oauthType" value="${open.oauthType}"/>
                    <input type="hidden" name="code" id="oauthCode" value="${open.oauthCode}"/>
                    <input type="hidden" name="oauthUserId"  id="oauthUserId" value="${open.oauthUserId}"/>
                    <input type="hidden" name="accessToken" id="accessToken" value="${open.accessToken}"/>
                    <input type="hidden" name="expireIn"  id="expireIn" value="${open.expireIn}"/>
                    <input type="hidden" name="refreshToken" id="refreshToken" value="${open.refreshToken}"/>
                    <input type="hidden" name="avatar" id="avatar" value="${open.avatar}"/>
                    <input type="hidden" name="check" id="check" value="${check}"/>

                    <div class="form-group ">
                        <label class="control-label" for="username">用户名</label>
                        <input class="form-control" id="username" name="username" type="text" value="${open.username}" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">
                        提  交
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    $('#username').bind('input propertychange', function () {
        $('#ajax_bind_username').html($(this).val());
    });

    seajs.use('validate', function (validate) {
        validate.oauthRegister('#submitForm');
    });
    function showBind () {
        $('#bind_alert').modal();
        $('#ajax_bind_submit').unbind().click(function () {
            doPostBind();
        });
    }
    function doPostBind() {
        var password = $('#ajax_bind_password').val();
        var oauthType = $('#oauthType').val();
        var code =  $('#oauthCode').val();
        var oauthUserId =   $('#oauthUserId').val();
        var accessToken =   $('#accessToken').val();
        var expireIn =  $('#expireIn').val();
        var refreshToken =   $('#refreshToken').val();
        var avatar =  $('#avatar').val();
        var username = $('#username').val();
        jQuery.post(_MTONS.BASE_PATH + '/oauth/callback/bind/check/', {
            'password':password,
            'oauthType':oauthType,
            'oauthCode':code,
            'oauthUserId':oauthUserId,
            'accessToken':accessToken,
            'expireIn':expireIn,
            'refreshToken':refreshToken,
            'avatar':avatar,
            'username':username
        }, function (ret) {
            if (ret && ret.code === 200) {
                window.location.reload();
            } else {
                layer.msg(ret.message, {icon: 2});
            }
        });
    }
    window.onload = function() {
        let check = $("input[name='check']").val();
        if(check && check === 'check'){
            showBind();
            return false;
        }
    }
</script>
</@layout>