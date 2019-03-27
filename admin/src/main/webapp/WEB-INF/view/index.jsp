<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>运营平台管理系统</title>
    <meta name="apple-mobile-web-app-title" content="基础平台－后台管理"/>
    <%@ include file="include/head.jsp" %>
    <link rel="stylesheet" href="${ctxAssets}/3rd-lib/jquery-ztree/3.5/css/zTreeStyle.css">
    <link rel="stylesheet" href="${ctxAssets}/custom/css/index.css">
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，平台暂不支持。 请 <a href="http://browsehappy.com/"
                                                                 target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
<header class="am-topbar am-topbar-inverse admin-header">
    <div class="am-topbar-brand">
        <a href="${ctx}">
            <small>运营平台管理系统 v1.0</small>
        </a>
    </div>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only"
            data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span
            class="am-icon-bars"></span></button>
    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    <span class="am-icon-users"></span> <shiro:principal/> <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <!-- <li><a href="#"><span class="am-icon-user"></span> 资料</a></li> -->
                    <li><a href="#" data-am-modal="{target: '#my-alert'}"><span class="am-icon-cog"></span> 改密</a></li>
                    <li><a href="${ctx}/logout"><span class="am-icon-power-off"></span> 退出</a></li>
                </ul>
            </li>
            <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span
                    class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
        </ul>
    </div>
</header>
<div class="am-cf admin-main">
    <!-- sidebar start -->
    <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
        <div class="am-offcanvas-bar admin-offcanvas-bar">
            <ul id="tree" class="ztree"></ul>
        </div>
    </div>
    <!-- sidebar end -->
    <!-- content start -->
    <div class="admin-content">
        <div class="admin-content-body" style="overflow-x: scroll">
            <iframe name="content" src="${ctx}/welcome" style="width:100%;height:100%;min-width: 800px;"
                    frameborder="no" scrolling="no"></iframe>
        </div>
    </div>
    <!-- content end -->
</div>
<!-- 适配小屏幕 -->
<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
   data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">修改密码
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <hr>
        <div class="am-modal-bd">
            <div class="am-g">
                <div class="am-u-md-8 am-u-sm-centered">
                    <form class="am-form" action="${ctx}/admin/updatePassword" method="post"
                          onsubmit="return verifyPassword()">
                        <fieldset class="am-form-set">
                            <input type="password" name="newPassword" placeholder="请输入新密码"><br>
                            <input type="password" name="newPassword2" placeholder="请确认新密码">
                        </fieldset>
                        <button type="submit" class="am-btn am-btn-primary am-btn-block">确定</button>
                    </form>
                </div>
            </div>
        </div>
        <!-- <div class="am-modal-footer">
          <span class="am-modal-btn">确定</span>
        </div> -->
    </div>
</div>
</body>
<script src="${ctxAssets}/3rd-lib/jquery-ztree/3.5/js/jquery.ztree.core-3.5.min.js"></script>
<script>
    (function ($) {
        'use strict';
        $(function () {
            var $fullText = $('.admin-fullText');
            $('#admin-fullscreen').on('click', function () {
                $.AMUI.fullscreen.toggle();
            });
            $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function () {
                $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
            });
        });
    })(jQuery);

    $(function () {
        $(document).ready(function () {
            var settings = {
                view: {
                    showLine: false,
                    showIcon: false,
                    selectedMulti: false,
                    dblClickExpand: false,
                    addDiyDom: addDiyDom
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    beforeClick: function (treeId, treeNode) {
                        var zTree = $.fn.zTree.getZTreeObj("tree");
                        if (treeNode.isParent) {
                            zTree.expandNode(treeNode);
                            return false;
                        } else {
                            if (treeNode.path == "" || treeNode.path == "#") {
                                return;
                            }
                            if (treeNode.path.startsWith('/')) {
                                parent.frames['content'].location.href = "${ctx}" + treeNode.path;
                            } else {
                                parent.frames['content'].location.href = "${ctx}/" + treeNode.path;
                            }
                            return true;
                        }
                    }
                }
            };
            var zNodes = [
                <c:forEach items="${menus}" var="m">
                {id: ${m.id}, pId: ${m.parentId}, name: "${m.name}", open: ${m.rootNode}, path: "${m.url}"},
                </c:forEach>
            ];

            var zTree = $("#tree");
            $.fn.zTree.init(zTree, settings, zNodes);
            zTree_Menu = $.fn.zTree.getZTreeObj("tree");
//  			zTree.hover(function () {
            zTree.addClass("showIcon");
//  			}, function() {
//  				zTree.removeClass("showIcon");
//  			});
        });

        function addDiyDom(treeId, treeNode) {
            var spaceWidth = 5;
            var switchObj = $("#" + treeNode.tId + "_switch"),
                icoObj = $("#" + treeNode.tId + "_ico");
            switchObj.remove();
            icoObj.before(switchObj);
            if (treeNode.level > 1) {
                var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
                switchObj.before(spaceStr);
            }
        }
    });

    function verifyPassword() {
        var newPassword = $('[name=newPassword]').val();
        var newPassword2 = $('[name=newPassword2]').val();
        if (newPassword == newPassword2) {
            return true;
        } else {
            alert('两次输入的密码不一致');
            return false;
        }
    }
</script>
</html>