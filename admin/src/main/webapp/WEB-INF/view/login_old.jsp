<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>运营平台管理系统</title>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate, no-siteapp"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <%@ include file="include/head.jsp" %>
    <style>
        .header {
            text-align: center;
        }

        .header h1 {
            font-size: 200%;
            color: #333;
            margin-top: 30px;
        }

        .header p {
            font-size: 14px;
        }
    </style>
    <script type="text/javascript">
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        <%--if (self.frameElement && self.frameElement.tagName == "IFRAME") {--%>
        <%--top.location.replace("${ctx}");--%>
        <%--}--%>
    </script>
</head>
<body>
<div class="header">
    <div class="am-g">
        <h1>运营平台管理系统 - 登陆</h1>
    </div>
    <hr/>
</div>
<div class="am-g">
    <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
        <form class="am-form am-form-horizontal" action="login" method="post" target="_parent">
            <div class="am-form-group">
                <label for="username" class="am-u-sm-2 am-form-label">用户名</label>
                <div class="am-u-sm-10">
                    <input type="text" id="username" name="username" value="${username }" placeholder="输入你的用户名"
                           required>
                </div>
            </div>

            <div class="am-form-group">
                <label for="password" class="am-u-sm-2 am-form-label">密码</label>
                <div class="am-u-sm-10">
                    <input type="password" id="password" name="password" placeholder="输入你的密码吧" required>
                </div>
            </div>

            <!-- <div class="am-form-group">
                <div class="am-u-sm-offset-2 am-u-sm-10">
                    <div class="checkbox">
                        <label> <input type="checkbox" name="rememberMe"> 记住我
                        </label>
                    </div>
                </div>
            </div> -->

            <div class="am-form-group">
                <div class="am-u-sm-10 am-u-sm-offset-2">
                    <button type="submit" class="am-btn am-btn-default">提交登入</button>
                    <span class="error">${message}</span>
                </div>
            </div>
        </form>
        <%@ include file="include/footer.jsp" %>
    </div>
</div>
</body>
</html>