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
                <small>生成订单</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form name="merchantPaySave" class="am-form" action="${ctx}/merchantPay/save" method="post">
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select name="merchantId" id="merchantId"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300}" required>
                                <option value="-1">请选择商户</option>
                                <c:forEach items="${merchantList}" var="merchant">
                                    <option value="${merchant.id }"
                                            <c:if test="${merchant.id == merchantPay.merchantId}">selected</c:if>>${merchant.company}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">充值金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="amount" class="am-input-sm" value="${merchantPay.amount}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="isChoose()">保存
                            </button>
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
    function isChoose() {
        var merchantId = document.getElementById("merchantId").value;

        if (merchantId == -1) {
            window.alert("请选择商户");
        } else {
            document.merchantPaySave.submit();
        }
    }
</script>
</html>
