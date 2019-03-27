package co.b4pay.admin.common.core.api.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MsgSendData {

    /**
     * 个性短信(一个号码对应一条内容)
     *
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String userName = "";
        String password = "";
        Client client = new Client(userName, password);

        String charset = "UTF-8";
        String mobile = "13888888888,13588888888";
        String content1 = "您的验证码是：11111【企业签名】";
        String content2 = "XX先生/女士，今天是您的生日，祝您生日快乐！【企业签名】";

        String content = URLEncoder.encode(content1, charset) + "," + URLEncoder.encode(content2, charset);
        String ecodeContent = URLEncoder.encode(content, charset);
        String result = client.mtData(ecodeContent, mobile, "", "", "", charset);
        //System.out.println(result);
        String strCode = result.split("\n")[0];
        long code = 0;
        code = Long.valueOf(strCode);
        String Info = null;
        if (code > 0) {// 成功提交
            Info = "发送成功";
        } else if (code == 0) {
            Info = "发送失败";
        } else if (code == -1) { // 用户名密码不正确
            Info = "用户名密码不正确";
        } else if (code == -2) { // 必填选项为空
            Info = "必填选项为空";
        } else if (code == -3) { // 短信内容0个字节
            Info = "短信内容0个字节";
        } else if (code == -4) { // 0个有效号码
            Info = "0个有效号码";
        } else if (code == -5) { // 余额不够
            Info = "余额不够";
        } else if (code == -10) { // 用户被禁用
            Info = "用户被禁用";
        } else if (code == -11) { // 短信内容过长
            Info = "短信内容过长";
        } else if (code == -12) { // 用户无扩展权限
            Info = "无扩展权限";
        } else if (code == -13) { // IP地址校验错
            Info = "IP校验错误";
        } else if (code == -14) { // 内容解析异常
            Info = "内容解析异常";
        } else {
            Info = "未知错误";
        }
        System.out.println("返回信息：" + Info + "--" + code + "--" + client.getPwd());
    }

}
