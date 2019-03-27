package co.b4pay.admin.controller.payroll;

import co.b4pay.admin.common.YfbUtil.PayrollConstants;

import co.b4pay.admin.common.web.YfbPayController;
import co.b4pay.admin.entity.base.AjaxResponse;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.YfbQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

/***
 * 转账到易付宝查询
 */
//
//@Controller
@RequestMapping("payroll/YfbTransferQuery.do")
public class YfbTransferQueryController extends YfbPayController {
    private static final Logger logger = LoggerFactory.getLogger(YfbTransferQueryController.class);

    @Autowired
    YfbQueryService yfbQueryService;
    @Autowired
    MerchantService merchantService;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse doPost(HttpServletRequest request) throws IOException {
        String secretKey = merchantService.get(request.getParameter("merchantId")).getSecretKey();
        String mac = getQueryMac(request, secretKey);
        HashMap<String, String> params = new HashMap<>();
        params.put("merchantId", request.getParameter("merchantId"));
        params.put("batchNo", request.getParameter("batchNo"));
        if (mac.equals(request.getParameter("mac"))) {
            return AjaxResponse.success(yfbQueryService.batchWithDraw(params, PayrollConstants.YFB_URL_TF_QUERY));
        } else {
            return AjaxResponse.failure(1001, "签名验证失败");
        }
    }


}

