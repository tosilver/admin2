<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>IP白名单管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">IP白名单管理</strong> /
                <small>IP白名单</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <%--<button type="button" class="am-btn am-btn-default" onclick="window.location='${ctx}/ipWhiteList/form'">
                            <span class="am-icon-plus"></span> 新增
                        </button>--%>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-6">
                <form id="searchForm" action="${ctx}/car/ipWhiteList/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>
                    <input type="hidden" name="status" value="${page.params.status}"/>
                    <div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:100px;" class="am-form-field" name="brand"
                               value="${page.params.mobile}" placeholder="电话">
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
                            <th>商户名</th>
                            <th>IP</th>
                            <th>描述</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="ipWhiteList" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${ipWhiteList.merchant.company}</td>
                                <td>${ipWhiteList.ip}</td>
                                <td>${ipWhiteList.desc}</td>
                                <td>${ipWhiteList.status==0?'停用':'可用'}</td>
                                <td><fmt:formatDate value="${ipWhiteList.createTime}"
                                                    pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td><fmt:formatDate value="${ipWhiteList.updateTime}"
                                                    pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <a href="${ctx}/ipWhiteList/form?id=${ipWhiteList.id}">详情</a>
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