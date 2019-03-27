package co.b4pay.admin.common.core.api.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MsgSend {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String userName = "rrck";
        String password = "n0ik1vdm";
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String hms = sdf.format(new Timestamp(System.currentTimeMillis()));
        String content = "您的手机验证码是：123456";
        String mobile = "13652328652";
        Client client = new Client(userName, password);
        String result = client.mt(URLEncoder.encode(content, "UTF-8"), mobile, "", "", "", "");
        //System.out.println(result);
        String strCode = result.split("\n")[0];
        long code = 0;
        code = Long.valueOf(strCode);
        String info = null;
        if (code > 0) {// 成功提交
            info = "发送成功";
        } else if (code == 0) {
            info = "发送失败";
        } else if (code == -1) { // 用户名密码不正确
            info = "用户名密码不正确";
        } else if (code == -2) { // 必填选项为空
            info = "必填选项为空";
        } else if (code == -3) { // 短信内容0个字节
            info = "短信内容0个字节";
        } else if (code == -4) { // 0个有效号码
            info = "0个有效号码";
        } else if (code == -5) { // 余额不够
            info = "余额不够";
        } else if (code == -10) { // 用户被禁用
            info = "用户被禁用";
        } else if (code == -11) { // 短信内容过长
            info = "短信内容过长";
        } else if (code == -12) { // 用户无扩展权限
            info = "无扩展权限";
        } else if (code == -13) { // IP地址校验错
            info = "IP校验错误";
        } else if (code == -14) { // 内容解析异常
            info = "内容解析异常";
        } else {
            info = "未知错误";
        }
        System.out.println("返回信息：" + info + "--" + code + "--" + client.getPwd());
    }
}
