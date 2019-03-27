<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>路由配置管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">路由配置管理</strong> /
                <small>路由配置信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6">
                <div class="am-btn-toolbar">
                    <shiro:hasPermission name="admin:create">
                        <div class="am-btn-group am-btn-group-xs">
                            <button type="button" class="am-btn am-btn-default"
                                    onclick="window.location='${ctx}/router/form'">
                                <span class="am-icon-plus"></span> 新增
                            </button>
                        </div>
                    </shiro:hasPermission>
                </div>
            </div>
            <div class="am-u-sm-6">
                <form id="searchForm" action="${ctx}/router" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>
                    <input type="hidden" name="status" value="${page.params.status}"/>
                    <div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:100px;" class="am-form-field" name="name"
                               value="${page.params.name}" placeholder="路由名">
                    </div>
                    <button type="submit" class="am-btn am-btn-sm am-radius"><span class="am-icon-search">搜索</span>
                    </button>
                </form>
            </div>
        </div>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form">
                    <table id="contentTable" class="am-table am-table-striped am-table-hover table-main">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>服务名</th>
                            <th>路由名</th>
                            <th>版本</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="router" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${router.id}</td>
                                <td>${router.name}</td>
                                <td>${router.version}</td>
                                <td>${router.status == 1 ? "正常" : "禁用"}</td>
                                <td><fmt:formatDate value="${router.createTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <a href="${ctx}/router/form?id=${router.id}">详情</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
                <%@ include file="../include/pagination.jsp" %>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
</script>
</html>