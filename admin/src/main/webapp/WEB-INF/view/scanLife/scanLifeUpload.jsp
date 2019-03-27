<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>

    <title>配置管理</title>
    <%@ include file="../include/head.jsp" %>

    <script>
        function getProduct() {
            $("#channelId").html("");
            $("#channelId").append("<option value=''>筛选产品</option>");
            var elementById = document.getElementById("routerId").value;
            $.get({
                url: '/scanLife/getChannel',
                data: {"routerId": elementById},
                async: false,
                success: function (data) {
                    //  console.info(data);

                    var len = data.length
                    for (var i = 0; i < len; i++) {
                        var e = data[i];
                        $("#channelId").append("<option value=" + e.id + ">" + e.name + "</option>");
                    }
                }

            })

        }

    </script>

</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">上传二维码</strong> /
                <small></small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="${ctx}/scanLife/upload" enctype="multipart/form-data" method="post">

                    <div class="am-hide-sm-only am-u-md-6"></div>
                    <select data-am-selected="{btnWidth: '120px'}" placeholder="商户" name="merchantId"
                            onchange="$('#searchForm').submit()" required>
                        <option value=" ">选择商户</option>
                        <c:forEach items="#{merchantList}" var="merchant">
                            <option value="${merchant.id}">${merchant.company}</option>
                        </c:forEach>
                    </select>
                    <div class="am-hide-sm-only am-u-md-6"></div>

                    <%--onchange="$('#searchForm').submit()"--%>

                    <div class="am-hide-sm-only am-u-md-6"></div>
                    <select data-am-selected="{btnWidth: '120px'}" placeholder="类型" name="routerId" id="routerId"
                            onchange="getProduct()" required>
                        <option value=" ">选择产品</option>
                        <c:forEach items="#{routerList}" var="router">
                            <option value="${router.id}">${router.name}</option>
                        </c:forEach>
                    </select>
                    <div class="am-hide-sm-only am-u-md-6"></div>

                    <div class="am-hide-sm-only am-u-md-6"></div>
                    <select data-am-selected="{btnWidth: '120px'}" placeholder="筛选产品" name="channelId" id="channelId"
                            onchange="$('#searchForm').submit()" required>
                        <option value=" ">选择渠道</option>
                        <%--<c:forEach items="#{channelList}" var="channel">--%>
                        <%--<option value="${channel.id}"<c:if test="${channel.product eq elementById}">selected</c:if>>${channel.name}</option>--%>
                        <%--</c:forEach>--%>
                    </select>
                    <div class="am-hide-sm-only am-u-md-6"></div>

                    <div class="am-hide-sm-only am-u-md-6"></div>
                    <select data-am-selected="{btnWidth: '120px'}" placeholder="类型" name="label"
                            onchange="$('#searchForm').submit()" required>
                        <option value=" ">选择码类型</option>
                        <option value="1" <c:if test="${page.params.label eq 1}">selected</c:if>>固额码</option>
                        <option value="2" <c:if test="${page.params.label eq 2}">selected</c:if>>任意码</option>
                    </select>
                    <div class="am-hide-sm-only am-u-md-6"></div>


                    <div class="am-g am-margin-top">


                        <div class="am-u-sm-8 am-u-md-4">
                            单个文件:<input type="file" name="scanFile"/><br>
                            多个文件:<input type="file" name="scanFileList" multiple/></br>

                            <button type="submit" class="am-btn am-btn-primary am-btn-xs">立即上传</button>
                            <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="history.go(-1)">返 回
                            </button>
                        </div>

                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>

                </form>
            </div>
        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>
</div>


</body>
<script>


</script>

</html>