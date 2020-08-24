<#include "/classic/inc/layout.ftl"/>
<@layout "修改用户信息" "修改用户信息" "修改用户的基本信息">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li class="active"><a href="profile">基本信息</a></li>
            <li><a href="email">修改邮箱</a></li>
			<li><a href="avatar">修改头像</a></li>
			<li><a href="password">修改密码</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div class="tab-pane active" id="profile">
			<form id="profileForm" action="#" method="post" onsubmit="return false;"  class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-lg-3" for="nickname">昵称</label>
					<div class="col-lg-4">
						<input type="text" class="form-control" name="name" value="${view.name}" maxlength="7" required>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-lg-3" for="nickname">个性签名</label>
					<div class="col-lg-6">
						<textarea name="signature" class="form-control" rows="3" maxlength="128">${view.signature}</textarea>
					</div>
				</div>
				<div class="form-group">
					<div class="text-center">
						<button type="button" onclick="updateProfile();"  class="btn btn-primary">提交</button>
					</div>
				</div><!-- /form-actions -->
			</form>
		</div>
	</div><!-- /panel-content -->
</div><!-- /panel -->

<script type="text/javascript">
    seajs.use('validate', function (validate) {
        validate.updateProfile('#submitForm');
    });
	function updateProfile() {
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/settings/profile" ,
			data: $('#profileForm').serialize(),
			success: function (result) {
				if (result.code === 200) {
					layer.msg("success", {icon: 1});
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