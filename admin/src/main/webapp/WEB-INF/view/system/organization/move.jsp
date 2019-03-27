<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>用户编辑</title>
    <%@ include file="../../include/head.jsp" %>
    <link rel="stylesheet" href="${ctxAssets}/3rd-lib/jquery-ztree/3.5/css/zTreeStyle.css">
    <style>
        ul.ztree {
            margin-top: 10px;
            border: 1px solid #ddd;
            background: #fff;
            width: 220px;
            height: 200px;
            overflow-y: auto;
            overflow-x: auto;
        }
    </style>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-g admin-form">
            <form id="form" class="am-form am-form-horizontal" method="post">
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">源节点名称：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" value=" ${source.name}" disabled/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">目标节点名称：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <div class="am-input-group">
                            <input type="text" id="targetName" class="am-form-field" readonly/>
                            <input type="hidden" id="targetId" name="targetId"/>
                            <span class="am-input-group-btn">
						        <button class="am-btn am-btn-default" id="menuBtn" type="button">选择</button>
						      </span>
                        </div>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-margin">
                    <button type="submit" class="am-btn am-btn-primary">保 存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="tree" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
<script src="${ctxAssets}/3rd-lib/jquery-ztree/3.5/js/jquery.ztree.core-3.5.min.js"></script>
<script>
    $(function () {
        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick
            }
        };

        var zNodes = [
            <c:forEach items="${targetList}" var="o">
            {id:${o.id}, pId:${o.parentId}, name: "${o.name}", open:${o.rootNode}},
            </c:forEach>
        ];

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree"),
                nodes = zTree.getSelectedNodes(),
                id = "",
                name = "";
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, l = nodes.length; i < l; i++) {
                id += nodes[i].id + ",";
                name += nodes[i].name + ",";
            }
            if (id.length > 0) id = id.substring(0, id.length - 1);
            if (name.length > 0) name = name.substring(0, name.length - 1);
            $("#targetId").val(id);
            $("#targetName").val(name);
            hideMenu();
        }

        function showMenu() {
            var cityObj = $("#targetName");
            var cityOffset = $("#targetName").offset();
            $("#menuContent").css({
                left: cityOffset.left + "px",
                top: cityOffset.top + cityObj.outerHeight() + "px"
            }).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

        $.fn.zTree.init($("#tree"), setting, zNodes);
        $("#menuBtn").click(showMenu);
    });
</script>
</body>
</html>