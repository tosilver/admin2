package co.b4pay.admin.controller.payroll;

import co.b4pay.admin.entity.Merchant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Payroll;


import co.b4pay.admin.entity.base.ResponseMode;
import co.b4pay.admin.service.ChannelService;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.PayrollService;

import co.b4pay.admin.service.YsbNotifyService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/payroll/ysbNotify.do")
public class YsbNotifyController {
    private static final Logger logger = LoggerFactory.getLogger(YsbPayController.class);
    private static final String ROUTER_KEY = "ysbPayroll";

    @Autowired
    PayrollService payrollService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    YsbNotifyService ysbNotifyService;
    @Autowired
    ChannelService channelService;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    void responseCallBackForm(ResponseMode responseMode, HttpServletResponse response) throws Exception {

        System.out.println("结果通知>>>" + responseMode.toString());
        String upOrderId = responseMode.getOrderId();
        Payroll payroll = payrollService.findByUpOrderId(upOrderId);
        Long merchantId = payroll.getMerchantId();
        List<Channel> byPayCost = channelService.findByPayCost(ROUTER_KEY);
        Channel channel = byPayCost.get(0);
        String secretKey = channel.getProdPublicKey();
        StringBuffer sf = new StringBuffer();
        sf.append("accountId=").append(channel.getProdPid());
        sf.append("&orderId=").append(upOrderId);
        sf.append("&amount=").append(responseMode.getAmount());
        sf.append("&result_code=").append(responseMode.getResult_code());
        sf.append("&result_msg=").append(responseMode.getResult_msg());
        sf.append("&key=").append(secretKey);
        System.out.println("验签串>>>：" + sf.toString());
        String responseMac = responseMode.getMac();
        System.out.println("接收到的MAC>>>" + responseMac);
        String mac = Md5Encrypt.md5(sf.toString()).toUpperCase();
        System.out.println("加密MAC>>>" + mac);
        if (responseMac.equals(mac)) {
            if (responseMode.getResult_code().equals("0000")) {
                payroll.setStatus(2);
                payroll.setContent(responseMode.toString());
                payrollService.update(payroll);
                logger.info("上游订单付款成功");
            } else {
                Merchant merchant = merchantService.get(merchantId.toString());
                BigDecimal balance = merchant.getBalance();
                BigDecimal payrollCost = payroll.getChannelPayCost().add(payroll.getMerchantPayCost());

                BigDecimal balances = balance.add(payroll.getAmount().add(payrollCost));
                merchant.setBalance(balances);
                merchantService.update(merchant);

//				BigDecimal amountLimit = channel.getAmountLimit();
//				BigDecimal amountLimits = amountLimit.add(payroll.getAmount());
//				channel.setAmountLimit(amountLimits);
//				channelService.update(channel);
                payroll.setStatus(3);
                payroll.setContent(responseMode.toString());
                payrollService.update(payroll);
                logger.info("上游订单付款失败");
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("000000");
            //ysbNotifyService.execute(merchantId,payroll,responseMode);
            logger.info("上游回调通知验签成功");
            System.out.println("上游验签成功");
        } else {
            logger.info("上游回调通知验签失败");
            System.out.println("验签失败");

        }
    }
}
