<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商城地址管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商城地址管理</strong> /
                <small>商城地址信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width:30%;">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <button type="button" class="am-btn am-btn-default"
                                onclick="window.location='${ctx}/malladdress/form'">
                            <span class="am-icon-plus"></span> 新增
                        </button>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/malladdressControl/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status"
                                onchange="$('#searchForm').submit()">
                            <option value="">全部</option>
                            <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>正常</option>
                            <option value="0" <c:if test="${page.params.status eq 0}">selected</c:if>>冻结</option>
                        </select>
                    </div>
                    <button type="submit" class="am-btn am-btn-sm am-radius"><span class="am-icon-search">搜索</span>
                    </button>
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
                            <th>商户名称</th>
                            <th>支付宝通道</th>
                            <th>微信通道</th>
                            <th>快捷通道</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="malladdressControl" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${malladdressControl.merchantName}</td>
                                <td>${malladdressControl.zfbAccessName}</td>
                                <td>${malladdressControl.wxAccessName}</td>
                                <td>${malladdressControl.kjAccessName}</td>
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
</html>