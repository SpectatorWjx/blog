<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>友链管理</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">友链管理</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">友链列表</h3>
                    <div class="box-tools">
                        <div class="grid-btn">
                            <button class="btn btn-info" onclick="linkAdd()"><i
                                        class="fa fa-plus"></i>&nbsp;新增
                            </button>
                            <button class="btn btn-danger" onclick="batchDelete()"><i
                                        class="fa fa-trash-o"></i>&nbsp;批量删除
                            </button>
                        </div>
                    </div>
                </div>
                <div class="box-body">
                    <form id="qForm" class="form-inline">
                        <input type="hidden" name="pageNo" value="${page.number + 1}"/>
                    </form>
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="50"><input type="checkbox" class="checkall"></th>
                                <th width="50">#</th>
                                <th>类型</th>
                                <th>网站名称</th>
                                <th>网站链接</th>
                                <th>网站描述</th>
                                <th width="70">排序值</th>
                                <th width="120">添加日期</th>
                                <th width="120">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list page.content as row>
                                <tr>
                                    <td>
                                        <input type="checkbox" name="id" value="${row.id}">
                                    </td>
                                    <td>${row.id}</td>
                                    <td>${row.linkType}</td>
                                    <td>${row.linkName}</td>
                                    <td>${row.linkUrl}</td>
                                    <td>${row.linkDescription}</td>
                                    <td>${row.linkRank}</td>
                                    <td>${row.createTime}</td>
                                    <td>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}"
                                           data-action="update">修改
                                        </a>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}"
                                           data-action="delete">删除
                                        </a>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="box-footer">
                    <@pager "list" page 5 />
                </div>
            </div>
        </div>
    </div>
    <!-- /.content -->
    <div class="content">
        <!-- 模态框（Modal） -->
        <div class="modal fade" id="linkModal" tabindex="-1" role="dialog" aria-labelledby="linkModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                        <h6 class="modal-title" id="linkModalLabel">Modal</h6>
                    </div>
                    <div class="modal-body">
                        <form id="linkForm">
                            <div class="form-group">
                                <div class="alert alert-danger" id="edit-error-msg" style="display: none;">
                                    错误信息展示栏。
                                </div>
                            </div>
                            <input type="hidden" class="form-control" id="linkId" name="linkId">
                            <div class="form-group">
                                <label for="linkType" class="control-label">友链类型:</label>
                                <select class="form-control" id="linkType" name="linkType">
                                    <option selected="selected" value="0">友链</option>
                                    <option value="1">网站收藏</option>
                                    <option value="2">个人链接</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="linkName" class="control-label">网站名称:</label>
                                <input type="text" class="form-control" id="linkName" name="linkName"
                                       placeholder="请输入网站名称" required="true">
                            </div>
                            <div class="form-group">
                                <label for="linkUrl" class="control-label">网站链接:</label>
                                <input type="url" class="form-control" id="linkUrl" name="linkUrl"
                                       placeholder="请输入网站链接" required="true">
                            </div>
                            <div class="form-group">
                                <label for="linkDescription" class="control-label">网站描述:</label>
                                <input type="url" class="form-control" id="linkDescription" name="linkDescription"
                                       placeholder="请输入网站描述" required="true">
                            </div>
                            <div class="form-group">
                                <label for="linkRank" class="control-label">排序值:</label>
                                <input type="number" class="form-control" id="linkRank" name="linkRank"
                                       placeholder="请输入排序值" required="true">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="saveButton" onclick="saveLink()">确认</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal -->
    </div>
</section>

