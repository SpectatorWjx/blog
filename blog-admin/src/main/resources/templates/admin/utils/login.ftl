<#-- layout -->
<#macro layout>
<!DOCTYPE html>
<html xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="https://www.w3.org/1999/xhtml ">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>后台管理 - ${options['site_name']}</title>

    <!-- Favicons -->
    <link rel="apple-touch-icon-precomposed" href="https://blogad.delpast.com/dist/images/logo.png"/>
    <link rel="shortcut icon" href="https://blogad.delpast.com/dist/images/logo.png"/>

    <!-- Bootstrap -->
    <link href="${base}/dist/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${base}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <!-- Theme Style -->
    <link href="${base}/dist/css/site.css" rel="stylesheet">
    <link href="${base}/dist/css/site.addons.css" rel="stylesheet">
    <link href="${base}/dist/css/skins/skin-blue.css" rel="stylesheet">

    <script type="text/javascript">
        var _MTONS = _MTONS || {};
        _MTONS.BASE_PATH = '${base}';
        _MTONS.LOGIN_TOKEN = '${profile.id}';
    </script>

    <!-- jQuery -->
    <script src="${base}/dist/js/jquery.min.js"></script>
    <script src="${base}/dist/js/plugins.js"></script>
    <script src="${base}/dist/js/site.js"></script>
    <script src="${base}/dist/js/site.base.js"></script>
    <!-- Bootstrap -->
    <script src="${base}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script src='${base}/dist/vendors/jquery-validation/jquery.validate.min.js'></script>
    <script src='${base}/dist/vendors/jquery-validation/additional-methods.js'></script>
    <script src='${base}/dist/vendors/jquery-validation/localization/messages_zh.min.js'></script>
    <script src="${base}/dist/vendors/layer/layer.js"></script>
    <!-- Favicons -->
    <link href="<@resource src=options['site_favicon']/>" rel="apple-touch-icon-precomposed"/>
    <link href="<@resource src=options['site_favicon']/>" rel="shortcut icon"/>
</head>
<body>
<#nested/>
<#--    <!-- Main Footer &ndash;&gt;-->
<#--    <footer class="main-footer">-->
<#--        <!-- To the right &ndash;&gt;-->
<#--        <div class="pull-right hidden-xs">${site.version}</div>-->
<#--        <!-- Default to the left &ndash;&gt;-->
<#--        <strong>Copyright &copy; 2020 <a href="#">spectator</a>.</strong> All rights reserved.-->
<#--    </footer>-->
</body>
</html>
</#macro>