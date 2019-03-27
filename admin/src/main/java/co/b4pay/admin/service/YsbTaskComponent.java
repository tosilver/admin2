package co.b4pay.admin.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import co.b4pay.admin.common.YfbUtil.PayrollConstants;
import co.b4pay.admin.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;


import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.common.util.HttpUtils;
import co.b4pay.admin.controller.payroll.YsbPayController;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.Payroll;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.base.ResponseMode;


@Component
public class YsbTaskComponent {
    private static final Logger logger = LoggerFactory.getLogger(YsbPayController.class);
    //private static final String YSB_URL = "http://pay.unspay.com:8081/delegate-pay-front/delegatePay/fourElementsPay";
//	private static final String YPL_URL = "http://admin.b4pay-hg.hk/payroll/ysbNotify.do";
    private static final String YPL_URL = "http://admin.b4pay.hk/payroll/ysbNotify.do";
    private static final String ROUTER_KEY = "ysbPayroll";
    @Autowired
    PayrollDao payrollDao;
    @Autowired
    MerchantDao merchantDao;
    @Autowired
    MerchantRateDao merchantRateDao;
    @Autowired
    ChannelDao channelDao;
    @Autowired
    RouterDao routerDao;
    @Autowired
    YsbNotifyService ysbNotifyService;

    @Scheduled(fixedDelay = 3 * 1000)
    public void ysbTaskExecute() throws IOException {

        Router router = routerDao.get(ROUTER_KEY);
        List<Channel> channels = channelDao.findByPayCost(router.getId());
        if (channels != null && channels.size() > 0) {
            Channel channel = channels.get(0);
            if (channel.getStatus() == 1) {
                List<Payroll> payrolls = payrollDao.findByStatus(0);
                if (payrolls != null && payrolls.size() > 0) {
                    for (int i = 0, length = payrolls.size(); i < length; i++) {
                        Payroll payroll = payrolls.get(i);
                        ResponseMode responseMode = ysbPayroll(payroll);
                        logger.error("responseMode :" + responseMode.toString());
                        if (responseMode != null) {
                            if (responseMode.getResult_code().equals("0000")) {
                                BigDecimal channelPayCost = payroll.getChannelPayCost();
                                Merchant merchant = merchantDao.get(payroll.getMerchantId());
                                BigDecimal balance = merchant.getBalance();
                                BigDecimal subtract = balance.subtract(channelPayCost);
                                merchant.setBalance(subtract);
                                merchantDao.update(merchant);
                                //更新渠道可用代付额度
                                String amountLimits = QueryBalance(channel);
                                if (amountLimits != null) {
                                    BigDecimal bigDecimal = new BigDecimal(amountLimits);
                                    channel.setAmountLimit(bigDecimal);
                                    channelDao.update(channel);
                                }

                                payroll.setStatus(1);
                                payroll.setContent(responseMode.toString());
                                payrollDao.update(payroll);
                                logger.info("订单号" + payroll.getUpOrderId() + "处理成功");
                            } else {
                                payroll.setStatus(-1);
                                payroll.setContent(responseMode.toString());
                                logger.info("订单号" + payroll.getUpOrderId() + "处理失败" + responseMode.toString());
                                Long merchantId = payroll.getMerchantId();
                                Merchant merchant = merchantDao.get(merchantId);
                                BigDecimal balance = merchant.getBalance();
                                BigDecimal merchantPayCost = payroll.getMerchantPayCost();
                                BigDecimal amount = payroll.getAmount();
                                BigDecimal balances = balance.add(amount.add(merchantPayCost));
                                merchant.setBalance(balances);
                                merchantDao.update(merchant);
                                payrollDao.update(payroll);

//					   String execute = ysbNotifyService.execute(merchantId, payroll, responseMode);
//					   if (execute.equals("100000")) {
//						   logger.info("商户" + merchantId + "接受通知成功");
//					   } else {
//						   logger.info("商户" + merchantId + "接受通知失败");
//					   }
                            }
                        }
                    }
                }
            } else {
                logger.warn("1113", "渠道暂不可用");
            }
        }


    }


    public ResponseMode ysbPayroll(Payroll payroll) throws IOException {
        Router router = routerDao.get(ROUTER_KEY);

        String upOrderId = payroll.getUpOrderId();

        Channel channel = channelDao.findByPayCost(ROUTER_KEY).get(0);

        HashMap<String, String> yspParam = new HashMap<String, String>();
        yspParam.put("accountId", channel.getProdPid());
        yspParam.put("name", payroll.getName());
        yspParam.put("cardNo", payroll.getCardNo());
        yspParam.put("orderId", upOrderId);
        yspParam.put("purpose", payroll.getPurpose());
        yspParam.put("amount", payroll.getAmount().toPlainString());
        yspParam.put("idCardNo", payroll.getIdCardNo());
        yspParam.put("summary", payroll.getSummary());
        yspParam.put("responseUrl", PayrollConstants.YYF_YSB_URL);
        yspParam.put("mac", ysbKey(yspParam, channel));
        try {
            String result = HttpUtils.doPost(PayrollConstants.YSB_URL_PRO, yspParam);
            JSONObject json = (JSONObject) JSONObject.parse(result);
            ResponseMode responseMode = new ResponseMode();
            responseMode.setResult_code(json.getString("result_code"));
            responseMode.setResult_msg(json.getString("result_msg"));
            return responseMode;
        } catch (Exception e) {
            logger.error("1111", "调用三方接口异常");
            return null;
        }
    }

    //MD5加密返回mac
    public String ysbKey(HashMap<String, String> yspParam, Channel channel) throws IOException {
        StringBuffer sf = new StringBuffer();
        sf.append("accountId=").append(yspParam.get("accountId"));
        sf.append("&name=").append(yspParam.get("name"));
        sf.append("&cardNo=").append(yspParam.get("cardNo"));
        sf.append("&orderId=").append(yspParam.get("orderId"));
        sf.append("&purpose=").append(yspParam.get("purpose"));
        sf.append("&amount=").append(yspParam.get("amount"));
        sf.append("&idCardNo=").append(yspParam.get("idCardNo"));
        sf.append("&summary=").append(yspParam.get("summary"));
        sf.append("&responseUrl=").append(yspParam.get("responseUrl"));
        sf.append("&key=").append(channel.getProdPublicKey());
        String mac = Md5Encrypt.md5(sf.toString()).toUpperCase();
        return mac;
    }

    //查询渠道余额
    public String QueryBalance(Channel channel) {
        StringBuffer sf = new StringBuffer();
        sf.append("accountId=").append(channel.getProdPid());
        sf.append("&key=").append(channel.getProdPublicKey());
        HashMap<String, String> yspParam = new HashMap<String, String>();
        String balance = null;
        try {
            String mac = Md5Encrypt.md5(sf.toString()).toUpperCase();
            HashMap<String, String> params = new HashMap<String, String>();
            yspParam.put("accountId", channel.getProdPid());
            yspParam.put("mac", mac);
            String result = HttpUtils.doPost(PayrollConstants.YSB_URL_BALANCE, params);
            JSONObject json = (JSONObject) JSONObject.parse(result);
            String result_code = json.getString("result_code");
            String result_msg = json.getString("result_msg");
            if (result_code.equals("0000")) {
                balance = json.getString("balance");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }
}