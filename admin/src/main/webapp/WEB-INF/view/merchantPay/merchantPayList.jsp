<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>渠道管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商户充值管理</strong> /
                <small>充值信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <shiro:hasAnyRoles name="admin,superadmin">
                <div class="am-u-sm-6" style="width:30%;">
                    <div class="am-btn-toolbar">
                        <div class="am-btn-group am-btn-group-xs">
                            <button type="button" class="am-btn am-btn-default"
                                    onclick="window.location='${ctx}/merchantPay/form'">
                                <span class="am-icon-plus"></span> 新增
                            </button>
                        </div>
                    </div>
                </div>
            </shiro:hasAnyRoles>
            <div class="am-u-sm-6" style="width: 30%;">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <strong class="am-text-primary am-text-lg">交易金额：${not empty sumMoney ? sumMoney : "0.0"}元</strong>
                        /
                        <small>交易笔数：${page.totalCount}笔</small>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/merchantPay/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">全部</option>
                            <option value="0" <c:if test="${page.params.status eq 0}">selected</c:if>>预充值</option>
                            <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>已充值</option>
                        </select>
                    </div>
                    <%--<div class="am-input-group am-input-group-sm">--%>
                    <%--<input type="text" style="width:100px;" class="am-form-field" name="name" value="${page.params.name}" placeholder="渠道名">--%>
                    <%--</div>--%>
                    <div class="am-input-group am-input-group-sm">
                        <div class="am-input-group am-datepicker-date" style="width:150px;"
                             data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
                            <input type="text" name="startDate" value="${page.params.startDate}" class="am-form-field"
                                   placeholder="起始时间" readonly>
                            <span class="am-input-group-btn am-datepicker-add-on">
                                <button class="am-btn am-btn-default" type="button">
                                    <span class="am-icon-calendar"></span>
                                </button>
                            </span>
                        </div>
                    </div>
                    <div class="am-input-group am-input-group-sm">
                        <div class="am-input-group am-datepicker-date" style="width:150px;"
                             data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
                            <input type="text" name="endDate" value="${page.params.endDate}" class="am-form-field"
                                   placeholder="结束时间" readonly>
                            <span class="am-input-group-btn am-datepicker-add-on">
                                <button class="am-btn am-btn-default" type="button">
                                    <span class="am-icon-calendar"></span>
                                </button>
                            </span>
                        </div>
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
                            <th>订单流水</th>
                            <th>商户ID</th>
                            <th>商户名</th>
                            <th>充值金额</th>
                            <th>操作人</th>
                            <th>订单状态</th>
                            <th>创建时间</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="merchantPay" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${merchantPay.orderId}</td>
                                <td>${merchantPay.merchantId}</td>
                                <td>${merchantPay.company}</td>
                                <td>${merchantPay.amount}</td>
                                <td>${merchantPay.operator}</td>
                                <td>
                                    <c:if test="${merchantPay.status == 0}">预充值</c:if>
                                    <c:if test="${merchantPay.status == 1}">已充值</c:if>
                                </td>
                                <td><fmt:formatDate value="${merchantPay.createTime}"
                                                    pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <a href="${ctx}/merchantPay/form?id=${merchantPay.id}">详情</a>
                                    <shiro:hasAnyRoles name="admin,superadmin">
                                        <c:if test="${merchantPay.status != 1}">
                                            <a href="${ctx}/merchantPay/updateStatus?id=${merchantPay.id}&status=1"
                                               onClick="return confirm('是否确定充值')">确认充值</a>
                                        </c:if>
                                    </shiro:hasAnyRoles>
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
