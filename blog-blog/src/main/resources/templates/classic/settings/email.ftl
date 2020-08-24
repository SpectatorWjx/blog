<#include "/classic/inc/layout.ftl"/>
<@layout "修改邮箱" "修改邮箱" "修改邮箱">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li><a href="profile">基本信息</a></li>
            <li class="active"><a href="email">修改邮箱</a></li>
			<li><a href="avatar">修改头像</a></li>
			<li><a href="password">修改密码</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div class="tab-pane active" id="profile">
			<form id="emailForm" action="#" method="post" onsubmit="return false;" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-lg-3" for="email">邮箱地址</label>
					<div class="col-lg-4">
						<div class="input-group">
							<input type="text" class="form-control" name="email" value="${profile.email}" maxlength="64" required>
                            <span class="input-group-btn">
								<button class="btn btn-primary" type="button" id="sendCode">获取验证码</button>
						  	</span>
						</div>
					</div>
				</div>
                <div class="form-group">
                    <label class="control-label col-lg-3">验证码</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control" name="code" maxlength="6" required>
                    </div>
                </div>
				<div class="form-group">
					<div class="text-center">
						<button type="button" onclick="updateEmail();" class="btn btn-primary">提交</button>
					</div>
				</div><!-- /form-actions -->
			</form>
		</div>
	</div><!-- /panel-content -->
</div><!-- /panel -->

<script type="text/javascript">
    seajs.use('validate', function (validate) {
        validate.updateEmail('#submitForm', '#sendCode');
    });
	function updateEmail() {
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/settings/email" ,
			data: $('#emailForm').serialize(),
			success: function (result) {
				if (result.code === 200) {
					layer.msg("success", {icon: 1});
					window.location.reload();
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