<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>用户编辑</title>
    <%@ include file="../include/head.jsp" %>
    <%--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>--%>

    <link rel="stylesheet" href="${ctxAssets}/3rd-lib/jquery-ztree/3.5/css/zTreeStyle.css">
    <style>
        .content-tcc-btn {
            width: 65px;
            height: 30px;
            line-height: 30px;
            border: 1px solid #CCCCCC;
            text-align: center;
        }

        .content-tcc {
            width: 550px;
            height: 500px;
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            margin: auto;
            z-index: 10;
            display: none;
        }

        .content-tcc .popup {
            padding: 15px;
            background-color: white;
            border: 1px solid #CCCCCC;
            width: 500px;
            height: 400px;
        }

        .content-tcc .con {
            height: 250px;
            width: 230px;
            overflow: scroll;
            border: 1px solid #CCCCCC;
            margin: 10px 0;
        }

        .content-tcc ul {
            margin: 0;
            padding: 10px;
        }

        .content-tcc li {
            list-style: none;
            cursor: pointer;
            height: 28px;
            line-height: 28px;
            padding: 0 5px;
        }

        .content-tcc li:hover {
            background-color: #f0f0f0;
        }

        .content-tcc .left-box {
            float: left;
        }

        .content-tcc .right-box {
            float: right;
        }

        .content-tcc .title {
            margin-bottom: 10px;
        }

        .content-tcc .main input {
            width: 240px;
            height: 30px;
            padding: 0 10px;
            border: 1px solid #CCCCCC;
        }

        .content-tcc .btn button {
            width: 55px;
            height: 30px;
            font-size: 16px;
        }
    </style>
    <style>
        ul.ztree {
            margin-top: 10px;
            border: 1px solid #ddd;
            background: #fff;
            width: 220px;
            height: 200px;
            overflow-y: auto;
            overflow-x: auto;
        }
    </style>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">用户管理</strong> /
                <small>用户${not empty user.id?'修改':'添加'}</small>
            </div>
        </div>
        <hr>
        <div class="am-g admin-form">
            <form class="am-form am-form-horizontal" modelAttribute="user"
                  action="${ctx}/admin/<c:choose><c:when test="${empty user.id}">create</c:when><c:otherwise>${user.id}/update</c:otherwise></c:choose>"
                  method="post">
                <input type="hidden" name="id" value="${user.id}"/>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">用户名：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <input type="text" name="username" value="${user.username}" required/>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6">*必填</div>
                </div>
                <c:if test="${empty user.id}">
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">密码：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <input type="password" name="password" value="${user.password}" required/>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6">*必填</div>
                    </div>
                </c:if>
                <c:if test="${'yes' eq ifSuperAdmin}">
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">所属组织：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <div class="am-input-group">
                                <input type="text" id="organizationName" class="am-form-field"
                                       value="${fnc:organizationName(user.organizationId)}" readonly/>
                                <input type="hidden" id="organizationId" name="organizationId"
                                       value="${user.organizationId}"/>
                                <span class="am-input-group-btn">
									<button class="am-btn am-btn-default" id="menuBtn" type="button">选择</button>
								  </span>
                            </div>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                    <div class="am-g am-margin-top">
                        <div class="am-u-sm-4 am-u-md-2 am-text-right">角色列表：</div>
                        <div class="am-u-sm-8 am-u-md-4">
                            <select name="roleIds"
                                    data="<c:forEach items="${user.roleIds}" var="item">${item},</c:forEach>" multiple>
                                <c:forEach items="${roleList}" var="m">
                                    <option value="${m.id}">${m.description}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-hide-sm-only am-u-md-6"></div>
                    </div>
                </c:if>

                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">渠道列表：</div>
                    <input type="button" class="content-tcc-btn" value="弹出">
                    <input type="hidden" name="channelIds" id="channelIds" value="${chIds}"/>
                    <%--<div class="am-u-sm-8 am-u-md-4">--%>
                    <%--<select name="channelIds" data="<c:forEach items="${user.channelIds}" var="item">${item},</c:forEach>" required multiple>--%>
                    <%--<c:forEach items="${channelList}" var="channel">--%>
                    <%--<option value="${channel.id}">${channel.name }&nbsp;&nbsp;${channel.dstDescribe }</option>--%>
                    <%--</c:forEach>--%>
                    <%--</select>--%>
                    <%--</div>--%>
                    <%--<div class="am-hide-sm-only am-u-md-6"></div>--%>
                    <div class="content-tcc">
                        <div class="popup">
                            <%--<div class="title">渠道列表</div>--%>
                            <div class="main">
                                <%--<input type="search" placeholder="搜索">--%>
                                <div class="main-con">
                                    <div class="con left-box">
                                        <ul id="channelIdsa">
                                            <c:forEach items="${channelList}" var="channel">
                                                <li id="${channel.id }">${channel.name }&nbsp;&nbsp;${channel.dstDescribe }</li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <div class="con right-box">
                                        <ul id="channelIdsb">
                                            <c:forEach items="${channelLists}" var="channel">
                                                <li id="${channel.id }">${channel.name }&nbsp;&nbsp;${channel.dstDescribe }</li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="btn">
                                    <%--<button>确认</button>--%>
                                    <input id="qdqr" type="button" value="确认">
                                    <%--<input type="button" class="content-tcc-back" value="返回">--%>
                                    <%--<button class="content-tcc-back">返回</button>--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="am-g am-margin-top">
                    <div class="am-u-sm-4 am-u-md-2 am-text-right">客户列表：</div>
                    <div class="am-u-sm-8 am-u-md-4">
                        <select name="merchantIds"
                                data="<c:forEach items="${user.merchantIds}" var="item">${item},</c:forEach>" required
                                multiple>
                            <c:forEach items="${merchantList}" var="merchant">
                                <option value="${merchant.id}">${merchant.company }&nbsp;&nbsp;${merchant.contacts }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="am-hide-sm-only am-u-md-6"></div>
                </div>

                <div class="am-margin">
                    <button type="submit" class="am-btn am-btn-primary">保 存</button>
                    <button type="button" class="am-btn" onclick="history.go(-1)">返 回</button>
                </div>
            </form>


        </div>
    </div>
    <%@ include file="../include/footer.jsp" %>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="tree" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>

