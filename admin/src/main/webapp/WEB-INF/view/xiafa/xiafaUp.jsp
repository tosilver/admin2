<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商戶交易管理</title>
    <%@ include file="../include/head.jsp" %>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript">
        function upload() {
            document.fileForm.submit();
        }

        //发送短信验证码
        function sendSmsCode() {
            $.get({
                url: "/xiafa/sendSms",
                dataType: "json",
                data: {},
                success: function (resultInfo) {
                    var flag = resultInfo.code;
                    if (flag == 1) {
                        $("#smsRsp").text(resultInfo.msg);
                        /*window.location = "register_ok.html";*/
                        alert(resultInfo.msg);
                    } else {
                        alert(resultInfo.msg);
                        $("#smsRsp").text(resultInfo.msg);
                    }
                },
                error: function () {
                    location.href = "error.html";
                }
            })
        }
    </script>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">批付数据录入</strong> /
                <small>文件上传</small>
                <a type="button" class="am-btn am-btn-primary am-btn-xs" style="margin-left: 20px" alt="请按照此模板来录入数据"
                   href="${pageContext.request.contextPath}/download/下发模板.xlsx">下载execl模板
                </a>
            </div>
        </div>
        <hr>
        <div class="am-g" style="margin-left: 20px;">
            <%--enctype="multipart/form-data" 指定当前表单位文件上传表单--%>
            <form name="fileForm" action="${pageContext.request.contextPath}/xiafa/upload"
                  method="post" enctype="multipart/form-data">
                <span>点击<span style="color:red;">选择文件</span>上传下发表格</span>
                <input type="file" name="execlFile" style="margin-left: 30px;"> <br><%--FileItem对象--%>
                <div class="control-group">
                    <hr/>
                    <div class="controls">
                        <input type="text" name="code" placeholder="短信验证码" class="input-xfat input-xlarge">
                        <a href="#"
                           onclick="sendSmsCode();">获取短信验证码</a>
                        <label id="smsRsp"></label>
                    </div>
                </div>
                <a type="button" class="am-btn am-btn-primary am-btn-xs" style="margin-top: 5px"
                   onclick="upload();">确认上传
                </a>
                <div><span>${empty msg? "" : msg}</span></div>
            </form>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/download/下发模板.xlsx"></a>
        </div>
        <%--<div class="am-u-sm-6" style="width: 70%;">--%>
        <%--<form id="searchForm" action="${ctx}/orderYfbPayroll/list" method="get" class="am-form-inline">--%>
        <%--<input type="hidden" name="pageIndex" value="${page.pageIndex}"/>--%>
        <%--<input type="hidden" name="pageSize" value="${page.pageSize}"/>--%>

        <%--<div class="am-input-group am-input-group-sm">--%>
        <%--<input type="text" style="width:120px;" class="am-form-field" name="serialNo" value="${page.params.serialNo}" onKeyUp="value=value.replace(/[\W]/g,'')" placeholder="商户订单号">--%>
        <%--</div>--%>

        <%--<shiro:hasPermission name="admin:create">--%>
        <%--<div class="am-input-group am-input-group-sm">--%>
        <%--<select data-am-selected="{btnWidth: '120px'}" placeholder="商户" name="merchantId" onchange="$('#searchForm').submit()">--%>
        <%--<option value=" ">所有商户</option>--%>
        <%--<c:forEach items="#{merchantList}" var="merchant">--%>
        <%--<option value="${merchant.id}" <c:if test="${merchant.id eq page.params.merchantId}">selected</c:if>>${merchant.company}</option>--%>
        <%--</c:forEach>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--</shiro:hasPermission>--%>

        <%--&lt;%&ndash;<div class="am-input-group am-input-group-sm">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<select data-am-selected="{btnWidth: '120px'}" placeholder="筛选产品" name="channelId" onchange="$('#searchForm').submit()">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<option value=" ">所有渠道</option>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<c:forEach items="#{channelList}" var="channel">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<option value="${channel.id}" <c:if test="${channel.id eq page.params.channelId}">selected</c:if>>${channel.name}</option>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

        <%--<div class="am-input-group am-input-group-sm">--%>
        <%--<select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status" onchange="$('#searchForm').submit()">--%>
        <%--<option value=" ">全部</option>--%>
        <%--<option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>处理中</option>--%>
        <%--<option value="2" <c:if test="${page.params.status eq 2}">selected</c:if>>支付成功</option>--%>
        <%--<option value="3" <c:if test="${page.params.status eq 3}">selected</c:if>>支付失败</option>--%>
        <%--</select>--%>
        <%--</div>--%>

        <%--<div class="am-input-group am-input-group-sm">--%>
        <%--&lt;%&ndash;<div class="am-input-group date" id="datetimepicker1"  data-date="" data-date-format="yyyy-mm-dd hh:ii">&ndash;%&gt;--%>
        <%--<input size="16"  name="startDate" id="startDate" value="${page.params.startDate}" type="datetime-local" class="am-form-field" placeholder="起始时间">--%>
        <%--<span class="am-input-group-label add-on">--%>
        <%--<button class="am-btn am-btn-default" type="button">--%>
        <%--<span class="icon-th am-icon-calendar"></span>--%>
        <%--</button>--%>
        <%--</span>--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--</div>--%>

        <%--<div class="am-input-group am-input-group-sm">--%>
        <%--&lt;%&ndash;<div class="am-input-group date" id="datetimepicker"  data-date="" data-date-format="yyyy-mm-dd hh:ii" >&ndash;%&gt;--%>
        <%--<input size="16"  name="endDate" id="endDate" value="${page.params.endDate}" type="datetime-local" class="am-form-field" placeholder="结束时间" >--%>
        <%--<span class="am-input-group-label add-on">--%>
        <%--<button class="am-btn am-btn-default" type="button">--%>
        <%--<span class="icon-th am-icon-calendar"></span>--%>
        <%--</button>--%>
        <%--</span>--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--</div>--%>

        <%--<button type="submit" class="am-btn am-btn-sm am-radius"><span class="am-icon-search">搜索</span></button>--%>
        <%--</form>--%>
        <%--</div>--%>
    </div>
    <%--<div class="am-g">--%>
    <%--<div class="am-u-sm-12">--%>
    <%--<form class="am-form">--%>
    <%--<table id="contentTable" class="am-table am-table-striped am-table-hover table-main">--%>
    <%--<thead>--%>
    <%--<tr>--%>
    <%--<th>序号</th>--%>
    <%--<th>订单号</th>--%>
    <%--<th>商户编号</th>--%>
    <%--<th>商户名</th>--%>
    <%--<th>代付成本</th>--%>
    <%--<th>交易金额</th>--%>
    <%--<th>调用状态</th>--%>
    <%--<th>调用时间</th>--%>
    <%--<th width="80px">操作</th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <%--<tbody>--%>
    <%--<c:forEach items="${page.list}" var="detailDataCard" varStatus="status">--%>
    <%--<tr>--%>
    <%--<td>${status.index + 1}</td>--%>
    <%--<td>${detailDataCard.serialNo}</td>--%>
    <%--<td>${detailDataCard.merchantId}</td>--%>
    <%--<td>${detailDataCard.company}</td>--%>
    <%--<td>${detailDataCard.merchantPayCost}</td>--%>
    <%--<td>${detailDataCard.amount}</td>--%>
    <%--<td>--%>
    <%--<c:if test="${detailDataCard.status == 1}">处理中</c:if>--%>
    <%--<c:if test="${detailDataCard.status == 2}">支付成功</c:if>--%>
    <%--<c:if test="${detailDataCard.status == 3}">支付失败</c:if>--%>
    <%--</td>--%>
    <%--<td><fmt:formatDate value='${detailDataCard.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>--%>
    <%--<td>--%>
    <%--<a href="${ctx}/orderYfbPayroll/form?id=${detailDataCard.id}">详情</a>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</tbody>--%>
    <%--</table>--%>
    <%--</form>--%>
    <%--<%@ include file="../include/pagination.jsp"%>--%>
    <%--</div>--%>
    <%--</div>--%>
</div>
</body>
</html>
