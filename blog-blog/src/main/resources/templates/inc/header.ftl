<!-- Login dialog BEGIN -->
<div id="login_alert" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog login-style" role="document">
        <div class="modal-content">
            <div class="hidden">
                <img src="<@resource src=options['site_favicon']/>" alt="站点图片"/>
            </div>
            <div class="modal-body">
                <form method="POST" action="${base}/login" accept-charset="UTF-8">
                    <div class="form-group">
                        <label class="control-label" for="username">账号</label>
                        <input class="form-control" id="ajax_login_username" name="username" type="text" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password">密码</label>
                        <input class="form-control" id="ajax_login_password" name="password" type="password" required>
                    </div>
                    <div class="form-group">
                        <button id="ajax_login_submit" class="btn btn-primary btn-block btn-sm" type="button">
                            登陆
                        </button>
                    </div>
                    <div class="form-group">
                        <div id="ajax_login_message" class="text-danger"></div>
                    </div>
                    <@controls name="register">
                        <fieldset class="form-group">
                            <a href="${base}/register" class="btn btn-default btn-block">注册</a>
                            <p class="register-third-login">
                                <span>快速登陆：</span>
                                <#if site.hasValue("weibo_client_id")>
                                    <a href="${base}/oauth/callback/call_weibo" class="third-login" target="_blank">
                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/weibo.png" alt="三方图片"/>
                                    </a>
                                </#if>
                                <#if site.hasValue("qq_app_id")>
                                    <a href="${base}/oauth/callback/call_qq" class="third-login"  target="_blank">
                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/qq.png" alt="三方图片"/>
                                    </a>
                                </#if>
                                <#if site.hasValue("github_client_id")>
                                    <a href="${base}/oauth/callback/call_github" class="third-login wwwHidden" target="_blank">
                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/github.jpg" alt="三方图片"/>
                                    </a>
                                </#if>
                                <#if site.hasValue("alipay_client_id")>
                                    <a href="${base}/oauth/callback/call_alipay" class="third-login wwwHidden"  target="_blank">
                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/alipay.jpg" alt="三方图片"/>
                                    </a>
                                </#if>
                                <#if site.hasValue("gitee_client_id")>
                                    <a href="${base}/oauth/callback/call_gitee" class="third-login wwwHidden"  target="_blank">
                                        <img class="third-login-img" src="${options['site_version']}/dist/images/oauth/gitee.jpg" alt="三方图片"/>
                                    </a>
                                </#if>
                            </p>
                        </fieldset>
                    </@controls>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- Login dialog END -->

<!--[if lt IE 9]>
<div class="alert alert-danger alert-dismissible fade in" role="alert">
	<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
	<strong>您正在使用低版本浏览器，</strong> 在本页面的显示效果可能有差异。
	建议您升级到
	<a href="http://www.google.cn/intl/zh-CN/chrome/" target="_blank">Chrome</a>
	或以下浏览器：
	<a href="www.mozilla.org/en-US/firefox/‎" target="_blank">Firefox</a> /
	<a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> /
	<a href="http://www.opera.com/" target="_blank">Opera</a> /
	<a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie" target="_blank">Internet Explorer 9+</a>
</div>
<![endif]-->

<!-- Fixed navbar -->
<header class="site-header headroom">
    <div class="container">
        <nav class="navbar" role="navigation">
            <div class="navbar-header">
                <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${base}/index">
                    <img src="<@resource src=options['site_logo']/>" alt="站点图"/>
                </a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
					<#if profile??>
						<li data="user">
							<a href="${base}/users/${profile.id}" nav="user">我的主页</a>
						</li>
					</#if>
					<#list channels as row>
						<li>
							<a href="${base}/channel/${row.id}" nav="${row.name}">${row.name}</a>
						</li>
					</#list>
                        <li>
                            <a href="${base}/link" nav="link">友链</a>
                        </li>
<#--                        <li>-->
<#--                            <a href="https://github.com/SpectatorWjx" nav="link" target="_blank">GITHUB</a>-->
<#--                        </li>-->
                </ul>
                <ul class="navbar-button list-inline" id="header_user">
                    <li view="search" class="hidden-xs hidden-sm">
                        <form method="GET" action="${base}/search" accept-charset="UTF-8" class="navbar-form navbar-left">
                            <div class="form-group">
                                <input class="form-control search-input mac-style" placeholder="搜索" name="kw" type="text" value="${kw}">
                                <button class="search-btn" type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
                            </div>
                        </form>
                    </li>

				<#if profile??>
                    <@controls name="post">
                        <li>
                            <a href="${base}/post/editing" class="plus"><i class="icon icon-note"></i> 写文章</a>
                        </li>
                    </@controls>
                    <li class="dropdown">
                        <a href="#" class="user dropdown-toggle" data-toggle="dropdown">
                            <img class="img-circle" src="<@resource src=profile.avatar + '?t=' + .now?time />"  alt="头像图片" />
                            <span>${profile.name}</span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${base}/users/${profile.id}">我的主页</a>
                            </li>
                            <li>
                                <a href="${base}/settings/profile">编辑资料</a>
                            </li>
                            <li><a href="${base}/logout">退出</a></li>
                        </ul>
                    </li>
				<#else>
                    <li><a href="${base}/login" class="btn btn-default btn-sm signup">登陆</a></li>
				</#if>

                </ul>
            </div>
        </nav>
    </div>
</header>

<script type="text/javascript">
$(function () {
	$('a[nav]').each(function(){  
        $this = $(this);
        if($this[0].href == String(window.location)){  
            $this.closest('li').addClass("active");  
        }  
    });

    $(function(){
        if(window.location.host != "delpast.com"){
            $('.wwwHidden').css('display','none');
        }
    });
});
window.old_title = document.title;
</script>
<style type="text/css">
    .hidden {
        display: none;
    }
    .login-style{
        width: 400px;
    }
    .third-login{
        margin-left:15px;
    }
    .third-login-img{
        height:40px;
        width:40px;
    }
    .register-third-login{
        margin-top:10px;
    }
</style>
<!-- Header END -->
