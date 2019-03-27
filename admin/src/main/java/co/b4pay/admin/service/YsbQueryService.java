package co.b4pay.admin.service;


import co.b4pay.admin.common.YfbUtil.PayrollConstants;
import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.common.util.HttpUtils;
import co.b4pay.admin.dao.*;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.Payroll;

import co.b4pay.admin.entity.base.AjaxResponse;
import com.alibaba.fastjson.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;

@Service
public class YsbQueryService {
    private static final Logger logger = LoggerFactory.getLogger(YsbQueryService.class);
    // private static final String YSB_URL = "http://pay.unspay.com:8081/delegate-pay-front/delegatePay/queryOrderStatus";
    private static final String ROUTER_KEY = "ysbPayroll";
    @Autowired
    MerchantDao merchantDao;
    @Autowired
    PayrollDao payrollDao;
    @Autowired
    ChannelDao channelDao;
    @Autowired
    RouterDao routerDao;

    public AjaxResponse queryOrder(HashMap<String, String> params) throws IOException {
        Payroll payroll = payrollDao.findByDownOrderId(params.get("orderId"), params.get("merchantId"));
        Integer status = payroll.getStatus();
        if (status.equals(-2)) {
            HashMap<String, String> fhs = new HashMap<>();
            fhs.put("status", "20");
            fhs.put("desc", "调用失败");
            ObjectMapper jsonObject = new ObjectMapper();
            String jsonOrder = jsonObject.writeValueAsString(fhs);
            return AjaxResponse.success(1000, jsonOrder);
        }
        List<Channel> byPayCost = channelDao.findByPayCost(ROUTER_KEY);
        Channel channel = byPayCost.get(0);
        HashMap<String, String> map = new HashMap<>();
        map.put("accountId", channel.getProdPid());
        map.put("orderId", payroll.getUpOrderId());
        String mac = getMac(map, channel);
        map.put("mac", mac);
        String result = HttpUtils.doPost(PayrollConstants.YSB_URL_QRDER, map);
        JSONObject json = (JSONObject) JSONObject.parse(result);
        logger.info(json.toJSONString());
        if (json.getString("result_code").equals("0000")) {
            HashMap<String, String> fhs = new HashMap<>();
            fhs.put("status", json.getString("status"));
            fhs.put("desc", json.getString("desc"));
            ObjectMapper jsonObject = new ObjectMapper();
            String jsonOrder = jsonObject.writeValueAsString(fhs);
            return AjaxResponse.success(1000, jsonOrder);
        }
        return AjaxResponse.failure(Integer.parseInt(json.getString("result_code")), json.getString("result_msg"));
    }


    public AjaxResponse queryBalance(String merchantId) throws JsonProcessingException {
        Merchant merchant = merchantDao.get(merchantId);
        HashMap<String, String> param = new HashMap<>();
        try {
            param.put("balance", merchant.getBalance().toPlainString());

        } catch (Exception E) {
            param.put("balance", "0.00");
        }
        param.put("merchantId", merchantId);
        ObjectMapper json = new ObjectMapper();
        String jsonParams = json.writeValueAsString(param);
        return AjaxResponse.success(1000, jsonParams);
    }

    private String getMac(HashMap<String, String> params, Channel channel) throws IOException {
        StringBuffer sf = new StringBuffer();
        sf.append("accountId=").append(params.get("accountId"));
        sf.append("&orderId=").append(params.get("orderId"));
        sf.append("&key=").append(channel.getProdPublicKey());
        return Md5Encrypt.md5(sf.toString()).toUpperCase();

    }
}
