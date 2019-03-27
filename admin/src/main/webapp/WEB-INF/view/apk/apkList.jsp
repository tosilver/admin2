<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>apk管理</title>
    <%@ include file="../include/head.jsp" %>
    <script type="text/javascript">

        function next() {

            window.location = "${ctx}/apk/save";

        }

    </script>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">apk记录</strong> /
                <small>apk信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/apk/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>

                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status"
                                onchange="$('#searchForm').submit()">
                            <option value=" ">全部</option>
                            <option value="-1" <c:if test="${page.params.status eq -1}">selected</c:if>>已失效</option>
                            <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>可用</option>
                        </select>
                    </div>

                    <input type="button" value="上传" onclick="next()">
                </form>
            </div>
        </div>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form">
                    <table id="contentTable" class="am-table am-table-striped am-table-hover table-main">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>应用名</th>
                            <th>应用版本</th>
                            <th>状态</th>
                            <th>上传时间</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="apkFile" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${apkFile.name}</td>
                                <td>${apkFile.version}</td>
                                <td>
                                    <c:if test="${apkFile.status == 1}">可用</c:if>
                                    <c:if test="${apkFile.status == -1}">已失效</c:if>
                                </td>
                                <td><fmt:formatDate value='${apkFile.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                        <%--<input type="button" value="更新" onclick="next()">--%>
                                    <a href="${ctx}/apk/form?id=${apkFile.id}">更新</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
                <%@ include file="../include/pagination.jsp" %>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
</script>
