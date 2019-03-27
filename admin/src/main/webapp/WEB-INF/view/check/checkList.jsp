<%--
  Created by IntelliJ IDEA.
  User: zenggp
  Date: 2019/3/25
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%--<meta http-equiv="refresh" content="300">--%>
    <title>Title</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${ctxAssets}/materialize/css/materialize.min.css" media="screen,projection"/>
    <!-- Bootstrap Styles-->
    <link href="${ctxAssets}/css/bootstrap.css" rel="stylesheet"/>
    <!-- FontAwesome Styles-->
    <link href="${ctxAssets}/css/font-awesome.css" rel="stylesheet"/>
    <!-- Morris Chart Styles-->
    <link href="${ctxAssets}/js/morris/morris-0.4.3.min.css" rel="stylesheet"/>
    <!-- Custom Styles-->
    <link href="${ctxAssets}/css/custom-styles.css" rel="stylesheet"/>
    <!-- Google Fonts-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
    <link rel="stylesheet" href="${ctxAssets}/js/Lightweight-Chart/cssCharts.css">
</head>
<body>
<div id="page-inner">

    <div class="dashboard-cards">
        <div class="row">
            <c:forEach items="${top4}" var="address" varStatus="status">
                <div class="col-xs-12 col-sm-6 col-md-3">
                    <div class="card horizontal cardIcon waves-effect waves-dark">
                        <div class="card-image blue">
                            <div class="card-content">
                                <h3>${status.index + 1}</h3>
                            </div>
                            <div class="card-action">
                                <h4>${address.mallName}</h4>
                            </div>
                        </div>
                        <div class="card-stacked red">
                            <div class="card-content">
                                <h3>营业额</h3>
                            </div>
                            <div class="card-action">
                                <h3>${address.turnover}</h3>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-7">
                <div class="cirStats">
                    <div class="row">
                        <form id="searchForm" action="${ctx}/mallcheck/list" method="get" class="am-form-inline">
                            <div class="col-xs-12 col-sm-6 col-md-6">
                                <select name="addressId" aria-controls="dataTables-example" class="form-control input-sm"
                                        onchange="$('#searchForm').submit()">
                                    <option value=" ">所有通道</option>
                                    <c:forEach items="#{addressList}" var="address">
                                        <option value="${address.id}"
                                                <c:if test="${address.id eq param.addressId}">selected</c:if>>${address.mallName}</option>
                                    </c:forEach>
                                </select>
                                <div class="card-panel text-center">
                                    <div class="easypiechart" id="easypiechart-blue" data-percent="${successRate}"><span class="percent">${successRate}%</span>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div><!--/.row-->
        </div>


    </div>


    <div class="fixed-action-btn horizontal click-to-toggle">
        <a class="btn-floating btn-large red">
            <i class="material-icons">menu</i>
        </a>
        <ul>
            <li><a class="btn-floating red"><i class="material-icons">track_changes</i></a></li>
            <li><a class="btn-floating yellow darken-1"><i class="material-icons">format_quote</i></a></li>
            <li><a class="btn-floating green"><i class="material-icons">publish</i></a></li>
            <li><a class="btn-floating blue"><i class="material-icons">attach_file</i></a></li>
        </ul>
    </div>


    <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
    <script src="${ctxAssets}/js/jquery-1.10.2.js"></script>

    <!-- Bootstrap Js -->
    <script src="${ctxAssets}/js/bootstrap.min.js"></script>

    <script src="${ctxAssets}/materialize/js/materialize.min.js"></script>

    <!-- Metis Menu Js -->
    <script src="${ctxAssets}/js/jquery.metisMenu.js"></script>
    <!-- Morris Chart Js -->
    <script src="${ctxAssets}/js/morris/raphael-2.1.0.min.js"></script>
    <script src="${ctxAssets}/js/morris/morris.js"></script>


    <script src="${ctxAssets}/js/easypiechart.js"></script>
    <script src="${ctxAssets}/js/easypiechart-data.js"></script>

    <script src="${ctxAssets}/js/Lightweight-Chart/jquery.chart.js"></script>

    <!-- Custom Js -->
    <script src="${ctxAssets}/js/custom-scripts.js"></script>

</body>
</html>
