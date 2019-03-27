<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>个码交易记录</title>
    <style>
        .tac {
            display: none;
        }

        .tac-content {
            position: fixed;
            z-index: 100;
            width: 330px;
            height: 200px;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            margin: auto;
            border: 1px solid #CCCCCC;
            padding: 15px 20px;
            background-color: white;
        }

        .bg-gray {
            width: 100%;
            height: 100%;
            position: fixed;
            background-color: #3F3F3F;
            opacity: 0.3;
            z-index: 99;
        }

        .title {
            text-align: center;
        }

        .tac-text {
            margin: 30px 0;
        }

        .tac-text span {
            margin-right: 15px;
        }

        .tac-text input {
            padding: 0 10px;
            width: 175px;
            height: 30px;
        }

        .tac-btn {
            margin-left: 105px;
            width: 170px;
        }

        .tac-btn button {
            width: 60px;
            height: 35px;
            margin: 0 10px;
        }
    </style>

    <%@ include file="../include/head.jsp" %>
    <link rel="stylesheet" href="${ctx}/assets/3rd-lib/amazeui/datetimepicker/css/amazeui.datetimepicker.css"/>
    <%--<script src="${ctx}/assets/3rd-lib/amazeui/datetimepicker/js/locales/amazeui.datetimepicker.zh-CN.js"></script>--%>
    <%--<script src="${ctx}/assets/3rd-lib/amazeui/datetimepicker/js/amazeui.datetimepicker.js"></script>--%>
    <%--<script src="${ctx}/assets/3rd-lib/amazeui/adddate.js"></script>--%>
    <script language="javascript" type="text/javascript" src="${ctx}/assets/My97DatePicker/WdatePicker.js"></script>
    <script>
        $(function () {
            $('#queding').click(function () {
                // console.log(document.getElementById("consumeId").value)
                // console.log(document.getElementById("moneys").value)
                $.ajax({
                    type: "get",
                    url: "${ctx}/order/updateStatus",
                    data: {
                        id: document.getElementById("orderId").value,
                        status: 2,
                        payedMoney: document.getElementById("moneys").value
                    },
                    // dataType: "json",
                    success: function (data) {
                        console.log(data)
                        window.location.reload();
                        // location.reload([bForceGet])
                    }
                });
            });


            // $('#comPay').click(function(){
            //     window.location.reload();
            // });
        });
    </script>
</head>
<body>
<%@ include file="../include/alert.jsp" %>

