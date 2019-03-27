<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商户交易记录</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">代付订单详情</strong> /
                <small>订单信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="${ctx}/orderPayroll/save" method="post">
                    <input type="hidden" name="id" value="${payroll.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">交易流水号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="id" class="am-input-sm" value="${payroll.id}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户ID：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="merchantId" class="am-input-sm" value="${payroll.merchantId}"
                                   readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <%--<shiro:hasPermission name="admin:create">--%>
                    <%--<div class="am-g am-margin-top">--%>
                    <%--<div class="am-u-sm-4 am-u-md-2 am-text-right">通道名ID：</div>--%>
                    <%--<div class="am-u-sm-8 am-u-md-4">--%>
                    <%--<input type="text" name="channel.name" class="am-input-sm" value="${consume.channel.name}" required />--%>
                    <%--</div>--%>
                    <%--<div class="am-hide-sm-only am-u-md-6"></div>--%>
                    <%--</div>--%>
                    <%--</shiro:hasPermission>--%>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户订单号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="downOrderId" class="am-input-sm" value="${payroll.downOrderId}"
                                   readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">支付订单号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="upOrderId" class="am-input-sm" value="${payroll.upOrderId}"
                                   readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">处理时间：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="createTime" class="am-input-sm"
                                   value="<fmt:formatDate value="${payroll.createTime}" pattern='yyyy-MM-dd HH:mm:ss' />"
                                   readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>


                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">客户名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="name" class="am-input-sm" value="${payroll.name}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">客户银行卡号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="cardNo" class="am-input-sm" value="${payroll.cardNo}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">客户身份证号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="idCardNo" class="am-input-sm" value="${payroll.idCardNo}"
                                   readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">代付金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="amount" class="am-input-sm" value="${payroll.amount}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">渠道代付成本：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="channelPayCost" class="am-input-sm"
                                   value="${payroll.channelPayCost}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户代付成本：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="merchantPayCost" class="am-input-sm"
                                   value="${payroll.merchantPayCost}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">付款目的：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="purpose" class="am-input-sm" value="${payroll.purpose}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>


                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">付款摘要：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="summary" class="am-input-sm" value="${payroll.summary}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">订单状态：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="status" class="am-input-sm" value="${payroll.status}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">响应结果：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <textarea cols="20" rows="4" name="content" readonly>${payroll.content} </textarea>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <c:if test="${empty payroll.id}">
                                <button type="submit" class="am-btn am-btn-primary am-btn-xs">保存</button>
                            </c:if>
                            <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="history.go(-1)">返 回
                            </button>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>
</div>
</body>
<script type="text/javascript">
</script>
</html>