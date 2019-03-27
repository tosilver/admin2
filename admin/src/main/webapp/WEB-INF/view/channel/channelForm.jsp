<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>合作商管理</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">渠道管理</strong> /
                <small>渠道${empty channel.id?'添加':'修改'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form name="channelForm" class="am-form" action="${ctx}/channel/save" method="post">
                    <input type="hidden" name="id" value="${channel.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">产品：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select name="router.id" id="routerId" data-am-selected="{btnWidth: '100%', maxHeight: 300}"
                                    required>
                                <option value="-1" selected="selected">请选择</option>
                                <c:forEach items="${routerList }" var="router">
                                    <option value="${router.id }"
                                            <c:if test="${router.id == channel.router.id}">selected</c:if>>${router.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">产品类型：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select name="product" id="product" data-am-selected="{btnWidth: '100%', maxHeight: 300}"
                                    required>
                                <option value="-1" selected="selected">请选择</option>
                                <option value="1" <c:if test="${channel.product eq 1}">selected</c:if>>微信</option>
                                <option value="2" <c:if test="${channel.product eq 2}">selected</c:if>>支付宝</option>
                                <option value="3" <c:if test="${channel.product eq 3}">selected</c:if>>银生宝</option>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">商品类别：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select name="goodsType.id" id="goodsTypeId"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300}" required>
                                <option value="-1" selected="selected">请选择</option>
                                <c:forEach items="${goodsTypeList }" var="goodsType">
                                    <option value="${goodsType.id }"
                                            <c:if test="${goodsType.id == channel.goodsType.id}">selected</c:if>>${goodsType.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">渠道名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="name" class="am-input-sm" value="${channel.name}"
                                   required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">渠道IP：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="ip4" class="am-input-sm" value="${channel.ip4}"
                                   placeholder="请输入渠道IP" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <%--<div class="am-g am-margin-top">--%>
                    <%--<div class="am-u-sm-4 am-u-md-2 am-text-right">测试环境商户PID：</div>--%>
                    <%--<div class="am-u-sm-8 am-u-md-4">--%>
                    <%--<input type="text" name="testPid" class="am-input-sm" value="${channel.testPid}" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'')"  required="required"/>--%>
                    <%--</div>--%>
                    <%--<div class="am-hide-sm-only am-u-md-6"></div>--%>
                    <%--</div>--%>
                    <%--<div class="am-g am-margin-top">--%>
                    <%--<div class="am-u-sm-4 am-u-md-2 am-text-right">测试环境私钥：</div>--%>
                    <%--<div class="am-u-sm-8 am-u-md-4">--%>
                    <%--<input type="text" name="testPrivateKey" class="am-input-sm" value="${channel.testPrivateKey}" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'')"  required="required"/>--%>
                    <%--</div>--%>
                    <%--<div class="am-hide-sm-only am-u-md-6"></div>--%>
                    <%--</div>--%>
                    <%--<div class="am-g am-margin-top">--%>
                    <%--<div class="am-u-sm-4 am-u-md-2 am-text-right">测试环境公钥：</div>--%>
                    <%--<div class="am-u-sm-8 am-u-md-4">--%>
                    <%--<input type="text" name="testPublicKey" class="am-input-sm" value="${channel.testPublicKey}" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'')"  required="required"/>--%>
                    <%--</div>--%>
                    <%--<div class="am-hide-sm-only am-u-md-6"></div>--%>
                    <%--</div>--%>
                    <%--<div class="am-g am-margin-top">--%>
                    <%--<div class="am-u-sm-4 am-u-md-2 am-text-right">测试环境APPID：</div>--%>
                    <%--<div class="am-u-sm-8 am-u-md-4">--%>
                    <%--<input type="text" name="testAppid" class="am-input-sm" value="${channel.testAppid}" onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'')"  required="required" />--%>
                    <%--</div>--%>
                    <%--<div class="am-hide-sm-only am-u-md-6"></div>--%>
                    <%--</div>--%>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">生产环境商户PID：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="prodPid" class="am-input-sm" value="${channel.prodPid}"
                                   onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'')" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">生产环境私钥：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="prodPrivateKey" class="am-input-sm"
                                   value="${channel.prodPrivateKey}"
                                   onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'')" required="required"/>
                        </div>

                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">生产环境公钥：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="prodPublicKey" class="am-input-sm" value="${channel.prodPublicKey}"
                                   onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'')" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">生产环境APPID：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="prodAppid" class="am-input-sm" value="${channel.prodAppid}"
                                   onkeyup="value=value.replace(/[\u4e00-\u9fa5]/g,'')" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">初始额度：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="amountInit" class="am-input-sm" value="${channel.amountInit}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额,不得超过1000W" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*单位：元</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">最低额度：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="amountMin" class="am-input-sm" value="${channel.amountMin}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额,不得超过1000W" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*单位：元</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">剩余额度：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="amountLimit" class="am-input-sm" value="${channel.amountLimit}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额,不得超过1000W" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*单位：元，24小时内重置</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">上游代付成本：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="payCost" class="am-input-sm" value="${channel.payCost}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">上游成本费率：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="costRate" class="am-input-sm" value="${channel.costRate}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入费率" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">单笔交易最高限额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="unitPrice" class="am-input-sm" value="${channel.unitPrice}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">单笔交易最低限额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="minPrice" class="am-input-sm"
                                   value="${channel.minPrice == null?0.00:channel.minPrice}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入金额" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">单笔交易速率（秒）：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="rate" class="am-input-sm"
                                   value="${channel.rate == null?0:channel.rate}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='';}"
                                   placeholder="请输入秒数" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">分账百分比：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="fzPercentage" class="am-input-sm"
                                   value="${channel.fzPercentage== null?0:channel.fzPercentage}"
                                   onkeyup="if( !/^[0-9]{1,}(?:.[0-9]{0,8})?$/.test(this.value)){alert('只能输入数字，小数点后最多保留八位');this.value='';}"
                                   placeholder="请输入分账百分比" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">渠道描述：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="dstDescribe" class="am-input-sm" value="${channel.dstDescribe}"
                                   placeholder="请输入渠道描述" required="required"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-u-sm-8 am-u-sm-centered">
                        <div class="am-g am-margin-top">
                            <div class="am-u-sm-4 am-text-right"></div>
                            <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="isChoose()">保存
                            </button>
                            <c:if test="${channel.status==1}">
                                <button type="button" class="am-btn am-btn-primary am-btn-xs"
                                        onclick="updateStatus(-1)">冻结
                                </button>
                            </c:if>
                            <c:if test="${channel.status==-1}">
                                <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="updateStatus(1)">
                                    启用
                                </button>
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
    function updateStatus(statusValue) {
        if (confirm('是否确认修改')) {
            window.location = '/channel/updateStatus?id=${channel.id}&status=' + statusValue;
        }
    }


    function isChoose() {
        var routerId = document.getElementById("routerId").value;
        var product = document.getElementById("product").value;
        if (routerId == -1) {
            window.alert("请选择产品");
        } else if (product == -1) {
            window.alert("请选择产品类型");
        } else {
            document.channelForm.submit();
        }
    }
</script>
</html>