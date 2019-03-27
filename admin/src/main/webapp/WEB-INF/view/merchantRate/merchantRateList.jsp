<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商戶费率管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商戶费率管理</strong> /
                <small>商戶</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width: 40%">
                <div class="am-btn-toolbar">
                    <shiro:hasPermission name="admin:create">
                        <div class="am-btn-group am-btn-group-xs">
                            <button type="button" class="am-btn am-btn-default"
                                    onclick="window.location='${ctx}/merchantRate/form'">
                                <span class="am-icon-plus"></span> 新增
                            </button>
                        </div>
                    </shiro:hasPermission>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 60%">
                <form id="searchForm" action="${ctx}/merchantRate/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="商户" name="merchantId"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">全部商户</option>
                            <c:forEach items="${merchantList}" var="merchant">
                                <option value="${merchant.id}"
                                        <c:if test="${merchant.id eq page.params.merchantId}">selected</c:if>>${merchant.company}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%--<shiro:hasPermission name="admin:create">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="通道" name="channelId" onchange="$('#searchForm').submit()">
                            <option value=" ">全部渠道</option>
                            <c:forEach items="${channelList}" var="channel">
                                <option value="${channel.id}" <c:if test="${channel.id eq page.params.channelId}">selected</c:if>>${channel.name}</option>
                            </c:forEach>
                        </select>
                    </shiro:hasPermission>--%>

                    <select data-am-selected="{btnWidth: '100px'}" placeholder="产品" name="routerId"
                            onchange="$('#searchForm').submit()">
                        <option value=" ">全部产品</option>
                        <c:forEach items="${routerList}" var="router">
                            <option value="${router.id}"
                                    <c:if test="${router.id eq page.params.routerId}">selected</c:if>>${router.name}</option>
                        </c:forEach>
                    </select>
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
                            <th>ID</th>
                            <th>商户名</th>
                            <th>产品名</th>
                            <th>结算费率</th>
                            <th>代付成本</th>
                            <th>提现成本</th>
                            <%--<th>创建时间</th>--%>
                            <th>更新时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="merchantRate" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${merchantRate.id}</td>
                                <td>${merchantRate.merchant.company}</td>
                                <td>${merchantRate.router.name}</td>
                                <td>${merchantRate.costRate}</td>
                                <td>${merchantRate.payCost}</td>
                                <td>${merchantRate.withdrawCost}</td>
                                    <%--<td><fmt:formatDate value="${merchantRate.createTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>--%>
                                <td><fmt:formatDate value="${merchantRate.updateTime}"
                                                    pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <a href="${ctx}/merchantRate/form?id=${merchantRate.id}">详情</a>
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