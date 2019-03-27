package co.b4pay.admin.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import co.b4pay.admin.controller.payroll.YsbPayController;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.common.util.HttpUtils;
import co.b4pay.admin.entity.Payroll;
import co.b4pay.admin.entity.base.ResponseMode;

@Service
public class YsbNotifyService {
    private static final Logger logger = LoggerFactory.getLogger(YsbNotifyService.class);
    @Autowired
    MerchantService merchantService;

    public String execute(Long merchantId, Payroll payroll, ResponseMode responseMode) throws UnsupportedEncodingException {
        StringBuffer sf = new StringBuffer();
        sf.append("merchantId=").append(merchantId);
        sf.append("&orderId=").append(payroll.getDownOrderId());
        sf.append("&amount=").append(payroll.getAmount());
        sf.append("&result_code=").append(responseMode.getResult_code());
        sf.append("&result_msg=").append(responseMode.getResult_msg());
        sf.append("&key=").append(merchantService.get(merchantId.toString()).getSecretKey());
        String mac = Md5Encrypt.md5(sf.toString()).toUpperCase();

        HashMap<String, String> yspParam = new HashMap<String, String>();
        yspParam.put("result_code", responseMode.getResult_code());
        yspParam.put("result_msg", responseMode.getResult_msg());
        yspParam.put("amount", payroll.getAmount().toPlainString());
        yspParam.put("orderId", payroll.getDownOrderId());
        yspParam.put("mac", mac);
        return "10000";
    }

}
//try {
//String result = HttpUtils.doPost(payroll.getNotifyUrl(),yspParam);
//			JSONObject json = (JSONObject) JSONObject.parse(result);
//			logger.info(json.toJSONString());
//			String result_code = json.getString("result_code");
//			return result_code;
//		} catch (IOException e) {
//
//			logger.info("下游回调通知失败");
//			String result_code = "100001";
//			return result_code;
//		}

//}

//}
