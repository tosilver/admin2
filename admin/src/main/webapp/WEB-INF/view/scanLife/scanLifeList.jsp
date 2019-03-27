<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>二维码管理</title>
    <script type="text/javascript">
        function next() {
            window.location = "${ctx}/scanLife/upload";
        }
    </script>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">上传记录</strong> /
                <small>二维码码信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <%--<div class="am-u-sm-6" style="width: 30%;">--%>
            <%--<div class="am-btn-toolbar">--%>
            <%--<div class="am-btn-group am-btn-group-xs">--%>
            <%--<strong class="am-text-primary am-text-lg">交易金额：${not empty sumMoney ? sumMoney : "0.0"}元</strong> / <small>交易笔数：${page.totalCount}笔</small>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/scanLife/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>

                    <%--<div class="am-input-group am-input-group-sm">--%>
                    <%--<input type="text" style="width:120px;" class="am-form-field" name="merchantOrderNo" value="${page.params.merchantOrderNo}" placeholder="商户订单号">--%>
                    <%--</div>--%>

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
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '120px'}" placeholder="筛选产品" name="routerId"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">所有产品</option>
                            <c:forEach items="#{routerList}" var="router">
                                <option value="${router.id}"
                                        <c:if test="${router.id eq page.params.routerId}">selected</c:if>>${router.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="筛选产品" name="channelId"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">所有渠道</option>
                            <c:forEach items="#{channelList}" var="channel">
                                <option value="${channel.id}"
                                        <c:if test="${channel.id eq page.params.channelId}">selected</c:if>>${channel.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%--<div class="am-input-group am-input-group-sm">--%>
                    <%--<select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status" onchange="$('#searchForm').submit()">--%>
                    <%--<option value=" ">所有状态</option>--%>
                    <%--<option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>可用</option>--%>
                    <%--<option value="-1" <c:if test="${page.params.status eq -1}">selected</c:if>>失效</option>--%>
                    <%--</select>--%>
                    <%--</div>--%>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="类型" name="label"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">所有类型</option>
                            <option value="1" <c:if test="${page.params.label eq 1}">selected</c:if>>固额码</option>
                            <option value="2" <c:if test="${page.params.label eq 2}">selected</c:if>>任意码</option>
                        </select>
                    </div>

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
                    <input type="button" value="上传二维码" onclick="next()">
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
                            <th>产品名</th>
                            <th>渠道ID</th>
                            <%--<shiro:hasPermission name="admin:create">--%>
                            <%--<th>支付渠道</th>--%>
                            <%--</shiro:hasPermission>--%>

                            <th>码类型</th>
                            <th>图片地址</th>
                            <%--<th>状态</th>--%>
                            <th>上传时间</th>
                            <%--<th width="80px">操作</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="scanLife" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${scanLife.merchant.company}</td>
                                <td>
                                        ${scanLife.router.name}
                                        <%--<c:if test="${scanLife.product == 1}">微信</c:if>--%>
                                        <%--<c:if test="${scanLife.product == 2}">支付宝</c:if>--%>
                                </td>
                                <td>${scanLife.channel.name}</td>
                                <td>
                                    <c:if test="${scanLife.label == 1}">固额码</c:if>
                                    <c:if test="${scanLife.label == 2}">任意码</c:if>
                                </td>
                                <td>${scanLife.pictureUrl}</td>
                                    <%--<td>${scanLife.status == 1 ? '可用' : '失效'}</td>--%>
                                <td><fmt:formatDate value='${scanLife.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                        <%--<a href="${ctx}/scanLife/form?id=${scanLife.id}">修改</a>--%>
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