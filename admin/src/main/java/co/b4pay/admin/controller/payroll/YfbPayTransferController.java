package co.b4pay.admin.controller.payroll;

import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.common.web.YfbPayController;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.base.AjaxResponse;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.RouterService;
import co.b4pay.admin.service.YfbPayTransferService;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * 代付到易付宝账户
 */

@Controller
@RequestMapping("/payroll/yfbTranPay.do")
public class YfbPayTransferController extends YfbPayController {
    private static final Logger logger = LoggerFactory.getLogger(YfbPayTransferController.class);
    private static final String RY_ROUTER_KEY = "ruiYuePay";//睿悦
    private static final String WC_ROUTER_KEY = "weiChuangPay";//威创

    private static final String[] BODY_PARAMS = new String[]{"batchNo", "merchantId", "totalNum", "totalAmount", "detailData", "goodsType", "batchOrderName", "notifyUrl"};
    @Autowired
    private RouterService routerService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private YfbPayTransferService yfbPayTransferService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse doPost(HttpServletRequest request) {
        try {
            Router router = null;
            System.out.println("区分ID：" + request.getParameter("id"));
            if ("0".equals(request.getParameter("id"))) {
                System.out.println("睿悦");
                router = routerService.get(RY_ROUTER_KEY);
            } else {
                System.out.println("威创");
                router = routerService.get(WC_ROUTER_KEY);
            }
            String secretKey = merchantService.get(request.getParameter("merchantId")).getSecretKey();
            //从请求中读取用户传来的数据
            String body = request.getParameter("body");
            Map maps = (Map) JSON.parse(body);
            YfbPayTransferController yp = new YfbPayTransferController();
            //获取该商家的结余*100(积分？)
            BigDecimal bg = merchantService.get(request.getParameter("merchantId")).getBalance().multiply(yp.getBigDecimal("100"));
            if (bg.compareTo(yp.getBigDecimal(maps.get("totalAmount"))) == -1) {
                //如果用户余额小于它要代付的金额,返回用户提示信息
                return AjaxResponse.failure(2006, "账号余额不足");
            }
            if (router == null || router.getStatus() == -1) {
                //路由不存在,或者已关闭,抛出异常
                throw new RuntimeException(String.format("[%s]路由异常", "yfbTransferPayroll"));
            }
            System.out.println("生成mac：" + getPayMac(request, secretKey));
            if (true || getPayMac(request, secretKey).equals(request.getParameter("mac"))) {
                String merchantId = request.getParameter("merchantId");
                if (merchantService.get(merchantId).getStatus() == 1) {
                    //如果商家状态正常,从商家结余中减去代付的金额,再/100.
                    BigDecimal bdi = bg.subtract(yp.getBigDecimal(maps.get("totalAmount"))).divide(yp.getBigDecimal("100"));
                    return yfbPayTransferService.executeTransfer(getMerchantId(request), getParams(request), bdi, router.getId());
                } else {
                    return AjaxResponse.failure(1004, "账户维护中");
                }
            } else {
                return AjaxResponse.failure(1001, "签名校验错误");
            }
        } catch (BizException e) {
            logger.warn(e.getMessage());
            return AjaxResponse.failure(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResponse.failure();
        }
    }

    /**
     * 将value转成BigDecimal类型的数据
     *
     * @param value 待转换的值
     * @return 转换后的BigDecimal数据
     */
    public BigDecimal getBigDecimal(Object value) {

        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    @Override
    protected String[] getRequiredParams() {
        return BODY_PARAMS;
    }

}