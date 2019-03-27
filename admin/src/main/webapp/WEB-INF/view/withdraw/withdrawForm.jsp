<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>申请信息</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">提现申请</strong> /
                <small>申请${empty withdraw.id?'添加':'详情'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="${ctx}/withdraw/save" method="post">
                    <input type="hidden" name="id" value="${withdraw.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">银行卡号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="cardNo" class="am-input-sm" value="${withdraw.cardNo}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">银行名称：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="bankName" class="am-input-sm"
                                   value="${withdraw.bankName}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">户主姓名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="customerName" class="am-input-sm"
                                   value="${withdraw.customerName}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">申请金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="amount" class="am-input-sm" value="${withdraw.amount}"
                                   required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <c:if test="${empty withdraw.id}">
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
</html>