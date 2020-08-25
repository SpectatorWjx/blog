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