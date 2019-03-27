<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>角色编辑</title>
    <%@ include file="../include/head.jsp" %>
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
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">角色管理</strong> /
                <small>角色${not empty user.id?'修改':'添加'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g admin-form">
            <form class="am-form am-form-horizontal" action="save" method="post">
                <input type="hidden" name="id" value="${role.id}"/>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">角色名：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="role" value="${role.role}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6">*必填</div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">角色描述：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="description" value="${role.description}"/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">拥有的资源列表：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <div class="am-input-group">
                            <input type="text" id="resourceName" class="am-form-field"
                                   value="${fnc:resourceNames(role.resourceIds)}" readonly/>
                            <input type="hidden" id="resourceIds" name="resourceIds"
                                   value='<c:forEach items="${role.resourceIds}" var="item">${item},</c:forEach>'/>
                            <span class="am-input-group-btn">
						        <button class="am-btn am-btn-default" id="menuBtn" type="button">选择</button>
						      </span>
                        </div>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-margin">
                    <button type="submit" class="am-btn am-btn-primary">保 存</button>
                    <button type="button" class="am-btn" onclick="history.go(-1)">返 回</button>
                </div>
            </form>
        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="tree" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
</body>
<script src="${ctxAssets}/3rd-lib/jquery-ztree/3.5/js/jquery.ztree.all-3.5.min.js"></script>
<script>
    $(function () {
        var setting = {
            check: {
                enable: true,
                chkboxType: {"Y": "", "N": ""}
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onCheck: onCheck
            }
        };

        var zNodes = [
            <c:forEach items="${resourceList}" var="r">
            <c:if test="${not r.rootNode}">
            {id:${r.id}, pId:${r.parentId}, name: "${r.name}", checked:${fnc:in(role.resourceIds, r.id)}},
            </c:if>
            </c:forEach>
        ];

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree"),
                nodes = zTree.getCheckedNodes(true),
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
            $("#resourceIds").val(id);
            $("#resourceName").val(name);
            // hideMenu();
        }

        function showMenu() {
            var cityObj = $("#resourceName");
            var cityOffset = $("#resourceName").offset();
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
</html>