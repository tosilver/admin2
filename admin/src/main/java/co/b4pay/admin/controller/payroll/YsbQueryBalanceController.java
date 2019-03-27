package co.b4pay.admin.controller.payroll;

import co.b4pay.admin.common.core.signature.Md5Encrypt;
import co.b4pay.admin.entity.base.AjaxResponse;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.YsbQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("payroll/ysbQueryBalance.do")
public class YsbQueryBalanceController {
    @Autowired
    MerchantService merchantService;
    @Autowired
    YsbQueryService ysbQueryService;


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse doPost(HttpServletRequest request) throws IOException {
        String mac = getMac(request);
        if (mac.equals(request.getParameter("mac"))) {
            return ysbQueryService.queryBalance(request.getParameter("merchantId"));
        } else {
            return AjaxResponse.failure(1001, "签名验证失败");
        }


    }

    public String getMac(HttpServletRequest request) throws IOException {
        StringBuffer sf = new StringBuffer();
        sf.append("merchantId=").append(request.getParameter("merchantId"));
        sf.append("&key=").append(merchantService.get(request.getParameter("merchantId")).getSecretKey());
        return Md5Encrypt.md5(sf.toString()).toUpperCase();
    }

}