<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">交易记录</strong> /
                <small>交易信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width: 30%;">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <strong class="am-text-primary am-text-lg">交易金额：${not empty amount ? amount : "0.0"}元</strong>
                        /
                        <small>${orderCount}笔</small>
                        <br>
                        <strong class="am-text-primary am-text-lg">已付金额：${not empty accountMoneyStr ? accountMoneyStr : "0.0"}元</strong>
                        /
                        <small>${sumAccountCount}笔</small>
                        <br>
                        <%--<shiro:hasAnyRoles name="superadmin">
                            <strong class="am-text-primary am-text-lg">交易成功率：${not empty successRate ? successRate : "0.0"}%</strong><br>
                        </shiro:hasAnyRoles>
                        <c:if test="${balance != null}">
                            <strong class="am-text-primary am-text-lg">商户余额：${not empty balance ? balance : "0.0"}元</strong><br>
                        </c:if>--%>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/order/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>

                    <%--<div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:120px;" class="am-form-field" name="orderId"
                               id="orderId" value="${page.params.orderId}"
                               onKeyUp="value=value.replace(/[\W]/g,'')" placeholder="商户订单号">
                    </div>--%>
                    <%--</shiro:hasAnyRoles>--%>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '120px'}" placeholder="筛选账号" name="account" id="account"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">所有账号</option>
                            <c:forEach items="#{userList}" var="user">
                                <option value="${user.account}"
                                        <c:if test="${user.account eq page.params.account}">selected</c:if>>${user.account}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%--</shiro:hasAnyRoles>--%>
                    <%--<div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '120px'}" placeholder="筛选产品" name="routerId" id="routerId"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">所有产品</option>
                            <c:forEach items="#{routerList}" var="router">
                                <option value="${router.id}"
                                        <c:if test="${router.id eq page.params.routerId}">selected</c:if>>${router.name}</option>
                            </c:forEach>
                        </select>
                    </div>--%>
                    <%--<shiro:hasAnyRoles name="admin,superadmin">--%>
                    <%--<div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '120px'}" placeholder="筛选产品" name="channelId"
                                id="channelId" onchange="$('#searchForm').submit()">
                            <option value=" ">所有渠道</option>
                            <c:forEach items="#{channelList}" var="channel">
                                <option value="${channel.id}"
                                        <c:if test="${channel.id eq page.params.channelId}">selected</c:if>>${channel.name}</option>
                            </c:forEach>
                        </select>
                    </div>--%>
                    <%--</shiro:hasAnyRoles>--%>
                    <%--<div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="支付方式" name="type" id="status"
                                onchange="$('#searchForm').submit()">
                            <option value="1" <c:if test="${page.params.payType eq 1}">selected</c:if>>支付宝</option>
                            <option value="0" <c:if test="${page.params.payType eq 2}">selected</c:if>>微信</option>
                        </select>
                    </div>--%>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="支付状态" name="payStatus"
                                id="payStatus" onchange="$('#searchForm').submit()">
                            <option value=" ">全部</option>
                            <option value="0" <c:if test="${page.params.payStatus eq 0}">selected</c:if>>未支付</option>
                            <option value="1" <c:if test="${page.params.payStatus eq 1}">selected</c:if>>支付成功</option>
                            <option value="2" <c:if test="${page.params.payStatus eq 2}">selected</c:if>>人工确认支付</option>
                            <option value="-1" <c:if test="${page.params.payStatus eq -1}">selected</c:if>>支付失败</option>
                            <option value="-2" <c:if test="${page.params.payStatus eq -2}">selected</c:if>>关闭</option>
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
                    <%--<a id="exceleee" class="am-btn am-btn-sm am-radius" onclick="derived()" value="导出">导出</a>--%>
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
                            <th>交易金额</th>
                            <th>支付宝收款账号</th>
                            <th class="xians">已付金额</th>
                            <th>创建时间</th>
                            <th>支付状态</th>
                            <th>支付时间</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="order" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${order.orderId}</td>
                                <td>${order.orderMoney}</td>
                                <td>${order.account}</td>
                                <td class="xians">${order.payedMoney}</td>
                                <td><fmt:formatDate value='${order.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <c:if test="${order.payStatus == 0}">未支付</c:if>
                                    <c:if test="${order.payStatus == 1}">支付成功</c:if>
                                    <c:if test="${order.payStatus == 2}">人工确认支付</c:if>
                                    <c:if test="${order.payStatus == -1}">支付失败</c:if>
                                    <c:if test="${order.payStatus == -2}">关闭</c:if>
                                </td>
                                <td><fmt:formatDate value='${order.payTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <shiro:hasAnyRoles name="admin,superadmin">
                                        <c:if test="${order.payStatus == 0}">

                                            <%--<a href="${ctx}/consume/updateStatus?id=${consume.id}&status=2&amount=998"  onClick="return document.getElementsByClassName('tac')[0].style.display = 'block'">确认支付</a>--%>
                                            <a href="#" id="comPay"
                                               onClick="document.getElementsByClassName('tac')[0].style.display = 'block';document.getElementById('orderId').value='${order.orderId}';document.getElementById('moneys').value=${order.orderMoney};">确认支付</a>

                                            <div class="tac">
                                                    <%--<div class="bg-gray"></div>--%>
                                                <div class="tac-content">
                                                    <div class="title">确认支付</div>
                                                    <div class="tac-text">
                                                        <input value="" type="hidden" id="orderId">
                                                        <span>请输入金额(请输入金额四位数的正确数据):</span>
                                                        <input type="text" value=""
                                                               onkeyup="if( ! /^([1-9]\d{0,3}|0)([.]?|(\.\d{1,2})?)$/.test(this.value)){this.value='';}"
                                                               placeholder="请输入金额" id="moneys">
                                                    </div>
                                                    <div class="tac-btn">
                                                        <button id="queding">确认</button>
                                                        <button id="quxiao"
                                                                onclick="document.getElementsByClassName('tac')[0].style.display='none';">
                                                            取消
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
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
    //var merchantId = $("#merchantId").val();
    //var routerId = document.getElementById("routerId").value;
    //var channelId = $("#channelId").val();
    //var status = document.getElementById("status").value;
    //var tradeState = document.getElementById("tradeState").value;
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;

    //var merchantOrderNo = document.getElementById("merchantOrderNo").value;

    function derived() {
        var form = $('<form></form>');
        $(document.body).append(form);
        form.attr('action', "${ctx}/consume/derived");
        form.attr('method', 'post');
        form.attr('target', '_self');
        var my_input1 = $('<input type="text" name="merchantId"/>');
        if (merchantId == null) {
            my_input1.attr('value', "");
        } else {
            my_input1.attr('value', merchantId);
        }
        var my_input2 = $('<input type="text" name="routerId" />');
        my_input2.attr('value', routerId);
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
</html>