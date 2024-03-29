<#-- Layout -->
<#macro layout_home>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    ${options['site_metas']}
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="keywords" content="${options['site_keywords']}"/>
    <meta name="description" content="网站的引导页,${options['site_description']}" />
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta property="og:title" content="随心小记" />
    <meta property="og:type" content="website" />
    <meta property="og:image" content="https://delpast.com/logo.png" />
    <meta property="og:url" content="https://delpast.com.com/" />
    <title>首页 - ${options['site_name']}</title>
    <link rel="shortcut icon" href="${options['site_version']}/dist/images/logo/m.png">
    <link rel="stylesheet" href="${options['site_version']}/dist/css/buttons.css">
    <link rel="stylesheet" id="patternfly-adjusted-css" href="${options['site_version']}/dist/css/app.css"
          type="text/css" media="all">
    <link href="${options['site_version']}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <script type="text/javascript" src="${options['site_version']}/dist/js/jquery.min.js"></script>

    <script type="text/javascript" src="${options['site_version']}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>
    <style>
        #main article { border-bottom: none; }
        body{
            font: 500 .875em PingFang SC,Lantinghei SC,Microsoft Yahei,Hiragino Sans GB,Microsoft Sans Serif,WenQuanYi Micro Hei,sans;
            background-color: #07040e;
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
<body class="home page page-id-194 page-template page-template-page-homepage page-template-page-homepage-php custom-background" ondragstart="window.event.returnValue=false" oncontextmenu="window.event.returnValue=false" onselectstart="event.returnValue=false">
<#nested>
</body>
</html>
</#macro>