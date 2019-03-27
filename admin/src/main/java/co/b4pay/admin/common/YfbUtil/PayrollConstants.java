package co.b4pay.admin.common.YfbUtil;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 代付请求地址
 */
@Component
public final class PayrollConstants implements InitializingBean {
    /**
     * 银生宝地址IP
     */
    //银生宝代付请求接口地址
    public static final String YSB_URL_PRO = "http://pay.unspay.com:8081/delegate-pay-front/delegatePay/fourElementsPay";
    //银生宝代付订单查询接口地址
    public static final String YSB_URL_QRDER = "http://pay.unspay.com:8081/delegate-pay-front/delegatePay/queryOrderStatus";
    //银生宝账户余额查询接口地址
    public static final String YSB_URL_BALANCE = "http://pay.unspay.com:8081/delegate-pay-front/delegatePay/queryBlance";
    /**
     * 易付宝地址IP
     */
    //易付宝批付到易付宝接口地址
//    public static final String YFB_URL_TF="https://wagtestpre.suning.com/epps-twg/transferAcquire.htm";
    public static final String YFB_URL_TF = "https://tag.yifubao.com/epps-twg/transferAcquire.htm";
    //public static final String YFB_URL_TF = "127.0.0.1";
    //易付宝批付到卡接口地址
    public static final String YFB_URL_WR = "https://wag.yifubao.com/epps-wag/withdraw.htm";
    //public static final String YFB_URL_WR = "127.0.0.1";
    //易付宝批付到易付宝查询订单接口地址
    public static final String YFB_URL_TF_QUERY = "https://tag.yifubao.com/epps-twg/transferQuery.htm";
    //public static final String YFB_URL_TF_QUERY = "127.0.0.1";
    //易付宝批付到卡查询订单接口地址
    public static final String YFB_URL_WR_QUERY = "https://wag.yifubao.com/epps-wag/withdrawQuery.htm";
    //public static final String YFB_URL_WR_QUERY = "127.0.0.1";
    /**
     * 测试环境下
     */
//    //银生宝异步通知地址
//    public static final String YYF_YSB_URL="http://admin.sssx.red/payroll/ysbNotify.do";
//    //易付宝到易付宝异步通知地址
//    public static final String YYF_YFB_TF_URL="http://admin.sssx.red/payroll/yfbTransferNotify.do";
//    //易付宝到卡异步通知地址
//    public static final String YYF_YFB_WR_URL="http://admin.sssx.red/payroll/yfbWithdrawNotify.do";
    /**
     * 海哥1号平台
     */
    //银生宝异步通知地址
//    public static final String YYF_YSB_URL="http://admin.b4pay-hg.hk/payroll/ysbNotify.do";
//    //易付宝到易付宝异步通知地址
//    public static final String YYF_YFB_TF_URL="http://admin.b4pay-hg.hk/payroll/yfbTransferNotify.do";
//    //易付宝到卡异步通知地址
//    public static final String YYF_YFB_WR_URL="http://admin.b4pay-hg.hk/payroll/yfbWithdrawNotify.do";

    /**
     * 海哥2号平台
     */
    //银生宝异步通知地址
//    public static final String YYF_YSB_URL="http://open.admin.b4pay-hg.hk/payroll/ysbNotify.do";
//    //易付宝到易付宝异步通知地址
//    public static final String YYF_YFB_TF_URL="http://open.admin.b4pay-hg.hk/payroll/yfbTransferNotify.do";
//    //易付宝到卡异步通知地址
//    public static final String YYF_YFB_WR_URL="http://open.admin.b4pay-hg.hk/payroll/yfbWithdrawNotify.do";

    /**
     * 张总平台
     */
//    public static final String YYF_YSB_URL="http://admin.yyfpay.hk/payroll/ysbNotify.do";
//    //易付宝到易付宝异步通知地址
//    public static final String YYF_YFB_TF_URL="http://admin.yyfpay.hk/payroll/yfbTransferNotify.do";
//    //易付宝到卡异步通知地址
//    public static final String YYF_YFB_WR_URL="http://admin.yyfpay.hk/payroll/yfbWithdrawNotify.do";

    /**
     * 代付平台
     */
    private static String YYF_DOMAIN_URL;

    @Value("${YYF_DOMAIN_URL}")
    public void setYyfDomainUrl(String yyfDomainUrl) {
        YYF_DOMAIN_URL = yyfDomainUrl;
    }

    //易付宝批付地址
    public static String YYF_YSB_PF_URL;

    public static String YYF_YSB_URL;
    //易付宝到易付宝异步通知地址
    public static String YYF_YFB_TF_URL;

    //易付宝到卡异步通知地址
    public static String YYF_YFB_WR_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        YYF_YSB_PF_URL = YYF_DOMAIN_URL + "payroll/yfbWithPay.do";
        YYF_YSB_URL = YYF_DOMAIN_URL + "payroll/ysbNotify.do";
        YYF_YFB_TF_URL = YYF_DOMAIN_URL + "payroll/yfbTransferNotify.do";
        YYF_YFB_WR_URL = YYF_DOMAIN_URL + "payroll/yfbWithdrawNotify.do";
    }
}
