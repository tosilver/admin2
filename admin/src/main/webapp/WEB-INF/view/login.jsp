<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>支付后台管理系统</title>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate, no-siteapp"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <%@ include file="include/head.jsp" %>
    <link rel="stylesheet" href="${ctxAssets}/custom/css/admin.css">
    <link rel="stylesheet" href="${ctxAssets}/custom/css/app.css">
    <style>
        hr {
            display: none;
        }

        .admin-content-footer {
            font-size: 100%;
        }
    </style>
</head>
<body data-type="login">

<div class="am-g myapp-login">
    <div class="myapp-login-logo-block  tpl-login-max">
        <div class="myapp-login-logo-text">
            <div class="myapp-login-logo-text">
                后台管理系统<span> 登陆</span> <i class="am-icon-skyatlas"></i>
            </div>
        </div>

        <div class="login-font">
            <i>支付接口 </i> <span> 数据管理</span>
        </div>
        <div class="am-u-sm-10 login-am-center">
            <form class="am-form" action="login" method="post" target="_parent">
                <fieldset>
                    <div class="am-form-group">
                        <input type="text" name="username" value="${username }" placeholder="请输入您的用户名" required>
                    </div>
                    <div class="am-form-group">
                        <input type="password" name="password" placeholder="请输入您的密码" required>
                    </div>
                    <p style="padding-top: 15px">
                        <button type="submit" class="am-btn am-btn-default">登录</button>
                    </p>
                </fieldset>
                <div class="form-group" style="text-align: center; color: red">${message}</div>
            </form>
            <%@ include file="include/footer.jsp" %>
        </div>
    </div>
</div>

</body>

</html>