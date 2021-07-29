<#include "/inc/layout.ftl"/>
<@layout "首页" "首页" "博客的首页">
    <h1 class="hidden">博客首页</h1>
    <div class="row">
        <div class="col-xs-12 col-md-9 side-left">
            <div id="modelSlider" class="modelSlider"></div>
            <div class="posts">
                <@contents pageNo=pageNo>
                <ul class="posts-list aninode">
                    <#include "/inc/posts_item.ftl"/>
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
                </@contents>
            </div>
            <div class="text-center">
                <!-- Pager -->
                <@utils.pager request.requestURI!"", results, 5/>
            </div>
        </div>
        <div class="col-xs-12 col-md-3 side-right opacity-background">
            <div class="aninode">
                <#include "/inc/right.ftl"/>
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
        };
        $("#modelSlider").slider({
            imgs: "${options['site_banner']}".split(','),
            scale: 120 / 40,
            border: false,   //是否显示分界线
            showBar: true,    //是否可以人工切换  如果只保留一种切换方式，目前只找到一种方法，把源码中的effects数组里面的不需要的方法注释掉。。。应该有其他方法
            x: 7,      //横向格子数
            y: 3,      //纵向格子数
            interval:3000
        });
    </script>
</@layout>