<#include "/inc/layout.ftl"/>
<@layout "激活成功" "用户激活成功" "激活成功界面，自动跳转中间页">
    <!-- 内容 -->
    <div class="main">
        <div class="container mt-5">
            <div class="jumbotron">
                <p class="lead">${msg}</p>
                <hr class="my-4">
                <p>
                    系统会在 <span id="seconds" class="text-danger">8</span> 秒后自动跳转,
                    您也可以点此 <a id="target" href="${target}" class="text-primary">链接</a>, 手动跳转!
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