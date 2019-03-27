<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>分账账户管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">分账账户管理</strong> /
                <small>账户${empty transin.id?'添加':'修改'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form name="transinForm" class="am-form" action="${ctx}/transin/save" method="post">
                    <input type="hidden" name="id" value="${transin.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">收款账户pid：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="pid" class="am-input-sm" value="${transin.pid}"
                                   placeholder="请输入支付宝分账pid" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">收款人姓名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="realname" class="am-input-sm" value="${transin.realname}"
                                   placeholder="请输入姓名" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-u-sm-8 am-u-sm-centered">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-text-right"></div>
                            <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="isChoose()">保存
                            </button>
                            <c:if test="${transin.status==1}">
                                <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="updateStatus(0)">
                                    冻结
                                </button>
                            </c:if>
                            <c:if test="${transin.status==0}">
                                <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="updateStatus(1)">
                                    启用
                                </button>
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
    function updateStatus(statusValue) {
        if (confirm('是否确认修改')) {
            window.location = '/transin/updateStatus?id=${transin.id}&status=' + statusValue;
        }
    }

    function isChoose() {
        document.transinForm.submit();
    }

</script>
</html>