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
                <form class="am-form" action="${ctx}/orderYfbPayroll/save" method="post">
                    <input type="hidden" name="id" value="${yfbPayroll.id}"/>
                    <c:if test="${not empty yfbPayroll.id}">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">交易流水号：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <input type="text" name="serialNo" class="am-input-sm" value="${yfbPayroll.serialNo}"
                                       readonly/>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                    </c:if>
                    <c:if test="${not empty yfbPayroll.merchantId}">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">商户ID：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <input type="text" name="merchantId" class="am-input-sm"
                                       value="${yfbPayroll.merchantId}" readonly/>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">商户名：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <input type="text" name="merchantId" class="am-input-sm" value="${yfbPayroll.company}"
                                       readonly/>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                    </c:if>
                    <c:if test="${empty yfbPayroll.merchantId}">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">商户：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <select id="merchantId" name="merchantId"
                                        data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}" required>
                                    <option value="-1">请选择商户</option>
                                    <c:forEach items="${merchantList }" var="merchant">
                                        <option value="${merchant.id }">${merchant.company }&nbsp;&nbsp;${merchant.contacts }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                    </c:if>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">支付订单号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="orderName" class="am-input-sm" value="${yfbPayroll.orderName}"
                                   placeholder="请输入支付订单号" ${empty yfbPayroll.orderName?'':'readonly'}/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <c:if test="${not empty yfbPayroll.createTime}">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">处理时间：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <input type="text" name="createTime" class="am-input-sm"
                                       value="<fmt:formatDate value="${yfbPayroll.createTime}" pattern='yyyy-MM-dd HH:mm:ss' />"
                                       readonly/>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                    </c:if>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">客户名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="receiverName" class="am-input-sm"
                                   value="${yfbPayroll.receiverName}"
                                   placeholder="请输入客户名" ${empty yfbPayroll.receiverName?'':'readonly'}/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">客户银行卡号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="receiverCardNo" class="am-input-sm"
                                   value="${yfbPayroll.receiverCardNo}"
                                   placeholder="请输入客户银行卡号" ${empty yfbPayroll.receiverCardNo?'':'readonly'}/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">开户行名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="bankName" class="am-input-sm" value="${yfbPayroll.bankName}"
                                   placeholder="请输入开户行名" ${empty yfbPayroll.bankName?'':'readonly'}/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">代付金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="amount" class="am-input-sm" value="${yfbPayroll.amount}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入代付金额" ${empty yfbPayroll.amount?'':'readonly'}/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户代付成本：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="merchantPayCost" class="am-input-sm"
                                   value="${empty yfbPayroll.merchantPayCost?0.00:yfbPayroll.merchantPayCost}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入代付成本" ${empty yfbPayroll.merchantPayCost?'':'readonly'}/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <c:if test="${not empty yfbPayroll.status}">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">订单状态：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <input type="text" name="status" class="am-input-sm" value="${yfbPayroll.status}"
                                       readonly/>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                    </c:if>

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