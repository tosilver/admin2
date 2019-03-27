<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>角色管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">角色管理</strong> /
                <small>角色</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <shiro:hasPermission name="role:create">
                            <button type="button" class="am-btn am-btn-default"
                                    onclick="window.location='${ctx}/role/form'">
                                <span class="am-icon-plus"></span> 新增
                            </button>
                        </shiro:hasPermission>
                    </div>
                </div>
            </div>
            <%--<div class="am-u-sm-12 am-u-md-3">--%>
            <%--<div class="am-input-group am-input-group-sm">--%>
            <%--<input type="text" class="am-form-field"> <span--%>
            <%--class="am-input-group-btn">--%>
            <%--<button class="am-btn am-btn-default" type="button">搜索</button>--%>
            <%--</span>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form">
                    <table id="contentTable"
                           class="am-table am-table-striped am-table-hover table-main">
                        <thead>
                        <tr>
                            <th>角色名称</th>
                            <th>角色描述</th>
                            <%--<th>拥有的资源</th>--%>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${roleList}" var="role">
                            <tr>
                                <td>${role.role}</td>
                                <td>${role.description}</td>
                                    <%--<td>${fnc:resourceNames(role.resourceIds)}</td>--%>
                                <td>
                                        <%-- 					                    <shiro:hasPermission name="role:update"> --%>
                                    <a href="${ctx}/role/form?id=${role.id}">修改</a>
                                        <%-- 					                    </shiro:hasPermission> --%>
                                        <%-- 					                    <shiro:hasPermission name="role:delete"> --%>
                                    <a href="${ctx}/role/${role.id}/delete"
                                       onclick="return confirm('确认要删除该条数据吗？', this.href)">删除</a>
                                        <%-- 					                    </shiro:hasPermission> --%>
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
<script type="text/javascript">
    $(document).ready(function () {

    });
</script>
</body>
</html>