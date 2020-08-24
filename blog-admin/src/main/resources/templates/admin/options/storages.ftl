<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="form-group">
        <label class="col-sm-2 control-label">存储方式</label>
        <div class="col-sm-3">
            <select class="form-control" name="storage_scheme" data-select="${options['storage_scheme']}">
                <option value="native">本地存储</option>
                <option value="upyun">又拍云</option>
            </select>
        </div>
    </div>
    <div class="scheme" data-scheme="upyun">
        <div class="form-group">
            <label class="col-sm-2 control-label">空间名称</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_bucket" class="form-control" value="${options['upyun_oss_bucket']}" placeholder="又拍云bucket名称">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">操作员名称</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_operator" class="form-control" value="${options['upyun_oss_operator']}" placeholder="又拍云operator">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">操作员密码</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_password" class="form-control" value="${options['upyun_oss_password']}" placeholder="又拍云operator password">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">域名绑定</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_domain" class="form-control" value="${options['upyun_oss_domain']}" placeholder="示例: https://blogad.delpast.com">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">文件目录</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_src" class="form-control" value="${options['upyun_oss_src']}" placeholder="示例: /static/">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>
</form>
<script>
    $(function () {
        $('select[name=storage_scheme]').change(function () {
            var value = $(this).val();
            $('.scheme').each(function () {
                if ($(this).data('scheme') === value) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        }).trigger('change');
    });
</script>