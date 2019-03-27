<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商户结算信息</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商户结算信息</strong> /
                <small>结算信息${empty settlement.id?'添加':'修改'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form name="settlement" class="am-form" action="${ctx}/settlement/save" method="post">
                    <input type="hidden" name="id" value="${settlement.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select id="merchantId" name="merchant.id"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}" required>
                                <option value="-1">请选择商户</option>
                                <c:forEach items="${merchantList }" var="merchant">
                                    <option value="${merchant.id }"
                                            <c:if test="${merchant.id == settlement.merchant.id}">selected</c:if>>${merchant.company }&nbsp;&nbsp;${merchant.contacts }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">结算类型：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select id="settleType" name="settleType"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}" required>
                                <option value="-1">请选择结算类型</option>
                                <c:forEach items="${settleTypes }" var="item">
                                    <option value="${item }"
                                            <c:if test="${item == settlement.settleType}">selected</c:if>>${item.text}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">账户类型：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select id="accountType" name="accountType"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}" required>
                                <option value="-1">请选择账户类型</option>
                                <c:forEach items="${accountTypes }" var="item">
                                    <option value="${item }"
                                            <c:if test="${item == settlement.accountType}">selected</c:if>>${item.text}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>


                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">银行账户：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="bankAccount" class="am-input-sm" value="${settlement.bankAccount}"
                                   onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" placeholder="请输入账号"
                                   required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">开户银行：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="bankName" class="am-input-sm" value="${settlement.bankName}"
                                   required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">银行名称：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="bankNickname" class="am-input-sm"
                                   value="${settlement.bankNickname}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>


                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <button type="button" onclick="isChoose()" class="am-btn am-btn-primary am-btn-xs">保存
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
        var settleType = document.getElementById("settleType").value;
        var accountType = document.getElementById("accountType").value;
        if (merchantId == -1) {
            window.alert("请选择商户");
        } else if (settleType == -1) {
            window.alert("请选择结算类型");
        } else if (accountType == -1) {
            window.alert("请选择账户类型");
        } else {
            document.settlement.submit();
        }
    }
</script>
</html>