<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商戶交易管理</title>
    <%@ include file="../include/head.jsp" %>
    <link rel="stylesheet" href="${ctx}/assets/3rd-lib/amazeui/datetimepicker/css/amazeui.datetimepicker.css"/>
    <%--<script src="${ctx}/assets/3rd-lib/amazeui/datetimepicker/js/locales/amazeui.datetimepicker.zh-CN.js"></script>--%>
    <%--<script src="${ctx}/assets/3rd-lib/amazeui/datetimepicker/js/amazeui.datetimepicker.js"></script>--%>
    <%--<script src="${ctx}/assets/3rd-lib/amazeui/adddate.js"></script>--%>
    <script language="javascript" type="text/javascript" src="${ctx}/assets/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">分账记录</strong> /
                <small>分账信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width: 30%;">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <strong class="am-text-primary am-text-lg">分账金额：${not empty fzAmount ? fzAmount : "0.0"}元</strong>
                        /
                        <small>${fzCount}笔</small>
                        <br>
                        <strong class="am-text-primary am-text-lg">分账失败：${failCount}笔</strong>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/fzInfo/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>

                    <div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:120px;" class="am-form-field" name="downOrderId"
                               value="${page.params.downOrderId}" onKeyUp="value=value.replace(/[\W]/g,'')"
                               placeholder="商户订单号">
                    </div>

                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '120px'}" placeholder="渠道" name="channelId"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">所有渠道</option>
                            <c:forEach items="#{channelList}" var="channel">
                                <option value="${channel.id}"
                                        <c:if test="${channel.id eq page.params.channelId}">selected</c:if>>${channel.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '140px'}" placeholder="分账" name="transinId"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">所有分账用户</option>
                            <c:forEach items="#{transinList}" var="transin">
                                <option value="${transin.id}"
                                        <c:if test="${transin.id eq page.params.transinId}">selected</c:if>>${transin.realname}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%--<div class="am-input-group am-input-group-sm">--%>
                    <%--<select data-am-selected="{btnWidth: '120px'}" placeholder="筛选产品" name="channelId" onchange="$('#searchForm').submit()">--%>
                    <%--<option value=" ">所有渠道</option>--%>
                    <%--<c:forEach items="#{channelList}" var="channel">--%>
                    <%--<option value="${channel.id}" <c:if test="${channel.id eq page.params.channelId}">selected</c:if>>${channel.name}</option>--%>
                    <%--</c:forEach>--%>
                    <%--</select>--%>
                    <%--</div>--%>

                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="fzStatus"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">全部</option>
                            <option value="1" <c:if test="${page.params.fzStatus eq 1}">selected</c:if>>分账成功</option>
                            <option value="2" <c:if test="${page.params.fzStatus eq 2}">selected</c:if>>分账失败</option>
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
                            <th>商户订单号</th>
                            <th>支付宝订单号</th>
                            <th>支付渠道</th>
                            <%--<th>产品名</th>--%>
                            <%--<shiro:hasPermission name="admin:create">--%>
                            <%--<th>支付渠道</th>--%>
                            <%--</shiro:hasPermission>--%>
                            <th>分账账户姓名</th>
                            <th>交易金额</th>
                            <th>分账金额</th>
                            <th>分账时间</th>
                            <th>分账状态</th>
                            <%--<th width="80px">操作</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${vo}" var="entity" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${entity.consume.merchantOrderNo}</td>
                                <td>${entity.consume.payOrderNo}</td>
                                <td>${entity.consume.channel.name}</td>
                                    <%--<td>${payroll.router.name}</td>--%>
                                    <%--<shiro:hasPermission name="admin:create">--%>
                                    <%--<td>${consume.channel.name}</td>--%>
                                    <%--</shiro:hasPermission>--%>
                                <td>${entity.transin}</td>
                                <td>${entity.consume.totalAmount}</td>
                                <td>${entity.consume.fzAmount}</td>
                                <td><fmt:formatDate value='${entity.consume.updateTime}'
                                                    pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <c:if test="${entity.consume.fzStatus == 0}">还未分账</c:if>
                                    <c:if test="${entity.consume.fzStatus == 1}">分账成功</c:if>
                                    <c:if test="${entity.consume.fzStatus == 2}">分账失败</c:if>
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
    var channelId = $("#channelId").val();
    var status = document.getElementById("status").value;
    var tradeState = document.getElementById("tradeState").value;
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    var merchantOrderNo = document.getElementById("merchantOrderNo").value;

    function derived() {
        var form = $('<form></form>');
        $(document.body).append(form);
        form.attr('action', "${ctx}/consume/derived");
        form.attr('method', 'post');
        form.attr('target', '_self');
        var my_input3 = $('<input type="text" name="channelId" />');
        if (channelId == null) {
            my_input3.attr('value', "");
        } else {
            my_input3.attr('value', channelId);
        }
        var my_input4 = $('<input type="text" name="status" />');
        my_input4.attr('value', status);
        var my_input5 = $('<input type="text" name="tradeState" />');
        my_input5.attr('value', tradeState);
        var my_input6 = $('<input type="text" name="startDate" />');
        my_input6.attr('value', startDate);
        var my_input7 = $('<input type="text" name="endDate" />');
        my_input7.attr('value', endDate);
        var my_input8 = $('<input type="text" name="merchantOrderNo" />');
        my_input8.attr('value', merchantOrderNo);
        // 附加到Form
        form.append(my_input1, my_input2, my_input3, my_input4, my_input5, my_input6, my_input7, my_input8);
        // 提交表单
        form.submit();
        // 注意return false取消链接的默认动作
        return false;
    }

</script>
