<#if user>
<#include "/inc/layout.ftl"/>
<@layout user.name + "的文章" user.name + "的文章列表" user.name + "发表的所有文章">
<div class="row users-show">
    <div class="col-xs-12 col-md-3 side-left">
		<#include "/inc/user_sidebar.ftl"/>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">发表的文章
                <p style="text-align:right;">[<span id="al_expand_collapse" style="cursor: s-resize;">展开/收缩</span>]</p>
            </div>
            <@user_contents userId=user.id>
                <div class="panel-body">
                    <article class="art">
                        <div class="art-main">
                            <div class="art-content">
                                <div id="archives">
                                    <#list results as yearRow>
                                    <h3 class="al_year">${yearRow.year}</h3>
                                    <ul class="al_mon_list">
                                        <#list yearRow.monthPostVoList as monthRow>
                                        <li class="al_li">
                                            <span class="al_mon" style="cursor: s-resize;">${monthRow.month}月(<span id="post-num">0</span>篇文章)</span>
                                            <ul class="al_post_list">
                                                <#list monthRow.postVOList as row>
                                                    <li>
                                                        <a href="${base}/post/${row.id}"><span style="color:#0bf;">${row.createDay}</span>
                                                            ${row.title}
                                                        </a>
                                                        <span class="meta">
                                                            &nbsp;&nbsp;
                                                            ${row.favors} 点赞
                                                            <span> ⋅ </span>
                                                            ${row.comments} 回复
                                                        </span>
                                                        <div class="pull-right hidden-xs">
                                                            <#if owner>
                                                            <a class="act_edit" href="javascript:void(0);" data-evt="edit" data-id="${row.id}" data-toggle="tooltip" title="编辑文章">
                                                                <i class="icon icon-note"></i>
                                                            </a>
                                                            <a class="act_delete" href="javascript:void(0);" data-evt="trash" data-id="${row.id}" data-toggle="tooltip" title="删除文章">
                                                                <i class="icon icon-close"></i>
                                                            </a>
                                                            </#if>
                                                        </div>
                                                    </li>
                                                </#list>
                                            </ul>
                                        </li>
                                        </#list>
                                    </ul>
                                    </#list>
                                </div>
                            </div>
                        </div>
                    </article>
                </div>
            </@user_contents>
        </div>
    </div>
</div>
<!-- /end -->
<script type="text/javascript">
    $(function() {
	// delete
        $('a[data-evt=trash]').click(function () {
            var id = $(this).attr('data-id');

            layer.confirm('确定删除此项吗?', {
                btn: ['确定','取消'], //按钮
                shade: false //不显示遮罩
            }, function(){
                jQuery.getJSON('${base}/post/delete/' + id, function (ret) {
                    layer.msg(ret.message, {icon: 1});
                    if (ret.code === 200) {
                        location.reload();
                    }
                });

            }, function(){

            });
        });

        // edit
        $('a[data-evt=edit]').click(function () {
            var id = $(this).attr('data-id');
            window.location.href='${base}/post/editing?id=' + id;
        });
    })
    $(function () {
        $('#al_expand_collapse,#archives span.al_mon').css({
            cursor: 's-resize'
        })
        $('#archives span.al_mon').each(function () {
            var num = $(this).next().children('li').length
            $(this).children('#post-num').text(num)
        })
        var $al_post_list = $('#archives ul.al_post_list'),
            $al_post_list_f = $('#archives ul.al_post_list:first')
        $al_post_list.hide(1, function () {
            $al_post_list_f.show()
        })
        $('#archives span.al_mon').click(function () {
            $(this).next().slideToggle(400)
            return false
        })
        if (document.body.clientWidth > 860) {
            $('#archives li.al_li').mouseover(function () {
                $(this).children('.al_post_list').show(400)
                return false
            })
            if (false) {
                $('#archives li.al_li').mouseout(function () {
                    $(this).children('.al_post_list').hide(400)
                    return false
                })
            }
        }
        let al_expand_collapse_click = 0
        $('#al_expand_collapse').click(function () {
            if (al_expand_collapse_click == 0) {
                $al_post_list.show()
                al_expand_collapse_click++
            } else if (al_expand_collapse_click == 1) {
                $al_post_list.hide()
                al_expand_collapse_click--
            }
        })
    })
</script>
<style>
    #archives ul {
        list-style:none;
        margin-bottom:0;
        left:10px
    }
    #archives li {
        list-style:none
    }
    #archives li>ul,li>ol {
        margin-left:-2.7em
    }
    #archives h3 {
        margin-top:0;
        margin-bottom:0
    }
    .art-content #archives .al_mon_list {
        position:relative;
        padding:10px 0;
        display:inline-block;
        vertical-align:middle
    }
    .art .art-content #archives a {
        color:#000
    }
    .art .art-content #archives a:hover {
        color:orange
    }
    .art .art-content #archives .al_year {
         padding-left:100px
     }
    .art-content #archives .al_mon_list .al_mon,.art .art-content .al_mon_list .al_post_list>li {
        position:relative
    }
    .art-content #archives .al_mon_list .al_mon,.art-content #archives .al_mon_list span {
        padding:0;
        border-radius:0;
        margin:0;
        background:none;
        font-weight:normal
    }
    .art .art-content #archives a {
        font-weight:normal
    }
    .art .art-content .al_mon_list {
        width:100%
    }
    .art .art-content .al_mon_list .al_post_list>li:before {
        position:absolute;
        left:116px;
        background:#fff;
        height:12px;
        width:12px;
        border-radius:6px;
        top:6px;
        content:""
    }
    .art .art-content .al_mon_list .al_post_list>li:after {
        position:absolute;
        left:118px;
        background:#6ecaf5;
        height:8px;
        width:8px;
        border-radius:6px;
        top:8px;
        content:""
    }
    .art-content #archives .al_mon_list .al_mon:before {
        position:absolute;
        left:113px;
        background:#fff;
        height:18px;
        width:18px;
        border-radius:9px;
        top:3px;
        content:""
    }
    .art-content #archives .al_mon_list .al_mon:after {
        position:absolute;
        left:116px;
        background:#6ecaf5;
        height:12px;
        width:12px;
        border-radius:6px;
        top:6px;
        content:""
    }
    .art .art-content .al_mon_list .al_post_list>li {
        padding-left:140px
    }
    .art-content #archives .al_mon_list .al_post_list,.art-content #archives .al_mon {
        display:block
    }
    .art-content #archives .al_mon_list:before {
        max-height:100%;
        height:100%;
        width:4px;
        background:#6ecaf5;
        position:absolute;
        left:120px;
        content:"";
        top:0
    }
    .art-content #archives .al_mon_list .al_mon:before,.art .art-content .al_mon_list .al_post_list>li:before {
        -webkit-box-shadow:1px 1px 1px #bbb;
        box-shadow:1px 1px 1px #bbb
    }
    .art-content #archives .al_mon_list .al_mon:after,.art .art-content .al_mon_list .al_post_list>li:after {
        background:#0bf
    }
</style>
</@layout>
<#else>
    <#include "/404.ftl"/>
</#if>