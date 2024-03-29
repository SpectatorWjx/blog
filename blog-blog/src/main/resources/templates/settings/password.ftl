<#include "/inc/layout.ftl"/>
<@layout "修改用户信息" "修改用户密码" "修改用户密码">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li><a href="profile">基本信息</a></li>
            <li><a href="email">修改邮箱</a></li>
			<li><a href="avatar">修改头像</a></li>
			<li class="active"><a href="password">修改密码</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div class="tab-pane active" id="passwd">
			<form id="passwordForm" action="#" method="post" onsubmit="return false;" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-lg-3" for="password">当前密码</label>
					<div class="col-lg-4">
						<input type="password" class="form-control" name="oldPassword" maxlength="18" placeholder="请输入当前密码" required>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-lg-3" for="password">新密码</label>
					<div class="col-lg-4">
						<input type="password" class="form-control" id="password" name="password" placeholder="请输入新密码" maxlength="18" required>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-lg-3" for="password2">确认密码</label>
					<div class="col-lg-4">
						<input type="password" class="form-control" name="password2" data-required placeholder="请再输入一遍新密码" maxlength="18" required>
					</div>
				</div>
				<div class="form-group">
					<div class="text-center">
					<button type="submit" onclick="updatePassword();" class="btn btn-primary">提交</button>
					</div>
				</div><!-- /form-actions -->
			</form>
		</div>
	</div><!-- /panel-content -->
</div><!-- /panel -->

<script type="text/javascript">
    seajs.use('validate', function (validate) {
        validate.updatePassword('#passwordForm');
    });
    function updatePassword() {
		if(!$("#passwordForm").validate().form()){
			return;
		}
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/settings/password" ,
			data: $('#passwordForm').serialize(),
			success: function (result) {
				if (result.code === 200) {
					layer.msg("success", {icon: 1});
					window.location.href = "/login";
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