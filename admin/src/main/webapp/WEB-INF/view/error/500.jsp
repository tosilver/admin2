<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<%@ page isErrorPage="true" %>
<%
    response.setStatus(HttpServletResponse.SC_OK);
    String path = request.getContextPath();
%>
<html>
<head>
    <title>500</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">500</strong> /
                <small>That’s an error</small>
            </div>
        </div>
        <hr>
        <div class="am-g" style="padding:0px 30px;">
            <div>系统执行发生错误，信息描述如下：</div>
            <div>错误状态代码是：${pageContext.errorData.statusCode}</div>
            <div>错误发生页面是：${pageContext.errorData.requestURI}</div>
            <div>错误信息：${pageContext.exception}</div>
            <div>
                错误堆栈信息：<br/>
                <c:forEach var="trace" items="${pageContext.exception.stackTrace}">
                    <div>${trace}</div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>