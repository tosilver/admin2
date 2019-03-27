<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty msg}">
    <div class="am-alert am-alert-success" data-am-alert>
        <button type="button" class="am-close">&times;</button>
        <p>${msg}</p>
    </div>
    <script type="text/javascript">
        window.setTimeout(function () {
            $('.am-alert').alert('close');
        }, 3000);
    </script>
</c:if>
<c:if test="${not empty errMsg}">
    <div class="am-alert am-alert-warning" data-am-alert>
        <button type="button" class="am-close">&times;</button>
        <p>${errMsg}</p>
    </div>
    <script type="text/javascript">
        window.setTimeout(function () {
            $('.am-alert').alert('close');
        }, 3000);
    </script>
</c:if>

<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">提示信息</div>
        <div class="am-modal-bd" id="alert-message"></div>
        <div class="am-modal-footer">
            <span class="am-modal-btn">确定</span>
        </div>
    </div>
</div>

<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">确认信息</div>
        <div class="am-modal-bd" id="confirm-message"></div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>确定</span>
        </div>
    </div>
</div>

<script type="text/javascript">
    window.alert = function (message) {
        $('#my-alert #alert-message').text(message);
        $('#my-alert').modal();
    }

    window.alertConfirm = function (message, onConfirm, onCancel) {
        $('#my-confirm #confirm-message').text(message);
        $('#my-confirm').modal({
            relatedTarget: this,
            onConfirm: onConfirm || function (options) {
//                var $link = $(this.relatedTarget).prev('a');
//                var msg = $link.length ? '你要删除的链接 ID 为 ' + $link.data('id') :
//                        '确定了，但不知道要整哪样';
//                alert('确定了，但不知道要整哪样');
            },
            // closeOnConfirm: false,
            onCancel: onCancel || function () {
//                alert('算求，不弄了');
            }
        });
        return false;
    }

    function alertSuccess(message) {
        $('.am-alert').alert('close');
        var str = '<div class="am-alert am-alert-success" data-am-alert>';
        str += '<button type="button" class="am-close">&times;</button>';
        str += '<p>' + message + '</p>';
        str += '</div>';
        $('.admin-content-body').before(str);
        window.setTimeout(function () {
            $('.am-alert').alert('close');
        }, 2000);
    }

    function alertWarning(message) {
        $('.am-alert').alert('close');
        var str = '<div class="am-alert am-alert-warning" data-am-alert>';
        str += '<button type="button" class="am-close">&times;</button>';
        str += '<p>' + message + '</p>';
        str += '</div>';
        $('.admin-content-body').before(str);
        window.setTimeout(function () {
            $('.am-alert').alert('close');
        }, 2000);
    }
</script>