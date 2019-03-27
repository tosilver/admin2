package co.b4pay.admin.controller.payroll;


import java.io.IOException;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import co.b4pay.admin.service.ChannelService;


import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.common.util.StringUtil;


import co.b4pay.admin.entity.base.AjaxResponse;

import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.RouterService;
import co.b4pay.admin.service.YsbPayService;


@RestController
@RequestMapping("payroll/ysbPay.do")
public class YsbPayController {
    private static final Logger logger = LoggerFactory.getLogger(YsbPayController.class);

    private static final String[] REQUIRED_PARAMS = new String[]{"merchantId", "name", "cardNo", "orderId", "purpose", "amount", "idCardNo", "summary", "notifyUrl"};
    private static final String YSB_ROUTER = "ysbPayroll";
    @Autowired
    YsbPayService ysbPayService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    RouterService routerService;
    @Autowired
    ChannelService channelService;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse doPost(HttpServletRequest request) throws IOException {


        if (getMac(request).equals(request.getParameter("mac"))) {
            String merchantId = request.getParameter("merchantId");
            String amount = request.getParameter("amount");
            if (merchantService.get(merchantId).getStatus() == 1) {
                if (merchantbalance(merchantId, amount)) {

                    BigDecimal orderAmount = new BigDecimal(amount);
                    BigDecimal quota = new BigDecimal(50000);
                    int compare = quota.compareTo(orderAmount);
                    BigDecimal amountLimit = channelService.findByPayCost(YSB_ROUTER).get(0).getAmountLimit();
                    int compareTo = amountLimit.compareTo(orderAmount);
                    if (compareTo > 0) {
                        if (compare > 0) {
                            return ysbPayService.execute(getMerchantId(request), getParams(request));
                        } else {
                            return AjaxResponse.failure(1003, "代付金额超出限额");
                        }
                    } else {
                        return AjaxResponse.failure(1002, "渠道维护中");
                    }
                } else {
                    return AjaxResponse.failure(1005, "商户账户余额不足");
                }
            } else {
                return AjaxResponse.failure(1004, "账户维护中");
            }
        } else {

            return AjaxResponse.failure(1001, "签名校验错误");
        }

    }


    //判断账户余额是否可用
    public boolean merchantbalance(String merchantId, String amount) {
        BigDecimal balance = merchantService.get(merchantId).getBalance();
        BigDecimal orderAmount = new BigDecimal(amount);
        int compareTo = balance.compareTo(orderAmount);

        if (compareTo < 0) {
            return false;
        } else {
            return true;
        }
    }

    public Long getMerchantId(HttpServletRequest request) {
        String merchantId = request.getParameter("merchantId");
        return isBlank(merchantId) ? null : Long.parseLong(merchantId);
    }

    public boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }


    public JSONObject getParams(HttpServletRequest request) throws BizException {
        Map<String, String> requiredParams = new HashMap<>();
        for (String paramName : getRequiredParams()) { // 参数
            String paramValue = request.getParameter(paramName);
            if (StringUtil.isBlank(paramValue)) {
                throw new BizException(String.format("缺少参数[%s]", paramName));
            }
            requiredParams.put(paramName, paramValue);
        }
        JSONObject params = new JSONObject(); // 业务参数
        if (MapUtils.isNotEmpty(requiredParams)) {
            params.putAll(requiredParams);
        }
        return params;
    }

    public String[] getRequiredParams() {
        return REQUIRED_PARAMS;
    }

    /*
     * 计算Mac
     */
    public String getMac(HttpServletRequest request) throws IOException {
        StringBuffer sf = new StringBuffer();
        sf.append("merchantId=").append(request.getParameter("merchantId"));
        sf.append("&name=").append(request.getParameter("name"));
        sf.append("&cardNo=").append(request.getParameter("cardNo"));
        sf.append("&orderId=").append(request.getParameter("orderId"));
        sf.append("&purpose=").append(request.getParameter("purpose"));
        sf.append("&amount=").append(request.getParameter("amount"));
        sf.append("&idCardNo=").append(request.getParameter("idCardNo"));
        sf.append("&summary=").append(request.getParameter("summary"));
        sf.append("&notifyUrl=").append(request.getParameter("notifyUrl"));
        sf.append("&key=").append(merchantService.get(request.getParameter("merchantId")).getSecretKey());
        return Md5Encrypt.md5(sf.toString()).toUpperCase();
    }

}