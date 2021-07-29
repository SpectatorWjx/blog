<#include "/inc/layout.ftl"/>
<@layout "友链" "友链" "博客友链列表">
    <div class="row">
        <div class="opacity-background">
            <div class="panel panel-default">
                <div class="panel-body streams-tags">
                    <div class="title">
                        <h3>小老弟</h3>
                    </div>
                    <#list favoriteLinks as favoriteLink>
                    <div class="list">
                        <ul>

                            <li><a href="${favoriteLink.linkUrl}"
                                   title="${favoriteLink.linkName}" target="_blank">${favoriteLink.linkName}</a>
                                -
                               ${favoriteLink.linkDescription}
                            </li>
                        </ul>
                    </div>
                    </#list>
                    <#if !favoriteLinks || favoriteLinks?size == 0>
                        <div class="infos">
                            <div class="media-heading">该目录下还没有内容!</div>
                        </div>
                    </#if>
                    <div class="title">
                        <h3>网站收藏</h3>
                    </div>
                    <#list recommendLinks as recommendLink>
                    <div class="list">
                        <ul>

                            <li><a href="${recommendLink.linkUrl}"
                                   title="${recommendLink.linkName}" target="_blank" data-anchor="${recommendLink.linkName}" class="a-font-color">${recommendLink.linkName}</a>
                                -
                                ${recommendLink.linkDescription}
                            </li>
                        </ul>
                    </div>
                    </#list>
                    <#if !recommendLinks || recommendLinks?size == 0>
                        <div class="infos">
                            <div class="media-heading">该目录下还没有内容!</div>
                        </div>
                    </#if>

                    <div class="title">
                        <h3>个人链接</h3>
                    </div>
                    <#list personalLinks as personalLink>
                        <div class="list">
                            <ul>

                                <li><a href="${personalLink.linkUrl}"
                                       title="${personalLink.linkName}" target="_blank" data-anchor="${personalLink.linkName}" class="a-font-color">${personalLink.linkName}</a>
                                    -
                                    ${personalLink.linkDescription}
                                </li>
                            </ul>
                        </div>
                    </#list>
                    <#if !personalLinks || personalLinks?size == 0>
                        <div class="infos">
                            <div class="media-heading">该目录下还没有内容!</div>
                        </div>
                    </#if>

                <h2 id="个人链接">链接须知</h2>
                <p>欢迎互换友链 ^_^ 不过请确定贵站可以正常运营.</p>
                <p>我的邮箱是
                    1332504948@qq.com
                    , 格式要求如下:
                </p>
                <ul>
                    <li>网站名称：spectator blog</li>
                    <li>网站链接：http://blog.delpast.com</li>
                    <li>网站描述：旁观者的个人博客</li>
                </ul>

                <p>请保证自己的链接长期有效,不然可能会被清理.</p>

                </div>
            </div>
        </div>
    </div>
    <style>
        a:link {
            color: #1d3e81; /*未访问的链接颜色*/
        }

        a:visited {
            color: #00b0e8; /*已访问的链接颜色*/
        }

        a:hover {
            color: #00b0e8; /*鼠标移动到链接的颜色*/
            text-decoration: underline;
        }

        a:active {
            color: #1d3e81; /*鼠标点击时的颜色*/
        }

        a {
            text-decoration: none; /*去掉下划线*/
        }
    </style>
</@layout>