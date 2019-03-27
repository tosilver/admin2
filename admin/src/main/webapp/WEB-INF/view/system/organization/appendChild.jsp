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
            <form id="form" class="am-form am-form-horizontal"
                  modelAttribute="child" method="post">
                <input type="hidden" name="id" value="${child.id}"/> <input
                    type="hidden" name="available" value="${child.available}"/> <input
                    type="hidden" name="parentId" value="${child.parentId}"/> <input
                    type="hidden" name="parentIds" value="${child.parentIds}"/>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">父节点名称：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" value="${parent.name}" disabled/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">子节点名称：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" id="name" name="name" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6">*必填</div>
                </div>
                <div class="am-margin">
                    <button type="submit" class="am-btn am-btn-primary">新增子节点</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>