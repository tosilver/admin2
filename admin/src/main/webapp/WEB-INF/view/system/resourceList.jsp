<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/taglib.jsp" %>
<html>
<head>
    <title>资源管理</title>
    <%@ include file="../include/head.jsp" %>
    <link rel="stylesheet" href="${ctxAssets}/3rd-lib/jquery-treetable/3.1.0/css/jquery.treetable.css">
    <link rel="stylesheet" href="${ctxAssets}/3rd-lib/jquery-treetable/3.1.0/css/jquery.treetable.theme.default.css">
    <style>
        #table th, #table td {
            font-size: 14px;
            padding: 8px;
        }
    </style>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">资源管理</strong> /
                <small>资源</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form">
                    <table id="contentTable" class="am-table am-table-striped am-table-hover table-main">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>类型</th>
                            <th>URL路径</th>
                            <th>权限字符串</th>
                            <th>顺序号</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${resourceList}" var="resource">
                            <tr data-tt-id='${resource.id}'
                                <c:if test="${not resource.rootNode}">data-tt-parent-id='${resource.parentId}'</c:if>>
                                <td>${resource.name}</td>
                                <td>${resource.type.text}</td>
                                <td>${resource.url}</td>
                                <td>${resource.permission}</td>
                                <td>${resource.sort}</td>
                                <td>
                                    <c:if test="${resource.type ne 'BUTTON'}">
                                        <%-- 						                <shiro:hasPermission name="resource:create"> --%>
                                        <a href="${ctx}/resource/appendChild?id=${resource.id}">添加子节点</a>
                                        <%-- 						                </shiro:hasPermission> --%>
                                    </c:if>
                                        <%-- 						                <shiro:hasPermission name="resource:update"> --%>
                                    <c:if test="${not resource.rootNode}">
                                        <a href="/resource/form?id=${resource.id}">查看</a>
                                    </c:if>
                                        <%-- 						                </shiro:hasPermission> --%>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>
</div>
</body>
<script type="text/javascript"
        src="${ctxAssets}/3rd-lib/jquery-treetable/3.1.0/js/jquery.treetable.js?_t=10101011"></script>
<script>
    $(function () {
        $("#contentTable").treetable({expandable: true}).treetable("expandNode", 1);//.treetable("expandAll")
    });
</script>
</html>