<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>组织机构</title>
    <%@ include file="../../include/head.jsp" %>
    <link rel="stylesheet"
          href="${ctxAssets}/3rd-lib/jquery-layout/1.3.0/css/layout-default-latest.css">
</head>
<body>
<iframe name="content" class="ui-layout-center" src="" frameborder="0" scrolling="auto"></iframe>
<iframe name="tree" class="ui-layout-west" src="${ctx}/organization/tree" frameborder="0" scrolling="auto"></iframe>

<script src="${ctxAssets}/3rd-lib/jquery-layout/1.3.0/js/jquery.layout-latest.min.js"></script>
<script>
    $(function () {
        $(document).ready(function () {
            $('body').layout({applyDemoStyles: true});
            $('iframe').css("border", "none");
        });
    });
</script>
</body>
</html>