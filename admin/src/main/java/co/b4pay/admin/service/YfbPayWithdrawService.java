package co.b4pay.admin.service;

import co.b4pay.admin.common.YfbUtil.HttpClientUtil;
import co.b4pay.admin.common.YfbUtil.PayrollConstants;
import co.b4pay.admin.dao.*;
import co.b4pay.admin.entity.*;
import co.b4pay.admin.entity.base.AjaxResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.*;
import java.util.List;

/**
 * 易付宝请求批付到卡
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class YfbPayWithdrawService extends YfbPayService {
    private static final Logger logger = LoggerFactory.getLogger(YfbPayWithdrawService.class);
    //    //批量出款到卡请求地址
//    private static final String YFB_URL = "http://pay.unspay.com:8081/delegate-pay-front/delegatePay/fourElementsPay";
//    //响应地址
//    private static final String YYF_URL = "http://admin.b4pay.hk/payroll/yfbWithdrawNotify.do";
    //产品名
    //private static final String ROUTER_KEY = "yfbPayroll";
    //签名方式
    private static final String signAlgorithm = "RSA";
    //签名指定字符集
    private static final String inputCharset = "UTF-8";

    private static final String productCode = "01070000042";

    @Autowired
    ChannelDao channelDao;
    @Autowired
    YfbPayrollDao yfbPayrollDao;
    @Autowired
    DetailDataCardDao detailDataCardDao;
    @Autowired
    MerchantDao merchantDao;
    @Autowired
    MerchantRateDao merchantRateDao;

    /**
     * 请求转账网关
     *
     * @param
     * @return signature 签名
     * @throws UnsupportedEncodingException
     * @throws java.security.InvalidKeyException
     * @throws KeyManagementException
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public AjaxResponse executeWithdraw(Long merchantId, JSONObject params) throws Exception {
        logger.warn("请求批付的参数:" + params);
        List<Channel> channels = channelDao.findByDstDescribe(merchantId.toString());
        for (Channel channel2 : channels) {
            logger.warn("获取到的通道有:" + channel2.getName());
        }
        Channel channel = channels.get(0);
        logger.warn("选择的通道是:" + channel.getName());
        //判断渠道状态
        if (channel.getStatus().equals(-1)) {
            return AjaxResponse.failure(1002, "渠道暂停服务");
        }

        //判断商户余额
        Merchant merchant = merchantDao.get(merchantId.toString());
        BigDecimal balance = merchant.getBalance();
        BigDecimal totalAmount = changeF2Y(params.getString("totalAmount"));
        logger.info("商户余额:" + balance);
        logger.info("下发金额:" + totalAmount);

        int i1 = balance.compareTo(totalAmount);
        if (i1 < 0) {
            return AjaxResponse.failure(1005, "商户余额不足");
        }

        //获取公钥索引
        String publicKeyIndex = channel.getProdAppid();
        //获取对应上游商户ID
        String merchantNo = channel.getProdPid();
        //封装业务请求body
        String bussinessParam = bulidBatchContentJosn(channel, params, productCode, PayrollConstants.YYF_YFB_WR_URL).toJSONString();
        String signature = null;
        //生成签名
        signature = calculateSign(channel, bussinessParam, "withdrawPlace");

        try {
            //调用三方接口
            String rspStr = HttpClientUtil.post(publicKeyIndex, signAlgorithm, merchantNo, inputCharset, PayrollConstants.YFB_URL_WR,
                    signature, bussinessParam);

            //计算手续费
            /*MerchantRate byPayCost = merchantRateDao.findByPayCost(merchant.getId(), channel.getRouter().getId());
            BigDecimal payCost = byPayCost.getPayCost();*/
            BigDecimal payCost = channel.getPayCost();

            //扣除手续费和提现金额
            BigDecimal subtract = balance.subtract(totalAmount.add(payCost));
            merchant.setBalance(subtract);
            merchantDao.update(merchant);

            //同步响应消息转化成JSONObject对象
            JSONObject rspJson = JSONObject.parseObject(rspStr);
            String batchNo = params.getString("batchNo");
            String responseCode = null;
            try {
                //如果提交的数据并未全部受理成功,则failObject不为空
                String failStr = rspJson.getString(batchNo + "_" + merchantNo);
                JSONObject failJson = JSONObject.parseObject(failStr);
                responseCode = failJson.getString("responseCode");
                logger.warn("batchNo:" + batchNo + "的批付失败,responseMsg:" + failJson.getString("responseMsg"));
            } catch (Exception j) {
                //抛出异常说明受理成功
                responseCode = rspJson.getString("responseCode");
                logger.warn("failObject解析异常,则提交的数据全部受理成功.responseCode:" + responseCode);
            }
            //业务受理成功
            if ("0000".equals(responseCode)) {
                YfbPayroll yfbPayroll = saveBatch(params, channel.getRouter().getId(), merchant);

                int i = yfbPayrollDao.insert(yfbPayroll);
                if (i > 0) {
                    logger.warn(params.getString("batchNo") + "批付订单保存成功");
                    saveCard(params, merchantId);
                } else {
                    logger.warn(params.getString("batchNo") + "批付订单保存失败");
                }
                return AjaxResponse.success(rspJson.toJSONString());
            } else {
                //处理失败,需要加回金额
                logger.error("请求批付处理失败,需要回滚给用户的金额为:" + totalAmount.add(payCost));
                merchant.setBalance(merchant.getBalance().add(totalAmount.add(payCost)));
                merchantDao.update(merchant);
                return AjaxResponse.failure(rspJson.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResponse.failure();
    }


    /***
     * 保存批次订单详情信息
     * @param params
     * @param merchantId
     */

    private void saveCard(JSONObject params, Long merchantId) {
        String detailData = params.getString("detailData");
        String batchNo = params.getString("batchNo");
        List<Channel> channels = channelDao.findByDstDescribe(merchantId.toString());
        Channel channel = channels.get(0);
        String routerId = channel.getRouter().getId();
        System.out.println("routerId:" + routerId);
        MerchantRate byPayCost = merchantRateDao.findByPayCost(merchantId.toString(), routerId);
        BigDecimal payCost = null;
        if (byPayCost != null) {
            payCost = byPayCost.getPayCost();
        }
        JSONArray objects = JSONArray.parseArray(detailData);
        String company = merchantDao.get(merchantId.toString()).getCompany();
        for (int i = 0; i < objects.size(); i++) {
            DetailDataCard detailDataCard = new DetailDataCard();
            JSONObject jsonObject = objects.getJSONObject(i);
            detailDataCard.setMerchantId(merchantId);
            detailDataCard.setCompany(company);
            try {
                String xiafaId = jsonObject.getString("xiafaId");
                if (!StringUtils.isEmpty(xiafaId)) {
                    detailDataCard.setXiafaId(Long.valueOf(xiafaId));
                }
                detailDataCard.setSerialNo(jsonObject.getString("serialNo"));
                detailDataCard.setOrderName(jsonObject.getString("orderName"));
                detailDataCard.setReceiverCardNo(jsonObject.getString("receiverCardNo"));
                detailDataCard.setReceiverName(jsonObject.getString("receiverName"));
                detailDataCard.setReceiverType(jsonObject.getString("receiverType"));
                detailDataCard.setBankName(jsonObject.getString("bankName"));
                detailDataCard.setBankCode(jsonObject.getString("bankCode"));
                detailDataCard.setBankProvince(jsonObject.getString("bankProvince"));
                detailDataCard.setBankCity(jsonObject.getString("bankCity"));
                detailDataCard.setPayeeBankLinesNo(jsonObject.getString("payeeBankLinesNo"));
                detailDataCard.setAmount(changeF2Y(jsonObject.getString("amount")));
                detailDataCard.setBatchNo(batchNo);
                detailDataCard.setMerchantPayCost(payCost);
                detailDataCard.setStatus(1);
            } catch (JSONException JH) {
                logger.warn("json33格式解析错误");
            } catch (Exception e) {
                logger.warn("批次:" + batchNo + "在构件代付记录时出异常了");
            }
            int insert = detailDataCardDao.insert(detailDataCard);
            if (insert > 0) {
                logger.warn(jsonObject.getString("serialNo") + "订单保存成功");
            } else {
                logger.warn(jsonObject.getString("serialNo") + "订单保存失败");
            }
        }

    }

}
