<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>用户编辑</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">用户管理</strong> /
                <small>修改密码</small>
            </div>
        </div>
        <hr>
        <div class="am-g admin-form">
            <form class="am-form am-form-horizontal" action="/admin/${user.id}/changePassword" method="post"
                  onsubmit="return verifyPassword()">
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">新密码：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="password" name="newPassword" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6">*必填</div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">确认新密码：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="password" name="newPassword2" onblur="verifyPassword()" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6">*必填</div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">&nbsp;</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <button type="submit" class="am-btn am-btn-primary"> 修改密码</button>
                        <button type="button" class="am-btn" onclick="history.go(-1)">返 回</button>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6">*必填</div>
                </div>
            </form>
        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>
</div>
</body>
<script type="text/javascript">
    function verifyPassword() {
        var newPassword = $('[name=newPassword]').val();
        var newPassword2 = $('[name=newPassword2]').val();
        if (newPassword == newPassword2) {
            return true;
        } else {
            alert('两次输入的密码不一致');
            return false;
        }
    }
</script>
</html>