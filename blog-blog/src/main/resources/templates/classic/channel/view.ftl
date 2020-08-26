<#--noinspection JSValidateTypes-->
<#if view>
<#include "/classic/inc/layout_post.ftl"/>
<#assign title = view.title />
<#assign keyword = view.title />
<#assign description = view.title+', '+view.summary />
<@layout_post title keyword description>
<div class="row main">
    <div class="col-xs-12 col-md-9 side-left topics-show">
        <!-- view show -->
        <div class="topic panel panel-default">
            <div class="infos panel-heading">
                <h1 class="panel-title topic-title">${view.title}</h1>
                <div class="meta inline-block">
                    <a class="author" href="${base}/users/${view.author.id}">
                    ${view.author.name}
                    </a>
                    <abbr class="timeago">${timeAgo(view.createTime)}</abbr>
                    <abbr>⋅ ${view.views} 阅读</abbr>
                </div>
                <div class="clearfix"></div>
            </div>

            <div class="content-body entry-content panel-body ">
                <div class="markdown-body">
                ${view.content}
                </div>
            </div>
            <div class="panel-footer operate">
                <#list view.tagsArray as tag>
                    <span>
                        <a class="label label-default" href="${base}/tag/${tag}/">#${tag}</a>
                    </span>
                </#list>
            </div>
            <div class="panel-footer">
                <div class="hidden-xs">
                    <div class="social-share" data-sites="qq, weibo, wechat, qzone, facebook, twitter, google"></div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="more-box">
                <a class="btn btn-fulltext" data-toggle="fulltext">
                    <i class="icon icon-arrow-down" aria-hidden="true"></i> 阅读全部
                </a>
            </div>
        </div>

        <!-- Comments -->
        <@controls name="comment">
        <div id="chat" class="chats shadow-box">
            <div class="chat_header">
                <h4>全部评论: <span id="chat_count">0</span> 条</h4>
            </div>
            <ul id="chat_container" class="its"></ul>
            <div id="pager" class="text-center"></div>
            <div class="chat_post">
                <div class="cbox-title">我有话说: <span id="chat_reply" style="display:none;">@<i
                        id="chat_to"></i></span>
                </div>
                <div class="cbox-post">
                    <div class="cbox-input">
                        <textarea id="chat_text" rows="3" placeholder="请输入评论内容"></textarea>
                        <input type="hidden" value="" name="chat_pid" id="chat_pid"/>
                    </div>
                    <div class="cbox-ats clearfix">
                        <div class="ats-func">
                            <div class="OwO" id="face-btn"></div>
                        </div>
                        <div class="ats-issue">
                            <button id="btn-chat" class="btn btn-success btn-sm bt">发送</button>
                        </div>
                    </div>
                </div>
                <div class="phiz-box" id="c-phiz" style="display:none">
                    <div class="phiz-list" view="c-phizs"></div>
                </div>
            </div>
        </div>
        </@controls>
        <!-- /view show -->
    </div>
    <div class="col-xs-12 col-md-3 side-right hidden-xs hidden-sm">
        <ul class="list-group about-user">
            <li class="list-group-item user-card background-opacity" >
                <div class="user-avatar">
                    <@utils.showAva view.author "img-circle"/>
                </div>
                <div class="user-name">
                    <span>${view.author.name}</span>
                </div>
                <div class="user-signature">
                    <span>${view.author.signature}</span>
                </div>
            </li>

            <li class="list-group-item background-opacity">
                <div class="user-datas">
                    <ul>
                        <li><strong>${view.author.posts}</strong><span>发布</span></li>
                        <li class="noborder"><strong>${view.author.comments}</strong><span>评论</span></li>
                    </ul>
                </div>
            </li>
            <li class="list-group-item background-opacity">
                <div class="text-center">
                    <a class="btn btn-default btn-sm" href="javascript:void(0);" data-id="${view.id}" rel="favor">
                        <i class="icon icon-star"></i> 收藏 <strong id="favors">${view.favors}</strong>
                    </a>
                </div>
            </li>
        </ul>
        <div class="opacity-background">
            <#include "/classic/inc/right.ftl"/>
        </div>
    </div>
</div>

<script type="text/plain" id="chat_template">
    <li id="chat{5}">
        <a class="avt fl" target="_blank" href="${base}/users/{0}">
            <img src="{1}" alt="image">
        </a>
        <div class="chat_body">
            <h5>
                <div class="fl"><a class="chat_name" href="${base}/users/{0}">{2}</a><span>{3}</span></div>
                <div class="fr reply_this"><a href="javascript:void(0);" onclick="goto('{5}', '{2}')"><i class="icon icon-action-redo"></i></a></div>
                <div class="clear"></div>
            </h5>
            <div class="chat_p">
                <div class="chat_pct">{4}</div> {6}
            </div>
        </div>
        <div class="clear"></div>
        <div class="chat_reply"></div>
    </li>
</script>

