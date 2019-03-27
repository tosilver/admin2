<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商戶结算信息</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商戶结算信息</strong> /
                <small>商戶结算</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width: 40%">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <button type="button" class="am-btn am-btn-default"
                                onclick="window.location='${ctx}/settlement/form'">
                            <span class="am-icon-plus"></span> 新增
                        </button>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 60%">
                <form id="searchForm" action="${ctx}/settlement/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>
                    <%--<div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status" onchange="$('#searchForm').submit()">
                            <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>启用</option>
                            <option value="0" <c:if test="${page.params.status eq 0}">selected</c:if>>未启用</option>
                        </select>
                    </div>--%>
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
                    <select data-am-selected="{btnWidth: '100px'}" placeholder="通道" name="settleType"
                            onchange="$('#searchForm').submit()">
                        <option value=" ">结算类型</option>
                        <c:forEach items="${settleTypes}" var="item">
                            <option value="${item}"
                                    <c:if test="${item eq page.params.settleType}">selected</c:if>>${item.text}</option>
                        </c:forEach>
                    </select>

                    <select data-am-selected="{btnWidth: '100px'}" placeholder="接口" name="accountType"
                            onchange="$('#searchForm').submit()">
                        <option value=" ">账户类型</option>
                        <c:forEach items="${accountTypes}" var="item">
                            <option value="${item}"
                                    <c:if test="${item eq page.params.accountType}">selected</c:if>>${item.text}</option>
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
                            <th>结算类型</th>
                            <th>账户类型</th>
                            <th>银行账户</th>
                            <th>开户银行</th>
                            <th>银行名称</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="settlement" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${settlement.id}</td>
                                <td>${settlement.merchant.company}</td>
                                <td>${settlement.settleType.text}</td>
                                <td>${settlement.accountType.text}</td>
                                <td>${settlement.bankAccount}</td>
                                <td>${settlement.bankName}</td>
                                <td>${settlement.bankNickname}</td>
                                <td><fmt:formatDate value="${settlement.createTime}"
                                                    pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <a href="${ctx}/settlement/form?id=${settlement.id}">详情</a>
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