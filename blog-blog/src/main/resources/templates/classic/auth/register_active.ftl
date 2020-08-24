<#include "/classic/inc/layout.ftl"/>
<@layout "等待验证" "等待用户激活" "等待激活界面，自动跳转中间页">
    <!-- 内容 -->
    <div class="main">
        <div class="container mt-5">
            <div class="jumbotron">
                <p>
                    注册成功，邮件已发送，请注意查收。
                </p>
                <p>
                    系统会在 <span id="seconds" class="text-danger">8</span> 秒后自动跳转,
                    您也可以点此 <a id="target" href="/login" class="text-primary">链接</a>, 手动跳转!
                </p>
            </div>
        </div>
    </div>

    <script>
        $(function(){
            setInterval(function(){
                var seconds = $("#seconds").text();
                $("#seconds").text(--seconds);
                if(seconds == 0) {
                    location.href = $("#target").attr("href");
                }
            }, 1000);
        });
    </script>
</@layout>