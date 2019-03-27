<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>合作商管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商户充值管理</strong> /
                <small>充值${empty merchantPay.id?'添加':'修改'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="${ctx}/merchantPay/save" method="post">
                    <input type="hidden" name="id" value="${merchantPay.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" class="am-input-sm" value="${merchantPay.company}" readonly/>

                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户ID：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" class="am-input-sm" value="${merchantPay.merchantId}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">订单流水号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" class="am-input-sm" value="${merchantPay.orderId}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">充值金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="amount" class="am-input-sm" value="${merchantPay.amount}"
                                   onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" placeholder="请输入金额"
                                   readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">操作人：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" class="am-input-sm" value="${merchantPay.operator}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">订单状态：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" class="am-input-sm" value="${merchantPay.status}" readonly/>
                        </div>

                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <%--<button type="submit" class="am-btn am-btn-primary am-btn-xs">保存</button>--%>
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
