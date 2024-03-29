<#-- Layout -->
<#macro layout_post title keywords description>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    ${options['site_metas']}
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!--[if IE]>
    <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'/>
    <![endif]-->
    <meta name="robots" content="index,follow"/>
    <meta name="keywords" content="${keywords},${options['site_keywords']}" />
    <meta name="description" content="${description},${options['site_description']}" />
    <meta name="spectator:myBlog" content="${site.version}" />

    <title>${title} - ${options['site_name']}</title>
    <link href="${options['site_version']}/dist/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${options['site_version']}/dist/css/editor.css" rel="stylesheet"/>
    <link href="${options['site_version']}/dist/css/plugins.css" rel="stylesheet"/>
    <link href="${options['site_version']}/dist/css/full_screen_background_slider.css" rel="stylesheet"/>
    <link href="${options['site_version']}/dist/css/style.css" rel="stylesheet"/>
    <link href="${options['site_version']}/dist/vendors/simple-line-icons/css/simple-line-icons.css" rel="stylesheet"/>
    <link href="${options['site_version']}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="<@resource src=options['site_favicon']/>" rel="apple-touch-icon-precomposed" />
    <link href="<@resource src=options['site_favicon']/>" rel="shortcut icon" />

    <script src="${options['site_version']}/dist/js/jquery.min.js"></script>
    <script src="${options['site_version']}/dist/vendors/layer/layer.js"></script>
    <script src="${options['site_version']}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        var _MTONS = _MTONS || {};
        _MTONS.BASE_PATH = '${base}';
        _MTONS.LOGIN_TOKEN = '${profile.id}';
    </script>
    <script src="${options['site_version']}/dist/js/sea.js"></script>
    <script src="${options['site_version']}/dist/js/sea.config.js"></script>
    <script src="${options['site_version']}/dist/js/modernizr.custom.js"></script>
    <script src="${options['site_version']}/dist/js/slider.js"></script>
    <script src="${options['site_version']}/dist/js/rainyday.js"></script>
    <script type='text/javascript'>
        function run() {
            let image = document.getElementById('background_img');
            let canvas = document.getElementById('canvas');
            image.onload = function() {
                let engine = new RainyDay({
                    image:this,
                    parentElement:canvas
                });
                engine.rain([ [3, 2, 2] ], 100);
            };
            image.crossOrigin = 'anonymous';
        }
    </script>
    <style>
        #background {
            position: fixed;
            width: 100%;
            height: 100%;
        }
        #background_img{
            width: 100%;
            height: 100%;
        }
        #canvas {
            position:absolute;
            width: 100%;
        }
        #body_content{
            position: absolute;
            width: 100%;
        }
    </style>
    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?325248498dda9db1788966d22610d24d";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</head>
<body>
<div id="background">
    <div id="canvas">
        <#assign background_url="${options['site_post_background']}"?split(',')>
        <img id="background_img" src="${background_url[0]}" alt="img" onload="run()">
    </div>
</div>
<div id="body_content">
    <#include "/inc/header.ftl"/>
    <div class="wrap">
        <div class="container">
            <#nested>
        </div>
    </div>
    <#include "/inc/footer.ftl"/>
</div>
</body>
<script type="text/javascript">
    /* 鼠标点击特效 */
    let a_idx = 0;
    jQuery(document).ready(function($) {
        $("body").click(function(e) {
            let a = ["富强", "民主", "文明", "和谐", "自由", "平等", "公正" ,"法治", "爱国", "敬业", "诚信", "友善"];
            let $i = $("<span/>").text(a[a_idx]);
            a_idx = (a_idx + 1) % a.length;
            let x = e.pageX,
                y = e.pageY;
            $i.css({
                "z-index": 999999999999999999999999999999999999999999999999999999999999999999999,
                "top": y - 20,
                "left": x,
                "position": "absolute",
                "font-weight": "bold",
                "color": "#ff6651"
            });
            $("body").append($i);
            $i.animate({
                    "top": y - 180,
                    "opacity": 0
                },
                1500,
                function() {
                    $i.remove();
                });
        });
    });
</script>
<script src="https://live2d.delpast.com/autoload.js" type="text/javascript"></script>
</html>
</#macro>