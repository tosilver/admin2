<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>Title</title>
</head>

<script type="text/javascript">
    <%--<c:if test="${qrcode != null}">
    window.open("${qrcode}");
    </c:if>--%>
</script>
<body>
<c:if test="${parameter != null}">
    <div>
        <label>请求参数</label>
        <input name="parameter" type="text" class="form-control" value="${parameter}">
    </div>
</c:if>
<c:if test="${mallAddress != null}">
    <div>
        <label>请求商城地址</label>
        <input name="mallAddress" type="text" class="form-control" value="${mallAddress}">
    </div>
</c:if>
<c:if test="${qrcode != null}">
    <div>
        <label>支付链接</label>
        <input name="tradeNo" type="text" class="form-control" value="${qrcode}">
        <a href="${qrcode}" target="_blank">点击访问</a>
    </div>
</c:if>
<c:if test="${RequestedURL != null}">
    <div>
        <label>请求链接</label>
        <input name="RequestedURL" type="text" class="form-control" value="${RequestedURL}">
        <a href="${RequestedURL}" target="_blank">点击请求</a>
    </div>
</c:if>
<c:if test="${code != null}">
    <div>
        <label>错误码</label>
        <input name="code" type="text" class="form-control" value="${code}">
    </div>
</c:if>
<c:if test="${msg != null}">
    <div>
        <label>提示</label>
        <input name="msg" type="text" class="form-control" value="${msg}">
    </div>
</c:if>
</body>
</html>
