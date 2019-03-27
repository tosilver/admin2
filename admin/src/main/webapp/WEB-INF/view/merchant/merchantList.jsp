<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商戶信息管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商戶管理</strong> /
                <small>商戶信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width: 30%">
                <div class="am-btn-toolbar">
                    <shiro:hasAnyRoles name="admin,superadmin,agency">
                        <div class="am-btn-group am-btn-group-xs">
                            <button type="button" class="am-btn am-btn-default"
                                    onclick="window.location='${ctx}/merchant/form'">
                                <span class="am-icon-plus"></span> 新增
                            </button>
                        </div>
                    </shiro:hasAnyRoles>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 70%">
                <form id="searchForm" action="${ctx}/merchant/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>
                    <div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:100px;" class="am-form-field" name="company"
                               value="${page.params.company}" placeholder="商户名称">
                    </div>
                    <div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:100px;" class="am-form-field" name="mobile"
                               value="${page.params.mobile}" placeholder="电话">
                    </div>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status"
                                onchange="$('#searchForm').submit()">
                            <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>正常</option>
                            <option value="-1" <c:if test="${page.params.status eq -1}">selected</c:if>>已冻结</option>
                        </select>
                    </div>
                    <div class="am-input-group am-input-group-sm">
                        <div class="am-input-group am-datepicker-date" style="width:150px;"
                             data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
                            <input type="text" name="startDate" value="${page.params.startDate}" class="am-form-field"
                                   placeholder="起始时间" readonly>
                            <span class="am-input-group-btn am-datepicker-add-on">
                                <button class="am-btn am-btn-default" type="button">
                                    <span class="am-icon-calendar"></span>
                                </button>
                            </span>
                        </div>
                    </div>
                    <div class="am-input-group am-input-group-sm">
                        <div class="am-input-group am-datepicker-date" style="width:150px;"
                             data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
                            <input type="text" name="endDate" value="${page.params.endDate}" class="am-form-field"
                                   placeholder="结束时间" readonly>
                            <span class="am-input-group-btn am-datepicker-add-on">
                                <button class="am-btn am-btn-default" type="button">
                                    <span class="am-icon-calendar"></span>
                                </button>
                            </span>
                        </div>
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
                            <th>商户ID</th>
                            <th>商户名</th>
                            <th>账户总余额</th>
                            <th>入账余额</th>
                            <th>出账冻结金额</th>
                            <th>可提现余额</th>
                            <th>提现冻结金额</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="merchant" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${merchant.id}</td>
                                <td>${merchant.company}</td>
                                <td>${merchant.balance}</td>
                                <td>${merchant.accountBalance}</td>
                                <td>${merchant.accountFrozen}</td>
                                <td>${merchant.withdrawBalance}</td>
                                <td>${merchant.withdrawFrozen}</td>
                                <td>${merchant.status == 1 ? '正常':'已冻结'}</td>
                                <td>
                                    <fmt:formatDate value="${merchant.createTime}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                </td>
                                <td>
                                    <shiro:hasPermission name="admin:create">
                                        <a href="${ctx}/merchant/update?id=${merchant.id}">修改</a>
                                    </shiro:hasPermission>
                                    <a href="${ctx}/merchant/form?id=${merchant.id}">详情</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr style="font-size: 11px">*账号总金额=入账余额+可提现余额 <br/>
                            *入账余额：可直接通过接口代付的余额．例：快捷类产品 <br/>
                            *可申请余额：只可通过提交申请进行银行转账的余额．例：ｍａｌｌ
                        </tr>
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