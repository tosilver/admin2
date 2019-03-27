package co.b4pay.admin.service;


import co.b4pay.admin.common.YfbUtil.HttpClientQueryUtil;
import co.b4pay.admin.controller.Utils.StringUtil;
import co.b4pay.admin.dao.ChannelDao;
import co.b4pay.admin.dao.DetailDataCardDao;
import co.b4pay.admin.dao.XiafaDao;
import co.b4pay.admin.dao.YfbPayrollDao;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.DetailDataCard;
import co.b4pay.admin.entity.base.AjaxResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Service
public class YfbQueryService extends YfbPayService {
    private static final Logger logger = LoggerFactory.getLogger(YfbQueryService.class);
    //批量转账下单付款到易付宝查询订单请求地址
    //private static final String YFB_URL = "http://pay.unspay.com:8081/delegate-pay-front/delegatePay/fourElementsPay";
    //产品名
    private static final String ROUTER_KEY = "yfbPayroll";
    //签名方式
    private static final String signAlgorithm = "RSA";
    //签名指定字符集
    private static final String inputCharset = "UTF-8";
    //产品编号
    // private static final String productCode = "01060000029";

    @Autowired
    ChannelDao channelDao;

    @Autowired
    YfbPayrollDao yfbPayrollDao;

    @Autowired
    DetailDataCardDao detailDataCardDao;

    /**
     * 请求转账网关
     *
     * @param
     * @return signature 签名
     * @throws UnsupportedEncodingException
     * @throws java.security.InvalidKeyException
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public String batchWithDraw(HashMap<String, String> params, String queryUrl) {
        JSONObject rspUserJson = new JSONObject();

        Channel channel = channelDao.findByPayCost(ROUTER_KEY).get(0);
        String batchNo = params.get("batchNo");

        DetailDataCard dataCard = detailDataCardDao.findByBatchNo(batchNo);

        if (dataCard != null && dataCard.getStatus() == 2) {
            logger.info("模拟的响应进来了--根据:" + dataCard);
            rspUserJson.put("responseCode", "1");
            rspUserJson.put("responseMsg", "支付成功");
            rspUserJson.put("batchNo", dataCard.getBatchNo());
            rspUserJson.put("total_fee", dataCard.getAmount());
            logger.warn("111返回给代付用户的json:" + rspUserJson.toJSONString());
            return rspUserJson.toJSONString();
        }
        String merchantNo = channel.getProdPid();
        String publicKeyIndex = channel.getProdAppid();
        String signature = calculateSign(channel, batchNo, "query");
        String rspStr = null;
        try {
            rspStr = HttpClientQueryUtil.post(publicKeyIndex, signAlgorithm,
                    merchantNo, inputCharset, queryUrl, signature, batchNo, merchantNo);
        } catch (Exception e) {
            rspUserJson = new JSONObject();
            rspUserJson.put("responseCode", "-1");
            rspUserJson.put("responseMsg", "请求三方查单时出了异常");
            logger.warn("222返回给代付用户的json:" + rspUserJson.toJSONString());
            return rspUserJson.toJSONString();
        }
        JSONObject rspJson = JSONObject.parseObject(rspStr);
        if (rspJson.containsKey("content")) {

            JSONObject content = JSONObject.parseObject(rspJson.getString("content"));
            JSONArray transferOrders = JSONArray.parseArray(content.getString("transferOrders"));
            JSONObject transferOrderJson = JSONObject.parseObject(transferOrders.get(0).toString());
            String errMessage = transferOrderJson.getString("errMessage");

            System.out.println(errMessage);
            rspUserJson.put("responseCode", "0");
            rspUserJson.put("responseMsg", errMessage);
            if (dataCard != null) {
                rspUserJson.put("batchNo", dataCard.getBatchNo());
                rspUserJson.put("total_fee", dataCard.getAmount());
            }
            logger.warn("333返回给代付用户的json:" + rspUserJson.toJSONString());
            return rspUserJson.toJSONString();
        } else {
            rspUserJson.put("responseCode", "0");

            rspUserJson.put("responseMsg", "支付失败原因不明");
            if (dataCard != null) {
                rspUserJson.put("batchNo", dataCard.getBatchNo());
                rspUserJson.put("total_fee", dataCard.getAmount());
            }
            logger.warn("444返回给代付用户的json:" + rspUserJson);
            return rspUserJson.toJSONString();
        }
    }
}