<script>
    var J = jQuery;

    function ajaxReload(json) {
        if (json.code >= 0) {
            if (json.message != null && json.message != '') {
                layer.msg(json.message, {icon: 1});
            }
            $('#qForm').submit();
        } else {
            layer.msg(json.message, {icon: 2});
        }
    }

    function linkAdd() {
        reset();
        $('.modal-title').html('友链添加');
        $('#linkModal').modal('show');
    }

    function saveLink() {
        var linkId = $("#linkId").val();
        var linkName = $("#linkName").val();
        var linkUrl = $("#linkUrl").val();
        var linkDescription = $("#linkDescription").val();
        var linkRank = $("#linkRank").val();
        if ('' == linkName) {
            $('#edit-error-msg').css("display", "block");
            $('#edit-error-msg').html("请输入名称！");
            return;
        }
        if ('' == linkUrl) {
            $('#edit-error-msg').css("display", "block");
            $('#edit-error-msg').html("请输入网址！");
            return;
        }
        if ('' == linkDescription) {
            $('#edit-error-msg').css("display", "block");
            $('#edit-error-msg').html("请输入描述！");
            return;
        }
        if ('' == linkRank || linkRank < 0) {
            $('#edit-error-msg').css("display", "block");
            $('#edit-error-msg').html("请输入排序值！");
            return;
        }
        var params = $("#linkForm").serialize();
        var url = '/admin/link/save';
        if (linkId != null && linkId !== '') {
            url = '/admin/link/update';
        }
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            data: params,
            success: function (result) {
                $("#linkId").val("");
                if (result.code === 200) {
                    $('#linkModal').modal('hide');
                    layer.alert("保存成功", {
                        icon: 6,
                        time: 2000,
                    });
                    window.location.reload();
                }
                else {
                    $('#linkModal').modal('hide');
                    layer.msg(result.message||"保存失败", {
                        icon: "error",
                        time: 2000,
                    });
                }
            },
            error: function () {
                layer.msg("操作失败", {
                    icon: "error",
                    time: 2000,
                });
            }
        });
    }

    function linkEdit(id) {
        if (id == null) {
            return;
        }
        reset();
        //请求数据
        $.get("/admin/link/info/" + id, function (r) {
            if (r.code === 200 && r.data != null) {
                //填充数据至modal
                $("#linkName").val(r.data.linkName);
                $("#linkUrl").val(r.data.linkUrl);
                $("#linkDescription").val(r.data.linkDescription);
                $("#linkRank").val(r.data.linkRank);
                //根据原linkType值设置select选择器为选中状态
                if (r.data.linkType == 1) {
                    $("#linkType option:eq(1)").prop("selected", 'selected');
                }
                if (r.data.linkType == 2) {
                    $("#linkType option:eq(2)").prop("selected", 'selected');
                }
            }
        });
        $('.modal-title').html('友链修改');
        $('#linkModal').modal('show');
        $("#linkId").val(id);
    }

    $(function () {
        // 删除
        $('#dataGrid a[data-action="update"]').bind('click', function () {
            var that = $(this);
            linkEdit(that.attr('data-id'));
        });
        // 删除
        $('#dataGrid a[data-action="delete"]').bind('click', function () {
            var that = $(this);
            layer.confirm('确定删除此项吗?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                doDelete(that.attr('data-id'));
            }, function () {
            });
            return false;
        });
    });

    function doDelete(ids) {
        J.getJSON('${base}/admin/link/deleteBatch', J.param({'id': ids}, true), ajaxReload);
    }

    function reset() {
        $("#linkName").val('');
        $("#linkUrl").val('');
        $("#linkDescription").val('');
        $("#linkRank").val(0);
        $('#edit-error-msg').css("display", "none");
        $("#linkType option:first").prop("selected", 'selected');
    }

    function batchDelete() {
        var check_length = $("input[type=checkbox][name=id]:checked").length;

        if (check_length == 0) {
            layer.msg("请至少选择一项", {icon: 2});
            return false;
        }

        var ids = [];
        $("input[type=checkbox][name=id]:checked").each(function () {
            ids.push($(this).val());
        });

        layer.confirm('确定删除此项吗?', {
            btn: ['确定', '取消'], //按钮
            shade: false //不显示遮罩
        }, function () {
            doDelete(ids);
        }, function () {
        });
    }
</script>
</@layout>