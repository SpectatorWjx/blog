<#-- Layout -->
<#macro layout title keywords description>
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
    <script type='text/javascript'>
        jQuery(document).ready(function() {
            jQuery('.fsb-slider').fsbslider({"animation_time":100,"animation_type":"crossfade","pattern":false});
        });
    </script>
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
<ul class="fsb-slider">
    <#list options['site_background']?split(",") as url>
        <li>
            <span>
                <img src="${url}" alt="img1">
            </span>
        </li>
    </#list>
</ul>
    <!-- header -->
    <#include "/inc/header.ftl"/>
    <!-- /header -->
    <!-- content -->
    <div class="wrap">
        <!-- Main -->
        <div class="container">
            <#nested>
        </div>
    </div>
    <!-- /content -->
    <!-- footer -->
    <#include "/inc/footer.ftl"/>
    <!-- footer -->

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

        // 背景图轮换
        eval(function(p, a, c, k, e, d) {
            e = function(c) {
                return (c < a ? '' : e(parseInt(c / a))) + ((c = c % a) > 35 ? String.fromCharCode(c + 29) : c.toString(36))
            };
            if (!''.replace(/^/, String)) {
                while (c--) {
                    d[e(c)] = k[c] || e(c)
                }
                k = [function(e) {
                    return d[e]
                }];
                e = function() {
                    return '\\w+'
                };
                c = 1
            };
            while (c--) {
                if (k[c]) {
                    p = p.replace(new RegExp('\\b' + e(c) + '\\b', 'g'), k[c])
                }
            }
            return p
        }('(G(s){M r={2b:G(b){M q="21 1S 1I 1G 1M 1L 1Q 1P 1O 1N 20 28 2d 2e 2c 2a".2z(" "),h=3(t);3(\'<2x 2u="1F" />\').2t().1d(3(t).1d()).2s("2r");1x(2v.2w){M p=b.U;"1w"==b.U&&(p=q[1f.1y(1f.1z()*q.K)]);3(h).u().u().z({"-1n-I-1q":J(b.13)+"s","-1o-I-1q":J(b.13)+"s","-o-I-1q":J(b.13)+"s","-1g-I-1q":J(b.13)+"s","I-1q":J(b.13)+"s"});3(t).u().1H(G(a){"1w"==b.U&&"1w"==b.U&&(p=q[1f.1y(1f.1z()*q.K)]);3(t).u(1).z("1D-1E","24("+3(t).u(1).u().1v("1e")+")");M c=a*(b.13/3(h).u().K);3(t).u(0).z({"-1n-I-1r":p,"-1o-I-1r":p,"-o-I-1r":p,"-1g-I-1r":p,"I-1r":p,"-1n-I-1k-1j":"1p","-1o-I-1k-1j":"1p","-o-I-1k-1j":"1p","-1g-I-1k-1j":"1p","I-1k-1j":"1p","-1n-I-H":c+"s","-1o-I-H":c+"s","-o-I-H":c+"s","-1g-I-H":c+"s","I-H":c+"s","-1n-I-1i-1h":"1m","-1o-I-1i-1h":"1m","-o-I-1i-1h":"1m","-1g-I-1i-1h":"1m","I-1i-1h":"1m"});3(t).u(0).1d("1Y 1U "+(a+1))})}29{M m=1,l=G(){M k=b.U;3(h).u().1H(G(c){"1w"==b.U&&(k=q[1f.1y(1f.1z()*q.K)]);"25"==3(t).u(1).z("1D-1E")&&(3(t).u(1).z("1D-1E","24("+3(t).u(1).u().1v("1e")+")"),3(t).u(1).z("1X","22:1J.1K.1R(1e=\'"+3(t).u(1).u().1v("1e")+"\',23=\'1Z\');"),3(t).u(1).z("-1g-1X","22:1J.1K.1R(1e=\'"+3(t).u(1).u().1v("1e")+"\',23=\'1Z\');"),3(t).u(0).1d("1Y 1U "+(c+1)));"2G"==k&&(3(t).u(0).z("w","A").z("v","A").z("j",3(9).j()+"6").z("i",3(9).i()+"6"),3(t).u(0).H(c*n).D(f,0.2),3(t).u(0).H(c*n).x({w:"-14",v:"-1A",j:1.1c*3(t).u(0).j()+"6",i:1.1c*3(9).i()+"6"},f,"y",G(){3(t).u(0).D(a,0.2);3(t).u(0).x({w:"-L",v:"-L",j:1.1*3(t).u(0).j()+"6",i:1.1*3(9).i()+"6"},a,"y")}),3(t).u(0).H(f).D(d,0.7),3(t).u(0).H(f).x({w:"-14",v:"-14",j:1.2*3(t).u(0).j()+"6",i:1.2*3(9).i()+"6"},d,"y",G(){}),c==J(3(h).u().K-1)?(3(t).u(0).H(f).x({w:"-1V",v:"-1T",j:1.1*3(t).u(0).j()+"6",i:1.1*3(9).i()+"6"},d,"y",G(){3(t).u().u().1s().z("w","A").z("v","A").z("j",3(9).j()+"6").z("i",3(9).i()+"6");3(t).u().u().1s().D(f,0.2)}),3(t).u(0).H(a).1t(e,G(){l()})):(3(t).u(0).H(f).x({w:"-1T",v:"-1V",j:1.1W*3(t).u(0).j()+"6",i:1.1W*3(9).i()+"6"},d,"y",G(){}),3(t).u(0).H(a).1t(e)));1x("2H"==k||"2D"==k){M g=0<c?c*(n+f+a):0;1<m&&0==c?(3(t).u(0).D(a,0.2),3(t).u(0).x({w:"-L",v:"-L",j:1.1*3(9).j()+"6",i:1.1*3(9).i()+"6"},a,"y")):(3(t).u(0).z("w",3(9).i()/4+"6").z("v",3(9).j()/4+"6").z("j",3(9).j()/2+"6").z("i",3(9).i()/2+"6"),3(t).u(0).H(c*n+g).D(f,0.2).x({w:"-14",v:"-1A",j:1.1c*3(9).j()+"6",i:1.1c*3(9).i()+"6"},f,"y",G(){3(t).u(0).D(a,0.2);3(t).u(0).x({w:"-L",v:"-L",j:1.1*3(9).j()+"6",i:1.1*3(9).i()+"6"},a,"y",G(){})}));3(t).u(0).H(f).D(d,0.7);3(t).u(0).H(f).x({w:"-14",v:"-14",j:1.2*3(9).j()+"6",i:1.2*3(9).i()+"6"},d,"y",G(){});c==J(3(h).u().K-1)?(3(t).u(0).H(f).D(a,0.2),3(t).u(0).H(f).x({w:3(9).i()/4+"6",v:3(9).j()/4+"6",j:3(9).j()/2+"6",i:3(9).i()/2+"6"},d,"y",G(){}),3(t).u(0).H(a).1t(a,G(){l()})):(3(t).u(0).H(f).x({w:3(9).i()/4+"6",v:3(9).j()/4+"6",j:3(9).j()/2+"6",i:3(9).i()/2+"6"},d,"y",G(){}),3(t).u(0).H(a).1t(a,G(){c==J(3(h).u().K-2)&&(3(t).u().u().1s().z("w",3(9).i()/4+"6").z("v",3(9).j()/4+"6").z("j",3(9).j()/2+"6").z("i",3(9).i()/2+"6"),3(t).u().u().1s().D(f,0.2).x({w:"-14",v:"-1A",j:1.1c*3(9).j()+"6",i:1.1c*3(9).i()+"6"},f,"y",G(){3(t).u(0).D(a,0.2);3(t).u(0).x({w:"-L",v:"-L",j:1.1*3(9).j()+"6",i:1.1*3(9).i()+"6"},a,"y")}))}))}"21"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",3(9).i()/8+"6").z("v","-"+3(t).j()+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({v:3(9).j()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({v:3(9).j()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1S"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",3(9).i()/8+"6").z("v","-"+3(t).j()+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({v:3(9).j()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({v:"-"+3(9).j()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1I"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",3(9).i()/8+"6").z("v","-"+3(t).j()+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({v:3(9).j()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({w:"-"+3(9).i()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1G"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",3(9).i()/8+"6").z("v","-"+3(t).j()+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({v:3(9).j()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({w:2*3(9).i()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1L"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",3(9).i()/8+"6").z("v",1.5*3(t).j()+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({v:3(9).j()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({v:1.5*3(9).j()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1M"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",3(9).i()/8+"6").z("v",1.5*3(t).j()+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({v:3(9).j()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({v:"-"+3(9).j()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1Q"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",3(9).i()/8+"6").z("v",1.5*3(t).j()+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({v:3(9).j()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({w:"-"+3(9).i()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1P"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",3(9).i()/8+"6").z("v",1.5*3(t).j()+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({v:3(9).j()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({w:2*3(9).i()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1O"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w","-"+3(9).i()+"6").z("v",3(t).j()/8+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({w:3(9).i()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({w:"-"+3(9).i()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"1N"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w","-"+3(9).i()+"6").z("v",3(t).j()/8+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({w:3(9).i()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({w:2*3(9).i()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"20"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w","-"+3(9).i()+"6").z("v",3(t).j()/8+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({w:3(9).i()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({v:"-"+3(9).j()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"28"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w","-"+3(9).i()+"6").z("v",3(t).j()/8+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({w:3(9).i()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({v:2*3(9).j()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"2e"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",2*3(9).i()+"6").z("v",3(t).j()/8+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({w:3(9).i()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({w:2*3(9).i()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"2d"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",2*3(9).i()+"6").z("v",3(t).j()/8+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({w:3(9).i()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({w:"-"+3(9).i()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"2c"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",2*3(9).i()+"6").z("v",3(t).j()/8+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({w:3(9).i()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({v:"-"+3(9).j()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));"2a"==k&&(g=2*(a+d+e),1<m&&0==c&&(g=0),3(t).u(0).z("w",2*3(9).i()+"6").z("v",3(t).j()/8+"6").z("j",0.C*3(t).j()+"6").z("i",0.C*3(9).i()+"6"),3(t).u(0).H(g*c).D(f,0.2).x({w:3(9).i()/8+"6"},f,"y").x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},a,"y").D(a,0.7).x({v:"A",w:"A",j:3(9).j()+"6",i:3(9).i()+"6"},d,"y").D(d,0.2).x({w:3(9).i()/8+"6",v:3(9).j()/8+"6",j:0.C*3(9).j()+"6",i:0.C*3(9).i()+"6"},e,"y").x({v:2*3(9).j()+"6"},e,"y",G(){c==J(3(h).u().K-1)&&l()}));m++})};3(t).u().u().z("2F","25");3(t).u(0).z("w","A").z("v","A").z("j",3(9).j()+"6").z("i",3(9).i()+"6");M n=2E*b.13/3(h).u().K,f=n/1u*12,a=n/1u*8,d=n/1u*16,e=n/1u*12;l(b.U,n)}3(t).B("1l").B("E").B("V").B("W").B("T").B("Y").B("R").B("N").B("S").B("O").B("Q").B("P").B("X").B("15").B("18").B("19").B("1a").B("1b").B("17").B("Z").B("10").B("11");!1==b.E?3(t).F("1l"):"E"==b.E?3(t).F("E"):"V"==b.E?3(t).F("V"):"W"==b.E?3(t).F("W"):"T"==b.E?3(t).F("T"):"Y"==b.E?3(t).F("Y"):"R"==b.E?3(t).F("R"):"N"==b.E?3(t).F("N"):"S"==b.E?3(t).F("S"):"O"==b.E?3(t).F("O"):"Q"==b.E?3(t).F("Q"):"P"==b.E?3(t).F("P"):"X"==b.E?3(t).F("X"):"15"==b.E?3(t).F("15"):"18"==b.E?3(t).F("18"):"19"==b.E?3(t).F("19"):"1a"==b.E?3(t).F("1a"):"1b"==b.E?3(t).F("1b"):"17"==b.E?3(t).F("17"):"Z"==b.E?3(t).F("Z"):"10"==b.E?3(t).F("10"):"11"==b.E&&3(t).F("11")},2I:G(){3(t).1d(3("#1F").1d());3("#1F").2J();3(t).B("E").B("V").B("W").B("T").B("Y").B("R").B("N").B("S").B("O").B("Q").B("P").B("X").B("15").B("18").B("19").B("1a").B("1b").B("17").B("Z").B("10").B("11").F("1l");1B=2C;1C 1},2B:G(b){3(t).B("1l").B("E").B("V").B("W").B("T").B("Y").B("R").B("N").B("S").B("O").B("Q").B("P").B("X").B("15").B("18").B("19").B("1a").B("1b").B("17").B("Z").B("10").B("11");"2l"==b.E?3(t).F("1l"):"E"==b.E?3(t).F("E"):"V"==b.E?3(t).F("V"):"W"==b.E?3(t).F("W"):"T"==b.E?3(t).F("T"):"Y"==b.E?3(t).F("Y"):"R"==b.E?3(t).F("R"):"N"==b.E?3(t).F("N"):"S"==b.E?3(t).F("S"):"O"==b.E?3(t).F("O"):"Q"==b.E?3(t).F("Q"):"P"==b.E?3(t).F("P"):"X"==b.E?3(t).F("X"):"15"==b.E?3(t).F("15"):"18"==b.E?3(t).F("18"):"19"==b.E?3(t).F("19"):"1a"==b.E?3(t).F("1a"):"1b"==b.E?3(t).F("1b"):"17"==b.E?3(t).F("17"):"Z"==b.E?3(t).F("Z"):"10"==b.E?3(t).F("10"):"11"==b.E&&3(t).F("11")}};s.2k.1B=G(b){1x(r[b])1C r[b].27(t,2m.2n.2o.2j(26,1));1x("2f"!==2g b&&b)s.2i("2h "+b+" 2p 2A 2q 2y 3.1B");29 1C r.2b.27(t,26)}})(3);', 62, 170, '|||jQuery|||px|||window|||||||||height|width||||||||||this|children|left|top|animate|easeInOutQuad|css|0px|removeClass|75|fadeTo|pattern|addClass|function|delay|animation|parseInt|length|1px|var|pattern6|pattern8|pattern10|pattern9|pattern5|pattern7|pattern3|animation_type|pattern1|pattern2|pattern11|pattern4|pattern18|pattern19|pattern20||animation_time|5px|pattern12||pattern17|pattern13|pattern14|pattern15|pattern16|05|html|src|Math|ms|count|iteration|mode|fill|hidepattern|infinite|webkit|moz|forwards|duration|name|first|fadeOut|100|attr|randomslide|if|floor|random|15px|fsbslider|return|background|image|hidden_fsbs|slidelefttobottom|each|slidelefttotop|DXImageTransform|Microsoft|sliderighttoright|sliderighttoleft|slidetoptobottom|slidetoptotop|sliderighttobottom|sliderighttotop|AlphaImageLoader|slidelefttoleft|3px|Image|7px|07|filter|Slider|scale|slidetoptoleft|slidelefttoright|progid|sizingMethod|url|none|arguments|apply|slidetoptoright|else|slidebottomtoright|init|slidebottomtoleft|slidebottomtotop|slidebottomtobottom|object|typeof|Method|error|call|fn|disable|Array|prototype|slice|does|exist|body|appendTo|hide|id|Modernizr|csstransitions|div|on|split|not|updatepattern|null|rotatefade|1E3|display|crossfade|slidefade|destroy|remove'.split('|'), 0, {}))
    </script>
</body>
<script src="https://live2d.delpast.com/autoload.js" type="text/javascript"></script>
</html>
</#macro>