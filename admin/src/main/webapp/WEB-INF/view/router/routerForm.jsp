<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>路由配置信息</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">路由配置管理</strong> /
                <small>路由配置${empty functionParameter.id?'添加':'修改'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="${ctx}/router/save" method="post">

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">服务名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="id" class="am-input-sm" value="${router.id}" required
                                   <c:if test="${not empty router.id}">readonly</c:if>/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">路由名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="name" class="am-input-sm" value="${router.name}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">版本：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="version" class="am-input-sm" value="${router.version}"
                                   onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" placeholder="请输入版本号"
                                   required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <c:if test="${empty router.id}">
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