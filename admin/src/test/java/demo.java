import co.b4pay.admin.common.smsUtil.SmsUtil;
import co.b4pay.admin.entity.base.AjaxResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class demo {


    @Autowired
    private SmsUtil smsUtil;

    @Test
    public void testsms() {
        //生成一个随机六位验证码
        String code = "";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        code = sb.toString();
        System.out.println("短信验证码:" + code);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "15119628057");
        map.put("signName", "Tosdom的私人小窝");
        map.put("templateCode", "SMS_150183039");
        map.put("templateParam", "{\"code\":\"" + code + "\"}");
        SendSmsResponse response = null;
        try {
            response = smsUtil.sendSms(
                    map.get("mobile"),
                    map.get("signName"),
                    map.get("templateCode"),
                    map.get("templateParam"));
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());
    }
}
