<#include "/admin/utils/login.ftl"/>
<@layout>
    <div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">后台登陆</h3>
            </div>
            <div class="panel-body">
                <form method="POST" action="login" id="login_Form" accept-charset="UTF-8">
                    <div class="form-group">
                        <label class="control-label" for="username">账号</label>
                        <input class="form-control" name="username" type="text" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password">密码</label>
                        <input class="form-control" name="password" type="password" required>
                    </div>
                    <div class="form-group">
                        <button type="submit" id ="submit" class="btn btn-primary btn-block">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div style="width: 100%; bottom: 0px;box-sizing: border-box;position: absolute;text-align:center">© 2019-2021  随心小记 <a href="http://www.beian.miit.gov.cn">黑ICP备17009694号-2 </a>All Rights Reserved.
    <div class="gh-foot-min-back hidden-xs hidden-sm">
        <!-- 请保留此处标识-->
        <span class="footer-nav-item">Powered by <a href="https://github.com/SpectatorWjx" target="_blank">myBlog</a></span>
    </div>
</div>
</@layout>

