<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商户费率信息</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商户费率管理</strong> /
                <small>商户费率${empty merchantRate.id?'添加':'修改'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form name="merchantRateForm" class="am-form" action="${ctx}/merchantRate/save" method="post">
                    <input type="hidden" name="id" value="${merchantRate.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select id="merchantId" name="merchant.id"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}" required>
                                <option value="-1">请选择商户</option>
                                <c:forEach items="${merchantList }" var="merchant">
                                    <option value="${merchant.id }"
                                            <c:if test="${merchant.id == merchantRate.merchant.id}">selected</c:if>>${merchant.company }&nbsp;&nbsp;${merchant.contacts }</option>
                                </c:forEach>
                            </select>
                            <c:if test="${not empty merchantRate.id}">
                                <script type="text/javascript">
                                    $('select[id=merchantId]').selected('disable');
                                </script>
                            </c:if>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">产品：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select id="routerId" name="router.id"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300}">
                                <option value="-1">请选择产品</option>
                                <c:forEach items="${routerList }" var="router">
                                    <option value="${router.id }"
                                            <c:if test="${router.id == merchantRate.router.id}">selected</c:if>>${router.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">结算费率：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="costRate" class="am-input-sm" value="${merchantRate.costRate}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入费率" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">代付成本：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="payCost" class="am-input-sm" value="${merchantRate.payCost}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">提现成本：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="withdrawCost" class="am-input-sm" value="${merchantRate.withdrawCost}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <c:if test="${not empty merchantRate.createTime}">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">创建时间：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <input type="text" class="am-input-sm"
                                       value="<fmt:formatDate value="${merchantRate.createTime}" pattern='yyyy-MM-dd HH:mm:ss'/>"
                                       readonly/>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                    </c:if>
                    <c:if test="${not empty merchantRate.updateTime}">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">更新时间：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <input type="text" class="am-input-sm"
                                       value="<fmt:formatDate value="${merchantRate.updateTime}" pattern='yyyy-MM-dd HH:mm:ss'/>"
                                       readonly/>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                    </c:if>
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
        var routerId = document.getElementById("routerId").value;
        if (merchantId == -1) {
            window.alert("请选择商户");
        } else if (routerId == -1) {
            window.alert("请选择产品");
        } else {
            document.merchantRateForm.submit();
        }
    }
</script>
</html>