<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>申请提现管理</title>
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
    </script>

    <script>
        $(function () {
            $('#queding').click(function () {
                // console.log(document.getElementById("consumeId").value)
                // console.log(document.getElementById("moneys").value)
                $.ajax({
                    type: "get",
                    url: "${ctx}/withdraw/updateStatus",
                    data: {
                        id: document.getElementById("withdrawId").value,
                        status: 1,
                        moneys: document.getElementById("moneys").value
                    },
                    // dataType: "json",
                    success: function (data) {
                        console.log(data)
                        window.location.reload();
                        // location.reload([bForceGet])
                    }
                });
            });
            $('#bohui').click(function () {
                $.ajax({
                    type: "get",
                    url: "${ctx}/withdraw/updateStatus",
                    data: {
                        id: document.getElementById("withdrawId").value,
                        status: 2,
                        moneys: document.getElementById("moneys").value

                    },
                    // dataType: "json",
                    success: function (data) {
                        console.log(data)
                        window.location.reload();
                        // location.reload([bForceGet])
                    }
                });
            });

        });
    </script>
</head>
<body>
<%@ include file="../include/alert.jsp" %>

<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">提现记录</strong> /
                <small>提现信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width: 30%;">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <strong class="am-text-primary am-text-lg">申请金额：${not empty sumMoney ? sumMoney : "0.0"}元</strong>
                        /
                        <small>${page.totalCount}笔</small>
                        <br>
                        <strong class="am-text-primary am-text-lg">到账金额：${not empty sumMoney ? sumMoney : "0.0"}元</strong>
                        /
                        <small>${accountCount}笔</small>
                        <br>
                        <shiro:hasAnyRoles name="superadmin">
                            <strong class="am-text-primary am-text-lg">申请成功率：${not empty successRate ? successRate : "0.0"}%</strong><br>
                        </shiro:hasAnyRoles>
                        <c:if test="${balance != null}">
                            <strong class="am-text-primary am-text-lg">商户可提现余额：${not empty balance ? balance : "0.0"}元</strong><br>
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="am-u-sm-6" style="width: 30%">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <button type="button" class="am-btn am-btn-default"
                                onclick="window.location='${ctx}/withdraw/form'">
                            <span class="am-icon-plus"></span> 新增申请
                        </button>
                        *申请时间每天10:00-19:00
                    </div>
                </div>
            </div>

            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/withdraw/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>

                    <div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:120px;" class="am-form-field" name="merchantOrderNo"
                               id="merchantOrderNo" value="${page.params.tradeNo}"
                               onKeyUp="value=value.replace(/[\W]/g,'')" placeholder="申请单号">
                    </div>

                    <%--<shiro:hasAnyRoles name="admin,superadmin">--%>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '120px'}" placeholder="商户" name="merchantId"
                                id="merchantId" onchange="$('#searchForm').submit()">
                            <option value=" ">所有商户</option>
                            <c:forEach items="#{merchantList}" var="merchant">
                                <option value="${merchant.id}"
                                        <c:if test="${merchant.id eq page.params.merchantId}">selected</c:if>>${merchant.company}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="审批状态" name="status"
                                id="status" onchange="$('#searchForm').submit()">
                            <option value=" ">全部</option>
                            <option value="0" <c:if test="${page.params.status eq 0}">selected</c:if>>审批中</option>
                            <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>审批通过</option>
                            <option value="2" <c:if test="${page.params.status eq 2}">selected</c:if>>驳回
                            </option>
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
                            <th>提现单号</th>
                            <th>商户名</th>
                            <th>银行卡号</th>
                            <%--<shiro:hasAnyRoles name="admin,superadmin">--%>
                            <th>银行名称</th>
                            <th>户主姓名</th>
                            <%--</shiro:hasAnyRoles>--%>
                            <th class="xians">申请金额</th>
                            <th>申请时间</th>
                            <th>审批状态</th>
                            <th>审批时间</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="withdraw" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${withdraw.tradeNo}</td>
                                <td>${withdraw.merchantCompany}</td>
                                <td>${withdraw.cardNo}</td>
                                <td>${withdraw.bankName}</td>
                                <td>${withdraw.customerName}</td>
                                <td class="xians">${withdraw.amount}</td>
                                <td><fmt:formatDate value='${withdraw.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <c:if test="${withdraw.status == 0}">审批中</c:if>
                                    <c:if test="${withdraw.status == 1}">审批通过</c:if>
                                    <c:if test="${withdraw.status == 2}">驳回</c:if>
                                </td>
                                <td>${withdraw.appTime == null?'':withdraw.appTime}</td>
                                <td>
                                    <a href="${ctx}/withdraw/form?id=${withdraw.id}">详情</a>
                                    <shiro:hasAnyRoles name="admin,superadmin">
                                        <c:if test="${withdraw.status != 1 and withdraw.status != 2 }">
                                            <a href="#" id="comPay"
                                               onClick="document.getElementsByClassName('tac')[0].style.display = 'block';document.getElementById('withdrawId').value='${withdraw.id}';document.getElementById('moneys').value=${withdraw.amount};">审批</a>

                                            <div class="tac">
                                                <div class="tac-content">
                                                    <div class="title">确认审批</div>
                                                    <div class="tac-text">
                                                        <input value="" type="hidden" id="withdrawId">
                                                        <span>请输入金额(请输入金额四位数的正确数据):</span>
                                                        <input type="text" value=""
                                                               onkeyup="if( ! /^([1-9]\d{0,3}|0)([.]?|(\.\d{1,2})?)$/.test(this.value)){this.value='';}"
                                                               placeholder="请输入金额" id="moneys">
                                                    </div>
                                                    <div>
                                                        <button id="queding">审批</button>
                                                        <button id="bohui">驳回</button>
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
    var merchantId = $("#merchantId").val();
    var routerId = document.getElementById("routerId").value;
    var channelId = $("#channelId").val();
    var status = document.getElementById("status").value;
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    var merchantOrderNo = document.getElementById("merchantOrderNo").value;

    function derived() {
        var form = $('<form></form>');
        $(document.body).append(form);
        form.attr('action', "${ctx}/withdraw/derived");
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