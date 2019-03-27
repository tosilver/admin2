<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商戶交易管理</title>
    <%@ include file="../include/head.jsp" %>
    <script language="javascript" type="text/javascript" src="${ctx}/assets/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">批付记录</strong> /
                <small>批付信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width: 100%">
                <div class="am-btn-toolbar">
                    <shiro:hasRole name="superadmin">
                        <div class="am-btn-group am-btn-group-xs">
                            <button type="button" class="am-btn am-btn-default"
                                    onclick="window.location='${ctx}/orderYfbPayroll/form'">
                                <span class="am-icon-plus"></span> 新增
                            </button>
                        </div>
                    </shiro:hasRole>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 30%;">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <strong class="am-text-primary am-text-lg">交易金额：${not empty sumAmount ? sumAmount : "0.0"}元</strong><br>
                        <strong class="am-text-primary am-text-lg">成本金额：${not empty sumCost ? sumCost : "0.0"}元</strong><br>
                        <strong class="am-text-primary am-text-lg">总金额：${not empty sumTotalAmount ? sumTotalAmount : "0.0"}元</strong>
                        /
                        <small>交易笔数：${page.totalCount}笔</small>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/orderYfbPayroll/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>

                    <div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:120px;" class="am-form-field" name="serialNo"
                               value="${page.params.serialNo}" onKeyUp="value=value.replace(/[\W]/g,'')"
                               placeholder="商户订单号">
                    </div>

                    <shiro:hasPermission name="admin:create">
                        <div class="am-input-group am-input-group-sm">
                            <select data-am-selected="{btnWidth: '120px'}" placeholder="商户" name="merchantId"
                                    onchange="$('#searchForm').submit()">
                                <option value=" ">所有商户</option>
                                <c:forEach items="#{merchantList}" var="merchant">
                                    <option value="${merchant.id}"
                                            <c:if test="${merchant.id eq page.params.merchantId}">selected</c:if>>${merchant.company}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </shiro:hasPermission>

                    <%--<div class="am-input-group am-input-group-sm">--%>
                    <%--<select data-am-selected="{btnWidth: '120px'}" placeholder="筛选产品" name="channelId" onchange="$('#searchForm').submit()">--%>
                    <%--<option value=" ">所有渠道</option>--%>
                    <%--<c:forEach items="#{channelList}" var="channel">--%>
                    <%--<option value="${channel.id}" <c:if test="${channel.id eq page.params.channelId}">selected</c:if>>${channel.name}</option>--%>
                    <%--</c:forEach>--%>
                    <%--</select>--%>
                    <%--</div>--%>

                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">全部</option>
                            <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>处理中</option>
                            <option value="2" <c:if test="${page.params.status eq 2}">selected</c:if>>支付成功</option>
                            <option value="3" <c:if test="${page.params.status eq 3}">selected</c:if>>支付失败</option>
                        </select>
                    </div>

                    <div class="am-input-group am-input-group-sm">

                        <input type="text" value="${page.params.startDate}" class="am-form-field" name="startDate"
                               id="startDate" onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'} )"
                               placeholder="起始时间"/>
                    </div>

                    <div class="am-input-group am-input-group-sm">
                        <input type="text" value="${page.params.endDate}" class="am-form-field" name="endDate"
                               id="endDate" onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'} )"
                               placeholder="结束时间"/>
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
                            <th>订单号</th>
                            <th>商户编号</th>
                            <th>商户名</th>
                            <th>代付成本</th>
                            <th>交易金额</th>
                            <th>调用状态</th>
                            <th>调用时间</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="xiafa" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${xiafa.serialNo}</td>
                                <td>${xiafa.merchantId}</td>
                                <td>${xiafa.company}</td>
                                <td>${xiafa.merchantPayCost}</td>
                                <td>${xiafa.amount}</td>
                                <td>
                                    <c:if test="${xiafa.status == 1}">处理中</c:if>
                                    <c:if test="${xiafa.status == 2}">支付成功</c:if>
                                    <c:if test="${xiafa.status == 3}">支付失败</c:if>
                                </td>
                                <td><fmt:formatDate value='${xiafa.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <a href="${ctx}/orderYfbPayroll/form?id=${xiafa.id}">详情</a>
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
