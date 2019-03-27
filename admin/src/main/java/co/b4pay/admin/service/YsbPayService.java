package co.b4pay.admin.service;


import java.math.BigDecimal;


import co.b4pay.admin.dao.*;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import co.b4pay.admin.common.biz.exception.BizException;


import co.b4pay.admin.entity.Payroll;
import co.b4pay.admin.entity.base.AjaxResponse;


@Service
public class YsbPayService {

    private static final Logger logger = LoggerFactory.getLogger(YsbPayService.class);
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

    @Transactional
    public AjaxResponse execute(Long merchantId, JSONObject params) throws BizException {
        long time = System.currentTimeMillis();
        String downOrderId = params.getString("orderId");
        StringBuffer ls = new StringBuffer();
        ls.append(time);
//				   ls.append(merchantId);
        ls.append(downOrderId);
        String upOrderId = ls.toString();
        Router router = routerDao.get(ROUTER_KEY);
        Merchant merchant = merchantDao.get(merchantId);
        BigDecimal merchantPayCost = merchantRateDao.findByPayCost(merchantId.toString(), router.getId()).getPayCost();
        BigDecimal channelPayCost = channelDao.findByPayCost(router.getId()).get(0).getPayCost();
        Payroll payroll = new Payroll();
        payroll.setMerchantId(merchantId);
        payroll.setCompany(merchant.getCompany());
        payroll.setName(params.getString("name"));
        payroll.setCardNo(params.getString("cardNo"));
        payroll.setDownOrderId(params.getString("orderId"));
        payroll.setUpOrderId(upOrderId);
        payroll.setPurpose(params.getString("purpose"));
        payroll.setAmount(new BigDecimal(params.getString("amount")));
        payroll.setIdCardNo(params.getString("idCardNo"));
        payroll.setSummary(params.getString("summary"));
        payroll.setMerchantPayCost(merchantPayCost);
        payroll.setChannelPayCost(channelPayCost);
        payroll.setStatus(0);
        payroll.setNotifyUrl(params.getString("notifyUrl"));
        //System.out.println(payroll.toString());
        if (payrollDao.insert(payroll) > 0) {
            BigDecimal balance = merchant.getBalance();
            BigDecimal bamount = new BigDecimal(params.getString("amount"));
            BigDecimal mbalance = balance.subtract(bamount.add(merchantPayCost));
            merchant.setBalance(mbalance);
            merchantDao.update(merchant);
            return AjaxResponse.success(1000, "订单处理成功");
        } else {
            logger.info("代付订单保存失败" + payroll.getDownOrderId());
            System.out.println("代付订单保存失败");
            return AjaxResponse.failure(1004, "订单处理失败");
        }
    }
}