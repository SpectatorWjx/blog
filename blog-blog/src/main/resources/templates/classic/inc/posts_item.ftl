<#macro posts_item row index escape=true>
<li class="content background-opacity leftIn delay-${index}">
    <#if row.thumbnail?? && row.thumbnail?length gt 0>
        <div class="content-box">
            <div class="posts-item posts-item-gallery">
                <h2><a href="${base}/post/${row.id}"><#if escape>${row.title?html}<#else>${row.title}</#if></a></h2>
                <div class="item-text">${row.summary}</div>
                <div class="item-info">
                    <ul>
                        <li class="post-author hidden-xs">
                            <div class="avatar">
                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50" alt="博客图片">
                            </div>
                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                        </li>
                        <li class="ico-cat"><@utils.showChannel row/></li>
                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.createTime)}</li>
                        <li class="ico-eye hidden-xs"><i class="icon-book-open"></i>${row.views}</li>
                        <li class="ico-like hidden-xs"><i class="icon-bubble"></i>${row.comments}</li>
                    </ul>
                </div>
            </div>
            <div class="posts-item-img">
                    <!-- normal -->
                    <div class="ih-item square effect6 from_top_and_bottom">
                        <a href="${base}/post/${row.id}">
                            <div class="img lazy thumbnail-img">
                                <img src="<@resource src=row.thumbnail/>" alt="img">
                            </div>
                            <div class="info">
                                <h3>阅读全文</h3>
                            </div>
                        </a>
                    </div>
            </div>
        </div>
    <#else>
        <div class="content-box posts-aside">
            <div class="posts-item">
                <div class="item-title">
                    <h2><a href="${base}/post/${row.id}"><#if escape>${row.title?html}<#else>${row.title}</#if></a></h2>
                </div>
                <div class="item-text">${row.summary}</div>
                <div class="item-info">
                    <ul>
                        <li class="post-author hidden-xs">
                            <div class="avatar">
                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50" alt="头像"/>
                            </div>
                            <a href="${base}/users/${row.author.id}" target="_blank">${row.author.name}</a>
                        </li>
                        <li class="ico-cat"><@utils.showChannel row/></li>
                        <li class="ico-time"><i class="icon-clock"></i>${timeAgo(row.createTime)}</li>
                        <li class="ico-eye hidden-xs"><i class="icon-book-open"></i>${row.views}</li>
                        <li class="ico-like hidden-xs"><i class="icon-bubble"></i>${row.comments}</li>
                    </ul>
                </div>
                <div class="item-info" style="float:right;">
                    <ul><li><a href="${base}/post/${row.id}">阅读更多</a></li></ul>
                </div>
            </div>
        </div>
    </#if>
</li>
<style type="text/css">

</style>
</#macro>