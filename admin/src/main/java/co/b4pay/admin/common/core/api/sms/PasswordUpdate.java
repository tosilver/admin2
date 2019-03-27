package co.b4pay.admin.common.core.api.sms;

import java.io.UnsupportedEncodingException;

public class PasswordUpdate {

    /**
     * 修改密码
     *
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String userName = "";
        String password = "";
        String newPassword = "";

        Client client = new Client(userName, password);
        String result = client.UpdatePassword(newPassword);
        System.out.println("返回结果：" + result);
    }

}
