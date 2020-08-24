<#include "/classic/inc/layout.ftl"/>
<@layout channel.name channel.name channel.name>
    <h1 class="hidden">${channel.name}</h1>
    <div class="row">
        <div class="col-xs-12 col-md-9 side-left">
            <@contents channelId=channel.id pageNo=pageNo order=order>
                <div class="posts">
                    <ul class="posts-list aninode">
                        <#include "/classic/inc/posts_item.ftl"/>
                        <#list results.content as row>
                            <@posts_item row row_index/>
                        </#list>
                        <#if  results.content?size == 0>
                        <li class="content">
                            <div class="content-box posts-aside">
                                <div class="posts-item">该目录下还没有内容!</div>
                            </div>
                        </li>
                        </#if>
                    </ul>
                </div>
                <!-- Pager -->
                <div class="text-center">
                    <@utils.pager request.requestURI!"", results, 5/>
                </div>
            </@contents>
        </div>

        <div class="col-xs-12 col-md-3 side-right opacity-background">
            <div class="aninode">
                <#include "/classic/inc/right.ftl" />
            </div>
        </div>
    </div>
    <script type="text/javascript">
        let posts = document.querySelector('.posts');
        let right_side = document.querySelector('.side-right');
        window.onload = function() {
            if(posts) {
                posts.classList.add('animated');
            }
            if(right_side){
                right_side.classList.add('animated');
            }
        }
    </script>
</@layout>

