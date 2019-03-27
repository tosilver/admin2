<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>渠道管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">渠道管理</strong> /
                <small>渠道信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-6" style="width:30%;">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <button type="button" class="am-btn am-btn-default"
                                onclick="window.location='${ctx}/channel/form'">
                            <span class="am-icon-plus"></span> 新增
                        </button>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-6" style="width: 70%;">
                <form id="searchForm" action="${ctx}/channel/list" method="get" class="am-form-inline">
                    <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                    <input type="hidden" name="pageSize" value="${page.pageSize}"/>
                    <div class="am-input-group am-input-group-sm">
                        <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status"
                                onchange="$('#searchForm').submit()">
                            <option value="0" <c:if test="${page.params.status eq 0}">selected</c:if>>全部</option>
                            <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>正常</option>
                            <option value="-1" <c:if test="${page.params.status eq -1}">selected</c:if>>已冻结</option>
                        </select>
                    </div>
                    <div class="am-input-group am-input-group-sm">
                        <input type="text" style="width:200px;" class="am-form-field" name="keyword"
                               value="${page.params.keyword}" placeholder="渠道名/手机号/渠道描述">
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
                            <th>产品名</th>
                            <th>产品类型</th>
                            <th>商品类别</th>
                            <th>渠道名</th>
                            <%--<th>初始额度</th>--%>
                            <th>剩余额度</th>
                            <th>上游代付成本</th>
                            <th>上游成本费率</th>
                            <th>单笔交易最高限额</th>
                            <th>单笔交易最低限额</th>
                            <th>支付宝分账百分比</th>
                            <th>渠道描述</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="channel" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${channel.router.name}</td>
                                <td>
                                    <c:if test="${channel.product == 1}">微信</c:if>
                                    <c:if test="${channel.product == 2}">支付宝</c:if>
                                    <c:if test="${channel.product == 3}">银生宝</c:if>
                                </td>
                                <td>${channel.goodsType.name}</td>
                                <td>${channel.name}</td>
                                    <%--<td>${channel.amountInit}</td>--%>
                                <td>${channel.amountLimit}</td>
                                <td>${channel.payCost}</td>
                                <td>${channel.costRate}</td>
                                <td>${empty channel.unitPrice ? "" : channel.unitPrice}</td>
                                <td>${empty channel.unitPrice ? "" : channel.minPrice}</td>
                                <td>${channel.fzPercentage}</td>
                                <td>${channel.dstDescribe}</td>
                                <td>${channel.status==-1?"<font color='#FF0000'>停用</font>":"可用"}</td>
                                <td><fmt:formatDate value="${channel.createTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <a href="${ctx}/channel/form?id=${channel.id}">详情</a>
                                    <a href="${ctx}/channel/delete?id=${channel.id}"
                                       onclick="return confirm('确认要删除该条数据吗？', this.href)">删除</a>
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
</html>