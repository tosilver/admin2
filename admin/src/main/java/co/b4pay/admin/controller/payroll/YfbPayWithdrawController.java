package co.b4pay.admin.controller.payroll;

import co.b4pay.admin.common.biz.exception.BizException;

import co.b4pay.admin.common.util.Thread.ThreadPoolManager;
import co.b4pay.admin.common.web.YfbPayController;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.base.AjaxResponse;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.RouterService;

import co.b4pay.admin.service.YfbPayWithdrawService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/****
 * 易付宝批付到卡下单接口
 */
@Controller
@RequestMapping("/payroll/yfbWithPay.do")
public class YfbPayWithdrawController extends YfbPayController {
    private static final Logger logger = LoggerFactory.getLogger(YfbPayWithdrawController.class);
    //    private static final String ROUTER_KEY = "aliSPay";
    private static final String ROUTER_KEY = "yfbPayroll";
    //    private static final String ROUTER_KEY = "yfbPayroll";
    private static final String[] BODY_PARAMS = new String[]{"batchNo", "merchantId", "totalNum", "totalAmount", "detailData", "goodsType", "batchOrderName", "notifyUrl"};
    @Autowired
    private RouterService routerService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private YfbPayWithdrawService yfbPayWithdrawService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse doPost(HttpServletRequest request) throws IOException {
        try {
            Router router = routerService.get(ROUTER_KEY);
            logger.warn("请求的角色为:" + router.getName());
            System.out.println(request.getParameter("merchantId"));
            String secretKey = merchantService.get(request.getParameter("merchantId")).getSecretKey();
            if (router == null || router.getStatus() == -1) {
                throw new RuntimeException(String.format("[%s]路由异常"));
            }

            System.out.println("生成mac：" + getPayMac(request, secretKey));
            if (getPayMac(request, secretKey).equals(request.getParameter("mac"))) {
                String merchantId = request.getParameter("merchantId");
                if (merchantService.get(merchantId).getStatus() == 1) {
                    return yfbPayWithdrawService.executeWithdraw(getMerchantId(request), getParams(request));
                } else {
                    return AjaxResponse.failure(1004, "账户维护中");
                }
            } else {
                return AjaxResponse.failure(1001, "签名校验错误");
            }
//            return  null;
        } catch (BizException e) {
            logger.warn(e.getMessage());
            return AjaxResponse.failure(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResponse.failure();
        }
    }


    @Override
    protected String[] getRequiredParams() {
        return BODY_PARAMS;

    }

}
