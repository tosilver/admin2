package co.b4pay.admin.service;

import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.common.util.HttpUtils;
import co.b4pay.admin.dao.*;
import co.b4pay.admin.entity.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

@Service
@Transactional(rollbackFor = Exception.class)
public class YfbNotifyService extends YfbPayService {
    private static final Logger logger = LoggerFactory.getLogger(YfbNotifyService.class);

    private static final String ROUTER_KEY = "yfbPayroll";
    @Autowired
    YfbPayrollDao yfbPayrollDao;
    @Autowired
    DetailDataAccountDao detailDataAccountDao;
    @Autowired
    DetailDataCardDao detailDataCardDao;
    @Autowired
    MerchantDao merchantDao;
    @Autowired
    MerchantRateDao merchantRateDao;
    @Autowired
    XiafaDao xiafaDao;

    public void transferExecute(JSONObject params) {
        YfbPayroll yfbPayroll = updateYfbPayroll(params);
        String transferOrders = params.getString("transferOrders");
        JSONArray objects = JSONArray.parseArray(transferOrders);
        for (int i = 0; i < objects.size(); i++) {
            JSONObject json = objects.getJSONObject(i);
            String serialNo = json.getString("serialNo");
            DetailDataAccount bySerialNo = detailDataAccountDao.findBySerialNo(serialNo);
            String success = json.getString("success");
            if (success.equals("true")) {
                bySerialNo.setStatus(1);
            } else if (success.equals("false")) {
                bySerialNo.setStatus(2);
            } else {
                bySerialNo.setStatus(3);
            }
            detailDataAccountDao.update(bySerialNo);
        }
        downNotify(yfbPayroll, params);
    }

    /***
     * 批付到卡通知
     * @param contentJson
     * @return
     * @throws Exception
     */
    public String withdrawExecute(JSONObject contentJson) throws Exception {
        logger.info("1进入回调业务层代码");
        logger.warn("2易付宝回调内容contentJson:" + contentJson);
        //更新批次记录
        YfbPayroll yfbPayroll = updateYfbPayroll(contentJson);
        /**
         * 判断批次通知是否重复,若数据库中有此记录,说明是重复的通知
         */
        //获得商户id
        String merchantId = yfbPayroll.getMerchantId();

        //若是第一次收到回调，则回调内容为空
        if (!StringUtils.isEmpty(yfbPayroll.getContent())) {
            BigDecimal failAmount = changeF2Y(contentJson.getString("failAmount"));
            //若批次处理失败，则回滚金额
            if (!failAmount.equals(BigDecimal.ZERO)) {
                Merchant merchant = merchantDao.get(merchantId);
                BigDecimal balance = merchant.getBalance();

                //计算手续费
                MerchantRate byPayCost = merchantRateDao.findByPayCost(merchant.getId(), ROUTER_KEY);
                BigDecimal payCost = byPayCost.getPayCost();

                logger.warn("回调得知充值失败,需要回滚的金额为:" + failAmount.add(payCost));
                BigDecimal newBalance = balance.add(failAmount.add(payCost));
                merchant.setBalance(newBalance);
                merchantDao.update(merchant);
            }
           /* //支付成功，扣除手续费
            Integer successNum = Integer.valueOf(contentJson.getString("successNum"));
            MerchantRate merchantRate = merchantRateDao.findByPayCost(merchantId, ROUTER_KEY);
            BigDecimal payCost = merchantRate.getPayCost();
            //扣的手续费为一笔的代付成本 X 批付笔数
            BigDecimal paycost = payCost.multiply(BigDecimal.valueOf(successNum));*/
        }
        //获得批付订单中详单的信息
        String transferOrders = contentJson.getString("transferOrders");
        JSONArray transferOrderJson = JSONArray.parseArray(transferOrders);
        String notify = null;
        /***
         * 更新订单批次订单中每笔详情
         * 如有订单在处理状态统一返回false等待下次通知
         * 如2小时依旧有订单处于processing处理状态请通知运营找上游核实该笔订单
         */
        for (int i = 0; i < transferOrderJson.size(); i++) {
            JSONObject json = transferOrderJson.getJSONObject(i);
            String serialNo = json.getString("serialNo");
            DetailDataCard dataCard = detailDataCardDao.findBySerialNo(serialNo);
            //更改对应下发记录的数据
            Xiafa xiafa = null;
            Long xiafaId = dataCard.getXiafaId();
            if (xiafaId != null) {
                xiafa = xiafaDao.get(xiafaId);
            }
            String success = json.getString("success");
            if ("true".equals(success)) {
                dataCard.setStatus(2);
                if (xiafa != null) {
                    xiafa.setStatus(2);
                }
                notify = "true";
            } else if ("false".equals(success)) {
                dataCard.setStatus(3);
                if (xiafa != null) {
                    xiafa.setStatus(3);
                }
                notify = "true";
            } else {
                dataCard.setStatus(1);
                notify = "false";
            }
            //更新批付记录状态
            detailDataCardDao.update(dataCard);
            //若批付记录有关联下发记录,同步更新状态
            if (xiafa != null) {
                xiafaDao.update(xiafa);
            }
        }
        try {
            downNotify(yfbPayroll, contentJson);
        } catch (Exception e) {
            logger.warn("代付回调给下游失败，失败原因：" + e);
        }
        return notify;
    }

    /**
     * 更新批次订单状态
     *
     * @param contentJson
     * @return
     */
    public YfbPayroll updateYfbPayroll(JSONObject contentJson) {
        String batchNo = contentJson.getString("batchNo");
        YfbPayroll yfbPayroll = yfbPayrollDao.findByBatchNo(batchNo);
        Integer status = yfbPayroll.getStatus();
        //05代表支付成功,07代表处理成功，满足这个两个条件说明信息无需更改
        if (status == 5 || status == 7) {
            return null;
        }
        //将易付宝返回的回调信息存储起来
        yfbPayroll.setContent(contentJson.toJSONString());
        yfbPayroll.setStatus(Integer.valueOf(contentJson.getString("status")));
        yfbPayrollDao.update(yfbPayroll);
        return yfbPayroll;
    }

    /***
     * 下游回调通知  暂不需要
     * @param yfbPayroll
     * @param params
     */
    public void downNotify(YfbPayroll yfbPayroll, JSONObject params) {
        StringBuffer sf = new StringBuffer();
        String merchantId = yfbPayroll.getMerchantId();
        sf.append("merchantId=").append(merchantId);
        sf.append("&contentJson=").append(params.toJSONString());
        sf.append("&key=").append(merchantDao.get(merchantId.toString()).getSecretKey());
        try {
            String mac = Md5Encrypt.md5(sf.toString()).toUpperCase();
            HashMap<String, String> yfbParam = new HashMap<String, String>();
            yfbParam.put("merchantId", merchantId);
            yfbParam.put("contentJson", params.toJSONString());
            yfbParam.put("mac", mac);
            String result = HttpUtils.doPost(yfbPayroll.getNotifyUrl(), yfbParam);
            if (result.equals("SUCCESS")) {
                logger.warn("下游通知成功");
            } else {
                logger.warn("下游通知失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