<script type="text/javascript">
    function goto(pid, user) {
        document.getElementById('chat_text').scrollIntoView();
        $('#chat_text').focus();
        $('#chat_text').val('');
        $('#chat_to').text(user);
        $('#chat_pid').val(pid);

        $('#chat_reply').show();
    }
    let container = $("#chat_container");
    let template = $('#chat_template')[0].text;

    seajs.use(['comment', 'view'], function (comment) {
        comment.init({
            load: '${site.controls.comment}',
            load_url: '${base}/comment/list/${view.id}',
            post_url: '${base}/comment/submit',
            toId: '${view.id}',
            onLoad: function (i, data) {
                let content = data.content;
                let quoto = '';
                if (data.pid !=='' && data.parent !== null) {
                    let pat = data.parent;
                    let pcontent = pat.content;
                    quoto = '<div class="quote"><a href="${base}/users/' + pat.author.id + '">@' + pat.author.name + '</a>: ' + pcontent + '</div>';
                }
                let item = jQuery.format(template,
                        data.author.id,
                        data.author.avatar,
                        data.author.name,
                        data.createTime,
                        content,
                        data.id, quoto);
                return item;
            }
        });
    });


    addCodeBtns();

    /*复制添加来源*/
    function setClipboardText(event){
        event.preventDefault();//阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
        let selection = window.getSelection();
        let node = document.createElement('div');
        node.appendChild(selection.getRangeAt(0).cloneContents());
        let htmlData = "<div>" + node.innerHTML + "</div>";
        let textData = selection.getRangeAt(0).toString();

        //如果选择是复制是低于200个字符的内容，是不会带小尾巴的
        if (("" + selection).length > 200){
            htmlData = "<div>"
                + node.innerHTML
                + "<br /><br /><br />版权声明：本文出自博客${options['site_name']}的文章-${view.title}，转载请附上原文出处链接及本声明。<br />"
                + "来源：<a href='"+document.location.href+"'>" + document.location.href + "</a><br />"
                + "</div>";
            textData = selection.getRangeAt(0).toString()
            + "\n\n\n版权声明：本文出自博客${options['site_name']}的文章-${view.title}，转载请附上原文出处链接及本声明。\n"
            + "来源：" + document.location.href;
        }
        if(event.clipboardData){
            event.clipboardData.setData("text/html", htmlData);
            event.clipboardData.setData("text/plain",textData);
        } else if(window.clipboardData){
            return window.clipboardData.setData("text", textData);
        }
    };
    document.addEventListener('copy',function(e){
        setClipboardText(e);
        Toast("复制成功",1500);
    });

    function addCodeBtns() {
        let pres = $('.main pre');
        if (pres) {
            $('pre').wrap("<div class='pre_div'></div>");
            let sel_code = "<button class='sel_code' type='button' aria-label='复制代码'><i aria-label='icon: copy' class='anticon anticon-copy'><svg viewBox='64 64 896 896' focusable='false' class='' data-icon='copy' width='1em' height='1em' fill='currentColor' aria-hidden='true'><path d='M832 64H296c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h496v688c0 4.4 3.6 8 8 8h56c4.4 0 8-3.6 8-8V96c0-17.7-14.3-32-32-32zM704 192H192c-17.7 0-32 14.3-32 32v530.7c0 8.5 3.4 16.6 9.4 22.6l173.3 173.3c2.2 2.2 4.7 4 7.4 5.5v1.9h4.2c3.5 1.3 7.2 2 11 2H704c17.7 0 32-14.3 32-32V224c0-17.7-14.3-32-32-32zM350 856.2L263.9 770H350v86.2zM664 888H414V746c0-22.1-17.9-40-40-40H232V264h432v624z'></path></svg></i></button>";
            $('.pre_div').prepend(sel_code);
            //添加复制按钮
            $(".sel_code").click(function() {
                const range = document.createRange();
                range.selectNode($(this).siblings()[0]);
                const selection = window.getSelection();
                if (selection.rangeCount > 0) {
                    selection.removeAllRanges();
                }
                selection.addRange(range);
                document.execCommand('copy');
                selection.removeAllRanges();
            })
        }
    }
    function Toast(msg, duration) {
        duration = isNaN(duration) ? 3000 : duration;
        let m = document.createElement('div');
        m.innerHTML = msg;
        m.style.cssText = "max-width:60%;min-width: 150px;padding:0 14px;height: 40px;color: rgb(255, 255, 255);line-height: 40px;text-align: center;border-radius: 4px;position: fixed;top: 50%;left: 50%;transform: translate(-50%, -50%);z-index: 999999;background: rgba(0, 0, 0,.7);font-size: 16px;";
        document.body.appendChild(m);
        setTimeout(function () {
            let d = 0.5;
            m.style.webkitTransition = '-webkit-transform ' + d + 's ease-in, opacity ' + d + 's ease-in';
            m.style.opacity = '0';
            setTimeout(function () {
                document.body.removeChild(m)
            }, d * 1000);
        }, duration);
    }

    $(function(){
        $(".pre_div").hover(function(){
            $(this).children(".sel_code").css("display", "block");
        },function(){
            $(this).children(".sel_code").css("display", "none");
        });
    });
</script>

<style>
    pre{
        width: 100%;
    }
    .pre_div{
        position:relative;
        display: flex;
        align-items: center;
    }
    .sel_code{
        display: none;
        position: absolute;
        top: 0;
        right: 0;
        opacity: 0.8;
        z-index: 1;
    }
</style>
</@layout_post>
<#else>
    <#include "/classic/404.ftl"/>
</#if>