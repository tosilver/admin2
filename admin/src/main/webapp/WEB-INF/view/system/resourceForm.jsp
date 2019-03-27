<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../include/taglib.jsp" %>
<html>
<head>
    <title>资源编辑</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">资源管理</strong> /
                <small>资源${not empty resource.id?'修改':'添加'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g admin-form">
            <form class="am-form am-form-horizontal" action="/resource/${empty rootResourceList?'appendChild':'save'}"
                  method="post">
                <input type="hidden" name="id" value="${resource.id}"/>
                <input type="hidden" name="parentId" value="${resource.parentId}"/>
                <input type="hidden" name="parentIds" value="${resource.parentIds}"/>
                <input type="hidden" name="available" value="${resource.available}"/>
                <%--<input type="hidden" name="parentId" value="${resource.parentId}" />--%>
                <%--<input type="hidden" name="parentIds" value="${resource.parentIds}" />--%>
                <c:if test="${not empty rootResourceList}">
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">父节点：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                                <%--<input type="text" value="${parent.name}" disabled="disabled" />--%>
                            <select name="parentId" data-am-selected="{btnWidth: '100%', dropUp: 0}" required>
                                <c:forEach var="item" items="${rootResourceList }">
                                    <option value="${item.id }"
                                            <c:if test="${resource.parentId eq item.id}">selected</c:if>>${item.name }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                </c:if>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right"><c:if test="${not empty parent}">子</c:if>名称：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input name="name" type="text" value="${resource.name}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6">*必填</div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">类型：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <select data-am-selected="{btnWidth: '100%', dropUp: 0}" name="type" data="${resource.type}">
                            <c:forEach items="${resourceTypes}" var="m">
                                <option value="${m}">${m.text}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">URL路径：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input name="url" type="text" value="${resource.url}"/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">权限字符串：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input name="permission" type="text" value="${resource.permission}"/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">顺序号：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input name="sort" type="number" value="${resource.sort}"/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">&nbsp;</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <button type="button" class="am-btn am-btn-default" onclick="history.go(-1)">返 回</button>
                        <button type="submit" class="am-btn am-btn-primary">保 存</button>
                        <c:if test="${not resource.rootNode}">
                            <%-- <shiro:hasPermission name="resource:delete"> --%>
                            <button type="button" class="am-btn am-btn-danger" onclick="del(${resource.id})">删 除
                            </button>
                            <%-- </shiro:hasPermission> --%>
                        </c:if>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        initSelectValue(true);//初始化下拉框的值
    });

    function del(id) {
        if (confirm("确认要删除?")) {
            $.post('/resource/delete', {
                id: id
            }, function (ret) {
                if (ret.code == 1) {
                    alert(ret.msg);
                    location.href = "${pageContext.request.contextPath}/resource";
                } else {
                    alert(ret.msg);
                }
            });
        }
    }
</script>
</html>