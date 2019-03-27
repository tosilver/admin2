package co.b4pay.admin.common.core.api.sms;

import java.io.UnsupportedEncodingException;

/**
 * 查询余额
 *
 * @author acer
 */
public class GetBalance {

    public static void main(String[] args) throws UnsupportedEncodingException {

        String userName = "";// 用户名
        String password = "";// 密码
        Client client = new Client(userName, password);

        // 查询余额
        String result_balance = client.getBalance();

        if (result_balance.startsWith("-")) {
            System.out.print("发送失败！返回值为：" + result_balance + "请查看HTTP返回值对照表");
            return;
        }

        System.out.print("您的余额为 : " + result_balance);
    }
}