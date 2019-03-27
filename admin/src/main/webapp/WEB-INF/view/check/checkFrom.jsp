<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="apple-touch-icon" sizes="76x76" href=""/>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>
    <meta name="viewport" content="width=device-width"/>

    <!-- CSS Files -->
    <link href="${ctxAssets}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctxAssets}/css/paper-bootstrap-wizard.css" rel="stylesheet"/>

    <!-- CSS Just for demo purpose, don't include it in your project -->
    <link href="${ctxAssets}/css/demo.css" rel="stylesheet"/>

    <!-- Fonts and Icons -->
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Muli:400,300' rel='stylesheet' type='text/css'>
    <link href="${ctxAssets}/css/themify-icons.css" rel="stylesheet">
</head>

<body>

<div class="image-container set-full-height">

    <div class="container">
        <div class="row">
            <div class="col-sm-8 col-sm-offset-2">


                <div class="wizard-container">

                    <div class="card wizard-card" data-color="orange" id="wizardProfile">
                        <form action="${ctx}/mallcheck/save" method="get">
                            <!--        You can switch " data-color="orange" "  with one of the next bright colors: "blue", "green", "orange", "red", "azure"          -->

                            <div class="wizard-header text-center">
                                <h3 class="wizard-title">通道检查</h3>
                                <p class="category"></p>
                            </div>

                            <div class="wizard-navigation">
                                <div class="progress-with-circle">
                                    <div class="progress-bar" role="progressbar" aria-valuenow="1" aria-valuemin="1" aria-valuemax="3" style="width: 21%;"></div>
                                </div>
                                <ul>
                                    <li>
                                        <a href="#about" data-toggle="tab">
                                            <div class="icon-circle">
                                                <i class="ti-user"></i>
                                            </div>
                                            请求参数
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#address" data-toggle="tab">
                                            <div class="icon-circle">
                                                <i class="ti-map"></i>
                                            </div>
                                            请求端口
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#account" data-toggle="tab">
                                            <div class="icon-circle">
                                                <i class="ti-settings"></i>
                                            </div>
                                            请求地址
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="tab-content">
                                <div class="tab-pane" id="about">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>订单号</label>
                                                <input name="tradeNo" type="text" class="form-control" value="${tradeNo}" >

                                            </div>
                                            <div class="form-group">
                                                <label>金额<small>(必填)</small></label>
                                                <input name="totalAmout" type="text" class="form-control" placeholder="100">
                                            </div>
                                            <div class="form-group">
                                                <label>时间
                                                    <small>(yyyy-mm-dd)</small>
                                                </label>
                                                <input name="time" type="text" class="form-control" value="${time}">
                                            </div>
                                            <div class="form-group">
                                                <label>支付方式</label><br>
                                                <select name="payType" class="form-control">
                                                    <option value="1"> 支付宝</option>
                                                    <option value="2"> 微信</option>
                                                    <option value="3"> 快捷</option>
                                                    <option value="...">...</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="address">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <h5 class="info-text">选择测试端口(单选)</h5>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="choice" data-toggle="wizard-checkbox">
                                                <input type="checkbox" name="port" value="1">
                                                <div class="card card-checkboxes card-hover-effect">
                                                    <i class="ti-star"></i>
                                                    <p>API(旧)</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="choice" data-toggle="wizard-checkbox">
                                                <input type="checkbox" name="port" value="2">
                                                <div class="card card-checkboxes card-hover-effect">
                                                    <i class="ti-star"></i>
                                                    <p>API(新)</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="choice" data-toggle="wizard-checkbox">
                                                <input type="checkbox" name="port" value="3">
                                                <div class="card card-checkboxes card-hover-effect">
                                                    <i class="ti-star"></i>
                                                    <p>商城</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane" id="account">
                                    <h5 class="info-text"> 选择商城地址(单选): </h5>
                                    <div class="row">
                                        <div class="col-sm-8 col-sm-offset-2">
                                            <c:forEach items="${page.list}" var="malladdress" varStatus="status">
                                                <div class="col-sm-3">
                                                    <div class="choice" data-toggle="wizard-checkbox">
                                                        <input type="checkbox" name="mallAddress" value="${malladdress.address}">
                                                        <div class="card card-checkboxes card-hover-effect">
                                                            <i class="ti-paint-roller"></i>
                                                            <p>${malladdress.mallName}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="wizard-footer">
                                <div class="pull-right">
                                    <input type='button'  class='btn btn-next btn-fill btn-warning btn-wd' name='next' value='下一步'/>
                                    <input type='submit'  class='btn btn-finish btn-fill btn-warning btn-wd' name='finish' value='提交'/>
                                </div>

                                <div class="pull-left">
                                    <input type='button' class='btn btn-previous btn-default btn-wd' name='previous' value='上一步'/>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </form>
                    </div>
                </div> <!-- wizard container -->
            </div>
        </div><!-- end row -->
    </div> <!--  big container -->
</div>

</body>

<!--   Core JS Files   -->
<script src="${ctxAssets}/js/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="${ctxAssets}/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxAssets}/js/jquery.bootstrap.wizard.js" type="text/javascript"></script>

<!--  Plugin for the Wizard -->
<script src="${ctxAssets}/js/paper-bootstrap-wizard.js" type="text/javascript"></script>

<!--  More information about jquery.validate here: http://jqueryvalidation.org/	 -->
<script src="${ctxAssets}/js/jquery.validate.min.js" type="text/javascript"></script>


</html>

