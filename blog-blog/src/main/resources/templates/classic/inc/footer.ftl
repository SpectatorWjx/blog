<footer class="footer opacity-background">
    <div class="container">
        <div class="footer-row">
            <nav class="footer-nav">
                <a class="footer-nav-item footer-nav-logo" href="${base}/">
                    <img src="<@resource src=options['site_logo']/>" alt="myBlog"/>
                </a>
                <span class="footer-nav-item">${options['site_copyright']}</span>
                <span class="footer-nav-item"><a href="http://www.beian.miit.gov.cn">${options['site_icp']}</a></span>
            </nav>
            <div class="gh-foot-min-back hidden-xs hidden-sm">
                <!-- 请保留此处标识-->
                <span class="footer-nav-item">Powered by <a href="https://github.com/SpectatorWjx" target="_blank">myBlog</a></span>
<#--                <a href="https://www.upyun.com/?utm_source=lianmeng&utm_medium=referral" target="_blank" data-link="my">本网站由<img src="../dist/images/logo/upyun_logo.png" style="height: 20px;width: 40px">提供CDN加速/云存储服务</a>-->
            </div>
        </div>
    </div>
</footer>

<a href="#" class="site-scroll-top">
    <i class="icon-arrow-up"></i>
</a>

<script type="text/javascript">
    seajs.use('main', function (main) {
        main.init();
    });

    //切换title
    let titleTime;
    document.addEventListener('visibilitychange', function() {
        let isHidden = document.hidden;
        if (isHidden) {
            document.title = 'Σ(っ °Д °;)っ，崩溃啦~';
            clearTimeout(titleTime);
        } else {
            document.title = '୧(๑•̀⌄•́๑)૭， 欢迎回来';
            titleTime = setTimeout(function() {
                document.title = window.old_title;
            }, 1000);
        }
    });
</script>