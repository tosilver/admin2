package co.b4pay.admin.controller.check;

import co.b4pay.admin.common.core.security.HttpsUtils;
import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.common.util.HttpClient;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.controller.Utils.Constants;
import co.b4pay.admin.controller.Utils.HmacSHA1Signature;
import co.b4pay.admin.controller.Utils.SignatureUtil;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.entity.*;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SignatureException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 自检Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("mallcheck")
public class MallCheckController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(MallCheckController.class);

    private static final String notify_url="http://api.b4bisi.com/notify/mallPayNotify.do";

    private static final String serverUrl = "http://api.b4bisi.com:9988";
    //private static final String serverUrl = "http://192.168.101.69:9988"; // 开发环境

    protected  String get(String apiUri, Map<String, Object> params) throws IOException, SignatureException {
        return HttpsUtils.get(serverUrl + apiUri, null, oToString(signParams(params)));
    }

    protected  String post(String apiUri, Map<String, Object> params) throws IOException, SignatureException {
        return HttpsUtils.post(serverUrl + apiUri, null, oToString(signParams(params)));
    }

    @Autowired
    private ConsumeService consumeService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RouterService routerService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MallAddressService mallAddressService;
    @Autowired
    private MerchantRateService merchantRateService;
    @Autowired
    private YfbPayrollService yfbPayrollService;
    @Autowired
    private MallTestTradeService mallTestTradeService;


    private static final HmacSHA1Signature hmacSHA1Signature = new HmacSHA1Signature();

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("mallcheck:list")
    public String list(Model model, @PageAttribute Page<Consume> page, HttpServletRequest request) {
        Params p = page.getParams();
        String startDate = null;
        if (p != null) {
            startDate = p.containsKey("startDate") ? p.getString("startDate") : null;
        } else {
            p = new Params();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isBlank(startDate)) {
            //获取每天的0点0分0秒
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR,0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            startDate = sdf.format(c.getTime());
            p.put("startDate", startDate);
        }
        page.setParams(p);
        //查找营业额排名前4的商城
        List<MallAddress> top4 = mallAddressService.findByTop4();
        model.addAttribute("top4",top4);
        //查找所有通道
        List<MallAddress> addressList = mallAddressService.findList();
        model.addAttribute("addressList",addressList);
        //查询成功率
        Double successRate = consumeService.getSuccessRate(page);
        model.addAttribute("successRate",successRate);


        return "check/checkList";
    }



    @RequestMapping(value = "form",method =RequestMethod.GET)
    @RequiresPermissions("mallcheck:form")
    public String form(Model model, @PageAttribute Page<MallAddress> page) {
        Page<MallAddress> addressPage = mallAddressService.findPage(page);
        List<MallAddress> addressList = addressPage.getList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (MallAddress address : addressList) {
            Map<String, Object> map = new HashMap<>();
            map.put("malladdress", address);
            mapList.add(map);
        }
        //自动生成系统订单号
        Calendar calendar = Calendar.getInstance();
        String tradeNo = String.format("%s%s", DateUtil.dateToStr(calendar.getTime(), DateUtil.YMdhmsS_noSpli), RandomStringUtils.randomNumeric(15));
        //生成日期
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        model.addAttribute("page", addressPage);
        model.addAttribute("tradeNo",tradeNo);
        model.addAttribute("time",dateString);
        return "check/checkFrom";
    }

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public String updateStatus(Model model,String tradeNo,String totalAmout,String time,String payType,String mallAddress,String port) {
        System.out.println("订单号:"+tradeNo);
        System.out.println("金额:"+totalAmout);
        System.out.println("下单时间:"+time);
        System.out.println("支付方式:"+payType);
        System.out.println("商城地址:"+mallAddress);
        System.out.println("请求端口:"+port);
        String merchantId = LoginHelper.getId();
        //把前端传过来的字符转换成金额
        BigDecimal amout = new BigDecimal(totalAmout);
        //转换单位为分
        BigDecimal totalFee = amout.multiply(new BigDecimal(100));
        int totalFeeInt = totalFee.intValue();
        if("3".equals(port)){
            //选择的通道为商城
            //构建请求参数
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            //long diyOrderId = idWorker.nextId();
            sb.append("out_trade_no=").append(tradeNo).append("&");
            sb.append("total_fee=").append(totalFeeInt).append("&");
            sb.append("time=").append(time).append("&");
            sb.append("ali_notify_url=").append(notify_url).append("&");
            sb.append("pay_type=").append(payType);
            logger.warn("参数:" + sb.toString());
            model.addAttribute("mallAddress",mallAddress);
            model.addAttribute("parameter",sb.toString());
            try {
                // 发送get请求
                HttpClient httpClient = new HttpClient(mallAddress + sb.toString());
                // 返回数据
                logger.warn("请求开始:");
                httpClient.get();
                logger.warn("请求结束!!!!!!!");
                String content = httpClient.getContent();
                logger.info("[商城支付]应答报文:  " + content);
                if (content != null){
                    JSONObject rspJson = JSON.parseObject(content);
                    if (StringUtils.isNotBlank(rspJson.getString("code")) && "2000".equals(rspJson.getString("code"))) {
                        String qrcode = rspJson.getString("data");
                        logger.info("[商城支付]支付链接:", qrcode);
                        MallTestTrade mallTestTrade=new MallTestTrade();
                        mallTestTrade.setId(tradeNo);
                        mallTestTrade.setMerchantId(merchantId);
                        mallTestTrade.setTotalAmount(amout);
                        mallTestTrade.setTime(time);
                        mallTestTrade.setNotifyUrl(notify_url);
                        mallTestTrade.setPayType(payType);
                        mallTestTrade.setStatus(1);
                        mallTestTradeService.save(mallTestTrade);
                        logger.info("保存测试订单成功");
                        model.addAttribute("qrcode",qrcode);
                    } else {
                        String msg = rspJson.getString("msg");
                        String code = rspJson.getString("code");
                        model.addAttribute("msg",msg);
                        model.addAttribute("code",code);
                    }
                }else {
                    model.addAttribute("msg","商城响应异常,请检查请求参数");
                }
            } catch (IOException e) {
               model.addAttribute("msg","请求失败");
            } catch (ParseException e) {
               model.addAttribute("msg","响应失败");
            }
        }else if("2".equals(port))  {
            JSONObject jsonObject = mallPayTest(tradeNo, String.valueOf(totalFeeInt), time, payType);
            logger.info("[新模式]响应参数为:"+jsonObject.toString());
            String data = jsonObject.getString("data");
            JSONObject dataJson = JSONObject.parseObject(data);
            System.out.println(dataJson);
            String requestedURL = dataJson.getString("RequestedURL");
            String msg = dataJson.getString("msg");
            model.addAttribute("RequestedURL",requestedURL);
            model.addAttribute("msg",msg);
        }else if("1".equals(port)){

            JSONObject jsonObject = mallPay(tradeNo, String.valueOf(totalFeeInt), time, payType);
            String data = jsonObject.getString("data");
            System.out.println(data);
            JSONObject dataJson = JSONObject.parseObject(data);
            String qrcode = dataJson.getString("qr_code");
            String msg = dataJson.getString("msg");
            System.out.println(qrcode);
            model.addAttribute("qrcode",qrcode);
            model.addAttribute("msg",msg);
        }
        return "check/location";
    }



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

    /**
     * 签名方法
     */
    private Map<String, Object> signParams(Map<String, Object> params) throws IOException, SignatureException {
        String merchantIds = LoginHelper.getMerchantIds();
        String merchantId = merchantIds.substring(0, merchantIds.length() - 1);
        params.remove("signature");
        // 系统级别参数 merchantId、timestamp、signature
        params.put("merchantId",merchantId);
        String timestamp = System.currentTimeMillis()+"";
        params.put("timestamp",timestamp);
        String content = SignatureUtil.getSignatureContent(params, true);
        Merchant merchant = merchantService.get(merchantIds);
        String secretKey = merchant.getSecretKey();
        String sign = hmacSHA1Signature.sign(content, secretKey, Constants.CHARSET_UTF8);//阿里签名
        params.put("signature", sign);
        return params;
    }


    /**
     *调用新模式接口
     */
    public JSONObject mallPayTest(String tradeNo,String totalAmout,String time,String payType){
        JSONObject jsonObject=null;
        try {
            Map<String, Object> params = new HashMap<>();
            // 业务参数
            params.put("tradeNo",tradeNo);
            params.put("totalAmount", totalAmout);
            params.put("notifyUrl", notify_url);
            params.put("time", time);
            logger.info("[新模式]请求参数:"+params.toString());
            if ("1".equals(payType)){
                //支付宝
                jsonObject = JSONObject.parseObject(post("/pay/mallTestPay.do", params));
            }else if ("2".equals(payType)){
                //微信
                /*JSONObject jsonObject = JSONObject.parseObject(post("/pay/mallTestPay.do", params));
                System.out.println(jsonObject);
                return jsonObject;*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     *调用旧模式接口
     */
    public JSONObject mallPay(String tradeNo,String totalAmout,String time,String payType){
        JSONObject jsonObject=null;
        try {
            Map<String, Object> params = new HashMap<>();
            // 业务参数
            params.put("tradeNo",tradeNo);
            params.put("totalAmount", totalAmout);
            params.put("notifyUrl", notify_url);
            params.put("time", time);
            params.put("money",totalAmout);
            logger.info("[旧模式]请求参数:"+params.toString());
            if ("1".equals(payType)){
                //支付宝
                jsonObject = JSONObject.parseObject(post("/pay/mallPay.do", params));

            }else if ("2".equals(payType)){
                //微信
                /*JSONObject jsonObject = JSONObject.parseObject(post("/pay/mallTestPay.do", params));
                System.out.println(jsonObject);
                return jsonObject;*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Map<String,Object>转Map<String,String>
     * @param map
     * @return
     */
    public Map<String,String> oToString(Map<String,Object> map){
        HashMap<String,String> stringHashMap=new HashMap<>();
        for (String s : map.keySet()) {
            stringHashMap.put(s,map.get(s).toString());
        }
        return stringHashMap;
    }
}
