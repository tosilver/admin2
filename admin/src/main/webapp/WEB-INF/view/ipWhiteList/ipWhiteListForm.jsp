<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>IP白名单管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">IP白名单管理</strong> /
                <small>IP白名单${empty ipWhiteList.id?'添加':'修改'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" method="post" action="${ctx}/ipWhiteList/save">
                    <input type="hidden" name="id" value="${ipWhiteList.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select name="merchant.id"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}" required>
                                <option></option>
                                <c:forEach items="${merchantList }" var="merchant">
                                    <option value="${merchant.id }"
                                            <c:if test="${merchant.id == ipWhiteList.merchant.id}">selected</c:if>>${merchant.company }&nbsp;&nbsp;${merchant.contacts }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">IP：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="ip" class="am-input-sm" value="${ipWhiteList.ip}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">描述：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="desc" class="am-input-sm" value="${ipWhiteList.desc}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">状态：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <%--<input type="text" name="status" class="am-input-sm" value="${ipWhiteList.status==0?'停用':'可用'}" required />--%>
                            <select name="status" data-am-selected="{btnWidth: '100%', maxHeight: 300}" required>
                                <option value="1" <c:if test="${ipWhiteList.status == 1}">selected</c:if>>可用</option>
                                <option value="0" <c:if test="${ipWhiteList.status == 0}">selected</c:if>>不可用</option>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <button type="submit" class="am-btn am-btn-primary am-btn-xs">保存</button>
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