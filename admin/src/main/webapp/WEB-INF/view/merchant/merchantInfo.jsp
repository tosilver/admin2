<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>商户信息</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商户信息</strong> /
                <small>基本信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <form class="am-form" action="${ctx}/merchant/save" method="post">
                <input type="hidden" name="id" value="${merchant.id}"/>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">公司名：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="company" class="am-input-sm" value="${merchant.company}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <shiro:hasPermission name="admin:create">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">账户总余额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="balance" class="am-input-sm" value="${merchant.balance}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </shiro:hasPermission>
                </div>
                <div class="am-g am-margin-top">
                    <shiro:hasPermission name="admin:create">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">入账余额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="accountBalance" class="am-input-sm" value="${merchant.accountBalance}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </shiro:hasPermission>
                </div>

                <div class="am-g am-margin-top">
                    <shiro:hasPermission name="admin:create">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">出账冻结金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="balance" class="am-input-sm" value="${merchant.accountFrozen}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </shiro:hasPermission>
                </div>
                <div class="am-g am-margin-top">
                    <shiro:hasPermission name="admin:create">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">提现余额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="WithdrawBalance" class="am-input-sm" value="${merchant.withdrawBalance}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </shiro:hasPermission>
                </div>
                <div class="am-g am-margin-top">
                    <shiro:hasPermission name="admin:create">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">提现冻结金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="WithdrawFrozen" class="am-input-sm" value="${merchant.withdrawFrozen}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </shiro:hasPermission>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">法人：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="contacts" class="am-input-sm" value="${merchant.contacts}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">法人身份证：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="idCard" class="am-input-sm" value="${merchant.idCard}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">联系电话：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="tel" class="am-input-sm" value="${merchant.tel}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">商户密钥：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="secretKey" class="am-input-sm" value="${merchant.secretKey}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">地址信息：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <textarea cols="20" rows="3"
                                  name="request">${merchant.province.province} ${merchant.city.city} ${merchant.area.area} ${merchant.address}</textarea>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
            </form>
        </div>
        <hr>
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商户信息</strong> /
                <small>产品费率</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="#">
                    <table id="contentTable" class="am-table am-table-striped am-table-hover table-main">
                        <thead>
                        <tr>
                            <th>产品名</th>
                            <th>结算费率</th>
                            <th>代付成本</th>
                            <th>开通时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${merchantRateList}" var="merchantRate" varStatus="status">
                            <tr>
                                <td>${merchantRate.router.name}</td>
                                <td>${merchantRate.costRate}</td>
                                <td>${merchantRate.payCost}</td>
                                <td><fmt:formatDate value="${merchantRate.createTime}"
                                                    pattern='yyyy-MM-dd HH:mm:ss'/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
        <hr>
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商户信息</strong> /
                <small>结算信息</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <form class="am-form" action="#">
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">结算类型：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <select name="settleType" data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}"
                                required>
                            <option></option>
                            <c:forEach items="${settleTypes }" var="item">
                                <option value="${item }"
                                        <c:if test="${item == merchant.settlement.settleType}">selected</c:if>>${item.text}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">账户类型：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <select name="accountType" data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}"
                                required>
                            <option></option>
                            <c:forEach items="${accountTypes }" var="item">
                                <option value="${item }"
                                        <c:if test="${item == merchant.settlement.accountType}">selected</c:if>>${item.text}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <script type="text/javascript">
                    $('select[name=settleType]').selected('disable');
                    $('select[name=accountType]').selected('disable');
                </script>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">银行账户：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="company" class="am-input-sm"
                               value="${empty merchant.settlement?"":merchant.settlement.bankAccount}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">开户银行：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="contacts" class="am-input-sm"
                               value="${empty merchant.settlement?"":merchant.settlement.bankName}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">银行名称：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="contacts" class="am-input-sm"
                               value="${empty merchant.settlement?"":merchant.settlement.bankNickname}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>
            </form>
        </div>
        <%@ include file="../include/footer.jsp" %>
    </div>
</body>
<script type="text/javascript">

</script>
</html>