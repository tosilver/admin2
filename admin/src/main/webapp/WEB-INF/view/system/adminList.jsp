<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">用户管理</strong> /
                <small>用户</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <shiro:hasPermission name="admin:create">
                            <button type="button" class="am-btn am-btn-default"
                                    onclick="window.location='${ctx}/admin/create'">
                                <span class="am-icon-plus"></span> 新增
                            </button>
                        </shiro:hasPermission>
                    </div>
                </div>
            </div>
            <%--<div class="am-u-sm-12 am-u-md-3">--%>
            <%--<div class="am-input-group am-input-group-sm">--%>
            <%--<input type="text" class="am-form-field">--%>
            <%--<span class="am-input-group-btn">--%>
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
                            <th>ID</th>
                            <th>用户名</th>
                            <th>所属组织</th>
                            <th>角色列表</th>
                            <th>状态</th>
                            <%--<c:if test="${adminId eq 1}">--%>
                            <th>操作</th>
                            <%--</c:if>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${adminList}" var="admin">
                            <tr>
                                <td>${admin.id}</td>
                                <td>${admin.username}</td>
                                <td>${fnc:organizationName(admin.organizationId)}</td>
                                <td>${fnc:roleNames(admin.roleIds)}</td>
                                <td>${admin.status eq 1 ? "启用" : "已冻结"}</td>
                                <td>
                                        <%-- 						                <shiro:hasPermission name="admin:delete"> --%>
                                    <c:if test="${admin.status eq 1}">
                                        <a href="${ctx}/admin/${admin.id}/delete"
                                           onclick="return confirm('确认冻结该用户？', this.href)">冻结</a>
                                    </c:if>
                                    <c:if test="${admin.status eq 0}">
                                        <a href="${ctx}/admin/${admin.id}/startUsing"
                                           onclick="return confirm('确认启用该用户？', this.href)">启用</a>
                                    </c:if>
                                    <c:if test="${adminId eq 1}">
                                        <%-- 						                </shiro:hasPermission> --%>
                                        <%-- 						                <shiro:hasPermission name="admin:update"> --%>
                                        <a href="${ctx}/admin/${admin.id}/update">修改</a>
                                        <%-- 						                </shiro:hasPermission> --%>
                                        <%-- 						                <shiro:hasPermission name="admin:update"> --%>
                                        <a href="${ctx}/admin/${admin.id}/changePassword">改密</a>
                                        <%-- 						                </shiro:hasPermission> --%>
                                    </c:if>
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