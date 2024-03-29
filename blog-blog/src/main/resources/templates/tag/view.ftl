<#include "/inc/layout.ftl"/>

<@layout "${name} - 标签" "标签 ${name}" "网站文章的标签:${name}下的所有文章">

<div class="row streams">
    <div class="col-xs-12 col-md-9 side-left">
        <div class="posts ">
            <ul class="posts-list">
                <li class="content">
                    <div class="content-box posts-aside">
                        <div class="posts-item">标签: ${name} 共 ${results.totalElements} 个结果.</div>
                    </div>
                </li>
                <#include "/inc/posts_item.ftl"/>
                <#list results.content as row>
                    <@posts_item row.post/>
                </#list>
                <#if !results?? || results.content?size == 0>
                    <li class="content">
                        <div class="content-box posts-aside">
                            <div class="posts-item">该目录下还没有内容!</div>
                        </div>
                    </li>
                </#if>
            </ul>
        </div>
        <div class="text-center">
            <@utils.pager request.requestURI, results, 5/>
        </div>
    </div>
    <div class="col-xs-12 col-md-3 side-right">
		<#include "/inc/right.ftl" />
    </div>
</div>
</@layout>