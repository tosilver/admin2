<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>文件更新</title>
    <%@ include file="../include/head.jsp" %>
</head>
<body>
<%@ include file="../include/alert.jsp" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">文件更新</strong> /
                <small>更新</small>
            </div>
        </div>
        <hr>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form" action="${ctx}/apk/update" enctype="multipart/form-data" method="post">
                    <input type="hidden" name="id" value="${apkFile.id}"/>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">应用名：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="name" class="am-input-sm" value="${apkFile.name}" readonly/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">版本号：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="version" class="am-input-sm" value="${apkFile.version}"
                                   onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" placeholder="请输入版本号"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">状态：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="text" name="status" class="am-input-sm" value="${apkFile.status}"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>

                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">更新文件：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="file" name="aplFile" class="am-input-sm"/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>


                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-text-right"></div>
                        <div class="am-u-sm-8 am-u-sm-centered">

                            <button type="submit" class="am-btn am-btn-primary am-btn-xs">上传</button>

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