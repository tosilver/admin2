<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商户交易记录</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">交易记录详情</strong> /
                <small>交易详情</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="${ctx}/consume/save" method="post">
                    <input type="hidden" name="id" value="${consume.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">交易流水号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="id" class="am-input-sm" value="${consume.id}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="merchantId" class="am-input-sm" value="${consume.merchant.company}"
                                   readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <shiro:hasPermission name="admin:create">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-u-md-2 am-text-right">通道名：</div>
                            <div class="am-u-sm-8 am-u-md-4">
                                <input type="text" name="channel.name" class="am-input-sm"
                                       value="${consume.channel.name}" required/>
                            </div>
                            <div class="am-hide-sm-only am-u-md-6"></div>
                        </div>
                    </shiro:hasPermission>
                    <select data-am-selected="{btnWidth: '120px'}" placeholder="筛选产品" name="channelId"
                            onchange="$('#searchForm').submit()">
                        <option value=" ">所有渠道</option>
                        <c:forEach items="#{channelList}" var="channel">
                            <option value="${channel.id}"
                                    <c:if test="${channel.id eq page.params.channelId}">selected</c:if>>${channel.name}</option>
                        </c:forEach>
                    </select>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">耗时：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="time" class="am-input-sm" value="${consume.time}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*单位毫秒</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">交易金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="totalAmount" class="am-input-sm" value="${consume.totalAmount}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">交易时间：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="createTime" class="am-input-sm"
                                   value="<fmt:formatDate value="${consume.createTime}" pattern='yyyy-MM-dd HH:mm:ss'/>"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>


                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商户订单号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="merchantOrderNo" class="am-input-sm"
                                   value="${consume.merchantOrderNo}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">支付订单号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="payOrderNo" class="am-input-sm" value="${consume.payOrderNo}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">成本费率：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="costRate" class="am-input-sm" value="${consume.costRate}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">代付成本：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="payCost" class="am-input-sm" value="${consume.payCost}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">手续费：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="serviceCharge" class="am-input-sm"
                                   value="${consume.serviceCharge}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">到账金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="accountAmount" class="am-input-sm"
                                   value="${consume.accountAmount}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">支付状态：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="tradeState" class="am-input-sm" value="${consume.tradeState}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>


                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">请求摘要：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <textarea cols="20" rows="4" name="request">${consume.request}</textarea>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">响应结果：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <textarea cols="20" rows="4" name="reponse">${consume.response}</textarea>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <c:if test="${empty consume.id}">
                                <button type="submit" class="am-btn am-btn-primary am-btn-xs">保存</button>
                            </c:if>
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