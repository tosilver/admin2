<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商戶交易管理</title>
    <%@ include file="../include/head.jsp" %>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/assets/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        function upload() {
            document.fileForm.submit();
        }

        //下发id集合
        var xiafaIds = "";

        function addXiafaId(id) {
            xiafaIds += id + ","
        }


    </script>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">批付记录</strong> /
                <small>批付信息</small>
            </div>
        </div>
        <div class="am-u-sm-6" style="width: 70%;">
            <form id="searchForm" action="${ctx}/xiafa/list" method="get" class="am-form-inline">
                <input type="hidden" name="pageIndex" value="${page.pageIndex}"/>
                <input type="hidden" name="pageSize" value="${page.pageSize}"/>

                <div class="am-input-group am-input-group-sm">
                    <select data-am-selected="{btnWidth: '100px'}" placeholder="状态" name="status"
                            onchange="$('#searchForm').submit()">
                        <option value=" ">全部</option>
                        <option value="0" <c:if test="${page.params.status eq 0}">selected</c:if>>还未下发</option>
                        <option value="1" <c:if test="${page.params.status eq 1}">selected</c:if>>下发中</option>
                        <option value="2" <c:if test="${page.params.status eq 2}">selected</c:if>>下发成功</option>
                        <option value="3" <c:if test="${page.params.status eq 3}">selected</c:if>>下发失败</option>
                    </select>
                </div>

                <div class="am-input-group am-input-group-sm">

                    <input type="text" value="${page.params.startDate}" class="am-form-field" name="startDate"
                           id="startDate" onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'} )"
                           placeholder="起始时间"/>
                </div>

                <div class="am-input-group am-input-group-sm">
                    <input type="text" value="${page.params.endDate}" class="am-form-field" name="endDate"
                           id="endDate" onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'} )"
                           placeholder="结束时间"/>
                </div>

                <button type="submit" class="am-btn am-btn-sm am-radius"><span class="am-icon-search">搜索</span></button>
            </form>
        </div>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form">
                    <table id="contentTable" class="am-table am-table-striped am-table-hover table-main">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>收款人卡号</th>
                            <th>收款人姓名</th>
                            <th>商户id</th>
                            <th>交易金额</th>
                            <th>银行名称</th>
                            <td>创建时间</td>
                            <th>下发状态</th>
                            <th width="80px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="xiafa" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${xiafa.receiverCardNo}</td>
                                <td>${xiafa.receiverName}</td>
                                <td>${xiafa.merchantId}</td>
                                <td>${xiafa.amount}</td>
                                <td>${xiafa.bankName}</td>
                                <td><fmt:formatDate value='${xiafa.createTime}'
                                                    pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                <td>
                                    <c:if test="${xiafa.status == 0}">未下发</c:if>
                                    <c:if test="${xiafa.status == 1}">下发中</c:if>
                                    <c:if test="${xiafa.status == 2}">下发成功</c:if>
                                    <c:if test="${xiafa.status == 3}">下发失败</c:if></td>
                                <td>
                                    <c:if test="${xiafa.status == 0}"><a
                                            href="${ctx}/xiafa/pay?id=${xiafa.id}">确认下发</a></c:if>
                                    <c:if test="${xiafa.status == 1}">等待下发</c:if>
                                    <c:if test="${xiafa.status == 2}">下发完毕</c:if>
                                    <c:if test="${xiafa.status == 3}"><a
                                            href="${ctx}/xiafa/pay?id=${xiafa.id}">再次下发</a></c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><a href="${ctx}/xiafa/payMany?ids=${ids}"
                                   style="color:darkblue;">一键下发</a></td>
                            <td></td>
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
</html>
