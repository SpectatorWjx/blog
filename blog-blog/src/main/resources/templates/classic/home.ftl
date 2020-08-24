<#include "/classic/inc/layout_home.ftl"/>
<@layout_home>
  <header role="banner">
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
              <span class="icon-bar">
              </span>
            <span class="icon-bar">
              </span>
            <span class="icon-bar">
              </span>
          </button>
          <a class="navbar-brand" id="logo" title="HY-PHP" href=""  rel="nofollow">
            <h1>随心小记</h1>
          </a>
        </div>
        <!-- end .navbar-header -->
        <div class="navbar-collapse collapse">
          <ul id="menu-primary" class="nav navbar-nav navbar-right">

            <li class="menu-item menu-item-type-post_type menu-item-object-page menu-item-has-children dropdown">
              <a href="https://www.cnblogs.com/spectatorwjx" class="dropdown-toggle" target="_blank"  rel="nofollow" >
                <h4>博客园</h4>
              </a>
            </li>
            <li class="menu-item menu-item-type-post_type menu-item-object-page menu-item-has-children dropdown">
              <a href="https://github.com/SpectatorWjx" class="dropdown-toggle" target="_blank"  rel="nofollow" >
                <h4>GITHUB</h4>
              </a>
            </li>
            <li class="menu-item menu-item-type-post_type menu-item-object-page menu-item-has-children dropdown">
              <a href="https://gitee.com/spectatorwjx" class="dropdown-toggle" target="_blank"  rel="nofollow" >
                <h4>码云</h4>
              </a>
            </li>
            <li class="menu-item menu-item-type-post_type menu-item-object-page menu-item-has-children dropdown">
              <a href="tencent://AddContact/?fromId=50&fromSubId=1&subcmd=all&uin=1332504948" class="dropdown-toggle"  rel="nofollow" >
                <h4>联系博主</h4>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
  <div class="jumbotron">
    <div class="container-size">
      <div class="splash">
        <div class="content">
          <div class="content-middle">
            <img src="https://q.qlogo.cn/headimg_dl?dst_uin=2637376843&spec=640" alt="qq头像" id="qq" class="wow fadeInDown animated head-img">
            <h2 class="wow fadeIn animated site-name" data-wow-delay="750ms">
              ${options['site_name']}
            </h2>
            <p class="description wow fadeIn animated user-name" data-wow-delay="1250ms">
              <h5>旁观者</h5>
            </p>
            <p>
              <a href="/index" class="button button-glow button-border button-rounded button-primary"  rel="nofollow" ><i class="fa fa-vimeo-square"></i>进入主页</a>&nbsp&nbsp&nbsp&nbsp
            </p>
            <p hidden>
              本站是一个博客网站，网站名称：delpast，域名备案名为随心小记，本站主要用于博文记录，包括编程相关的技术知识和问题记录，或者分享一些个人故事心得，天下皆白，唯我独黑,做一个旁观者。
              作为本站的开发者，希望将本站做成开放式的，对外开放注册，支持注册用户发表文章（最好是原创文章）或者对他人文章发表评论，后续旁观者也会持续发表文章，丰富本站内容。同时，本站欢迎其他网站互为友链。
            </p>
            <p  hidden>此放彼放，此方彼方，此岸彼岸，此生彼生，确实是真的放下了。</p>
            <p hidden>为天地立心,为生民立命,为往圣继绝学,为万世开太平! ----张载</p>
            <p  hidden>读书人识字越多，认得历史越多，心思就难免越重。才学越高，往往分寸感越弱，不喜欢拿捏火候。准确来说，是不屑，懒得与人与事去虚与委蛇。看人和做事就容易非黑即可，也就是你所谓的意气用事了。</p>
          </div>
          <div class="home-bottom">
            <div>
              <span class="footer-nav-item">© 2019-2021  随心小记 <a href="http://www.beian.miit.gov.cn"  rel="nofollow" >黑ICP备17009694号-2 </a>All Rights Reserved.</span>
            </div>
            <div class="gh-foot-min-back hidden-xs hidden-sm">
              <!-- 请保留此处标识-->
              <span class="footer-nav-item">Powered by <a href="" target="_blank">myBlog</a></span>
              <span class="footer-nav-item">眼前的黑不是黑，你说的白是什么白</span>
            </div>
          </div>
        </div>

      </div>
    </div>
    <canvas id="canvas"></canvas>
  </div>
  <script src="/dist/js/hovertreewelcome.js"></script>
  <script src="/dist/js/canvas.js"></script>
  <style>
    #canvas {
      position: fixed;
      z-index: 10;
      top: 0;
      left: 0;
      bottom: 0;
      right: 0;
      cursor: none;
    }
    .content{
      position: absolute;
      z-index: 11;
      right: 0;
      left: 0;
      top: 0;
      bottom: 0;
    }
    .splash{
      position: relative;
    }
    img#qq {
      width: 128px;
      background-size: cover;
      border-radius: 200px;
      box-shadow: 0px 0px 40px rgba(63, 81, 181, 0.72);
      border: 3px solid #00a0ff;
      opacity: 1;
      margin: 0 auto;
      margin-top: 100px;
      margin-bottom: 20px;
      transition: all 1.0s;
    }
    #qq:hover {
      box-shadow: 0 0 10px #fff;
      -webkit-box-shadow: 0 0 19px #fff;
      transform:rotate(360deg);
      -ms-transform:rotate(360deg); 	/* IE 9 */
      -moz-transform:rotate(360deg); 	/* Firefox */
      -webkit-transform:rotate(360deg); /* Safari 和 Chrome */
      -o-transform:rotate(360deg); 	/* Opera */
      filter:progid:DXImageTransform.Microsoft.BasicImage(rotation=3);
    }
    .home-bottom{
      width: 100%;
      bottom: 0;
      box-sizing: border-box;
      position: absolute;
      text-align: center;
      margin: auto;
    }
    .container-size{
      height: 800px;
      bottom: 0px;
    }
    .head-img{
      visibility: visible;
      animation-name: fadeInDown;
    }
    .site-name{
      visibility: visible;
      animation-delay: 750ms;
      animation-name: fadeIn;
    }
    .user-name{
      visibility: visible;
      animation-delay: 1250ms;
      animation-name: fadeIn;
    }
    .content-middle {
      height: 90%;
    }
  </style>
  <script>
    canvas(window);
  </script>
</@layout_home>