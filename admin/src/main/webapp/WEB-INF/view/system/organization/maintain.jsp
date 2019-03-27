<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>组织机构</title>
    <%@ include file="../../include/head.jsp" %>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-g admin-form">
            <form id="form" class="am-form am-form-horizontal" modelAttribute="organization" method="post">
                <input type="hidden" name="id" value="${organization.id}"/>
                <input type="hidden" name="available" value="${organization.available}"/>
                <input type="hidden" name="parentId" value="${organization.parentId}"/>
                <input type="hidden" name="parentIds" value="${organization.parentIds}"/>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">名称：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" id="name" name="name" value="${organization.name}"/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6">*必填</div>
                </div>
                <div class="am-margin">
                    <shiro:hasPermission name="organization:update">
                        <button type="button" id="updateBtn">修改</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="organization:delete">
                        <c:if test="${not organization.rootNode}">
                            <button type="button" id="deleteBtn">删除</button>
                        </c:if>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="organization:create">
                        <button type="button" id="appendChildBtn">添加子节点</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="organization:update">
                        <c:if test="${not organization.rootNode}">
                            <button type="button" id="moveBtn">移动节点</button>
                        </c:if>
                    </shiro:hasPermission>
                </div>

            </form>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#updateBtn").click(function () {
            if ($("#name").val() == "") {
                alert("名称必填");
                return;
            }
            $("#form")
                .attr("action", "${ctx}/organization/${organization.id}/update")
                .submit();
            return false;
        });
        $("#deleteBtn").click(function () {
            if (confirm("确认删除吗？")) {
                $("#form")
                    .attr("action", "${ctx}/organization/${organization.id}/delete")
                    .submit();
            }
            return false;
        });

        $("#appendChildBtn").click(function () {
            location.href = "${ctx}/organization/${organization.id}/appendChild";
            return false;
        });

        $("#moveBtn").click(function () {
            location.href = "${ctx}/organization/${organization.id}/move";
            return false;
        });
    });
</script>
</body>
</html>