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
                <strong class="am-text-primary am-text-lg">商户管理</strong> /
                <small>商户${empty merchant.id?'添加':'修改'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="${ctx}/merchant/save" method="post">
                    <input type="hidden" name="id" value="${merchant.id}"/>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">公司名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="company" class="am-input-sm" value="${merchant.company}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">账户总余额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="balance" class="am-input-sm"
                                   value="${merchant.balance == null?0.00:merchant.balance}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">入账余额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="accountBalance" class="am-input-sm"
                                   value="${merchant.accountFrozen == null?0.00:merchant.accountBalance}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">出账冻结金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="accountFrozen" class="am-input-sm"
                                   value="${merchant.accountFrozen == null?0.00:merchant.accountFrozen}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">提现余额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="WithdrawBalance" class="am-input-sm"
                                   value="${merchant.accountFrozen == null?0.00:merchant.withdrawBalance}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">提现冻结金额：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="WithdrawFrozen" class="am-input-sm"
                                   value="${merchant.accountFrozen == null?0.00:merchant.withdrawFrozen}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">法人：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="contacts" class="am-input-sm" value="${merchant.contacts}"
                                   required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">法人身份证：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="idCard" class="am-input-sm" value="${merchant.idCard}"
                                   maxlength="18" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">联系电话：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="tel" class="am-input-sm" value="${merchant.tel}" maxlength="11"
                                   required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>


                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">省份：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select id="provinceId" name="province.provinceId"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}"
                                    data-val="${merchant.province.provinceId }">
                                <option></option>
                                <c:forEach items="${provinceList }" var="province">
                                    <option id="${province.provinceId }"
                                            value="${province.provinceId }">${province.province }</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">城市：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select id="cityId" name="city.cityId"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 300, searchBox: 1}"
                                    data-val="${merchant.city.cityId }">
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">区县：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select id="areaId" name="area.areaId"
                                    data-am-selected="{btnWidth: '100%', maxHeight: 150, searchBox: 1, dropUp: 0}"
                                    data-val="${merchant.area.areaId }">
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">地址：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="address" class="am-input-sm" value="${merchant.address}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*选填</div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">
                            <button type="submit" class="am-btn am-btn-primary am-btn-xs">保存</button>
                            <c:if test="${merchant.status==1}">
                                <button type="button" class="am-btn am-btn-primary am-btn-xs"
                                        onclick="updateStatus(-1)">冻结用户
                                </button>
                            </c:if>
                            <c:if test="${merchant.status==-1}">
                                <button type="button" class="am-btn am-btn-primary am-btn-xs" onclick="updateStatus(1)">
                                    启用用户
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
            window.location = '/merchant/updateStatus?id=${merchant.id}&status=' + statusValue;
        }
    }

    var provinceIdSelect = $('select#provinceId');
    var cityIdSelected = $('select#cityId');
    var areaIdSelected = $('select#areaId');

    window.setTimeout(function () {
        //下拉省份默认选中
        if (provinceIdSelect.data('val')) {
            provinceIdSelect.find('option#' + provinceIdSelect.data('val')).attr('selected', true);
        }
    }, 1000);

    provinceIdSelect.on('change', function () {
        if (!$(this).val()) {
            return;
        }
        $.get('/city/getByProvinceId', {
            'provinceId': $(this).val()
        }, function (data) {
            if (data) {
                cityIdSelected.empty();
                for (var i in data) {
                    cityIdSelected.append('<option id="' + data[i].cityId + '" value="' + data[i].cityId + '" >' + data[i].city + '</option>');
                }

                //下拉城市默认选中
                if (cityIdSelected.data('val')) {
                    cityIdSelected.find('option#' + cityIdSelected.data('val')).attr('selected', true);
                }
            }
        });
    });

    cityIdSelected.on('change', function () {
        if (!$(this).val()) {
            return;
        }
        $.get('/sysArea/getByCityId', {
            'cityId': $(this).val()
        }, function (data) {
            if (data) {
                areaIdSelected.empty();
                for (var i in data) {
                    areaIdSelected.append('<option id="' + data[i].areaId + '" value="' + data[i].areaId + '" >' + data[i].area + '</option>');
                }
            }
        });
    });

</script>
</html>