<script src="${ctxAssets}/3rd-lib/jquery-ztree/3.5/js/jquery.ztree.core-3.5.min.js"></script>
<script>
    $(function () {
        initSelectValue(true);//初始化下拉框的值
        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick
            }
        };
        var zNodes = [
            <c:forEach items="${organizationList}" var="o">
            <c:if test="${not o.rootNode}">
            {id:${o.id}, pId:${o.parentId}, name: "${o.name}"},
            </c:if>
            </c:forEach>
        ];

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree"),
                nodes = zTree.getSelectedNodes(),
                id = "",
                name = "";
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, l = nodes.length; i < l; i++) {
                id += nodes[i].id + ",";
                name += nodes[i].name + ",";
            }
            if (id.length > 0) id = id.substring(0, id.length - 1);
            if (name.length > 0) name = name.substring(0, name.length - 1);
            $("#organizationId").val(id);
            $("#organizationName").val(name);
            hideMenu();
        }

        function showMenu() {
            var cityObj = $("#organizationName");
            var cityOffset = $("#organizationName").offset();
            $("#menuContent").css({
                left: cityOffset.left + "px",
                top: cityOffset.top + cityObj.outerHeight() + "px"
            }).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

        $.fn.zTree.init($("#tree"), setting, zNodes);
        $("#menuBtn").click(showMenu);
    });
</script>
<script>
    $('.content-tcc-btn').click(function () {
        $('.content-tcc').show()
    })
    $('.content-tcc-back').click(function () {
        $('.content-tcc').hide();
    })

    $('#qdqr').click(function () {
        var aaa = document.getElementById("channelIdsa").getElementsByTagName('li');
        var bbb = document.getElementById("channelIdsb").getElementsByTagName('li');
        var c = "";
        for (var i = 0; i < bbb.length; i++) {
            c = c.trim() + bbb[i].id.trim() + ","
        }
        console.log(c)
        document.getElementById("channelIds").value = c
        $('.content-tcc').hide()
    })

    $(function () {
        $('.left-box').on('click', 'li', function () {
            $('.right-box ul').append($(this))
        })
        $('.right-box').on('click', 'li', function () {
            $('.left-box ul').append($(this))
        })
    })
</script>
</body>
</html>