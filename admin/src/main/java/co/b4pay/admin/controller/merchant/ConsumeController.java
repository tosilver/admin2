package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.core.security.HttpsUtils;
import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.util.myutil.file.FileDownloadUtil;
import co.b4pay.admin.common.util.myutil.file.excel.ExcelTemplateUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
//import com.suning.epps.merchantsignature.SignatureUtil;
import co.b4pay.admin.controller.Utils.Constants;
import co.b4pay.admin.controller.Utils.HmacSHA1Signature;
import co.b4pay.admin.controller.Utils.SignatureUtil;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.entity.*;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.*;

import com.alibaba.fastjson.JSONObject;
import javassist.bytecode.ByteArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SignatureException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 交易记录Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("consume")
public class ConsumeController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ConsumeController.class);

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
    private OrderService orderService;

    private Page<Consume> pac = new Page<>();

    //    @Autowired
    private HmacSHA1Signature signature = new HmacSHA1Signature();

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("consume:list")
    public String list(Model model, @PageAttribute Page<Consume> page, HttpServletRequest request) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();
        String aid = LoginHelper.getId();
        Params p = page.getParams();
        String startDate = null;
        if (p != null) {
            startDate = p.containsKey("startDate") ? p.getString("startDate") : null;
        } else {
            p = new Params();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isBlank(startDate)) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 2);
            startDate = sdf.format(c.getTime());
            p.put("startDate", startDate);
        }
        page.setParams(p);

        if (roleIds.contains("1") || roleIds.contains("2")) {   //如果拥有超级管理员权限
            Params paramsc = page.getParams();
            List<Router> routerList = routerService.findList();
            List<MallAddress> addressList = mallAddressService.findList();
            model.addAttribute("routerList", routerList);
            model.addAttribute("addressList", addressList);
            model.addAttribute("page", consumeService.findPage(page));
            Double successRate = consumeService.getSuccessRate(page);
            pac = page;
            if (paramsc != null) {
                try {
                    String routerId = paramsc.getString("routerId");
                    if (routerId != null && routerId != "") {
                        List<Channel> channelList = channelService.findByPayCost(routerId);
                        model.addAttribute("channelList", channelList);
                    }

                } catch (DtoException e) {
                    model.addAttribute("channelList", channelService.findList());
                }

            } else {
                model.addAttribute("channelList", channelService.findList());
            }
            model.addAttribute("merchantList", merchantService.findList());
            model.addAttribute("successRate", successRate);

        } else if (StringUtil.isNoneBlank(merchantIds)) {    //如果不是超级管理员则只查询个人交易记录信息
            Params params = page.getParams();
            if (params != null) {
                try {
                    String routerId = params.getString("routerId");
                    if (routerId != null && routerId != "") {
                        List<Channel> channelList = channelService.findByPayCost(routerId);
                        List<MerchantRate> byMerchantId = merchantRateService.findByMerchantId(merchantIds);
                        List<Router> routerList = new ArrayList();
                        for (int i = 0; i < byMerchantId.size(); i++) {
                            routerList.add(byMerchantId.get(i).getRouter());
                        }
                        model.addAttribute("routerList", routerList);
                        model.addAttribute("channelList", channelList);
                    }
                } catch (DtoException D) {
                    List<MerchantRate> byMerchantId = merchantRateService.findByMerchantId(merchantIds);
                    List<Router> routerList = new ArrayList();
                    List channelList = new ArrayList();
                    for (int i = 0; i < byMerchantId.size(); i++) {
                        String id = byMerchantId.get(i).getRouter().getId();

                        routerList.add(byMerchantId.get(i).getRouter());

                        List<Channel> byPayCost = channelService.findByPayCost(id);
                        for (int j = 0; j < byPayCost.size(); j++) {

                            channelList.add(byPayCost.get(j));
                        }
                    }
                    model.addAttribute("routerList", routerList);
                    model.addAttribute("channelList", channelService.findList());
                }

            } else {
                List<MerchantRate> byMerchantId = merchantRateService.findByMerchantId(merchantIds);
                List<Router> routerList = new ArrayList();
                List channelList = new ArrayList();

                for (int i = 0; i < byMerchantId.size(); i++) {
                    String id = byMerchantId.get(i).getRouter().getId();
                    routerList.add(byMerchantId.get(i).getRouter());
                    List<Channel> byPayCost = channelService.findByPayCost(id);
                    for (int j = 0; j < byPayCost.size(); j++) {
                        channelList.add(byPayCost.get(j));
                    }
                }
                model.addAttribute("routerList", channelService.selName());
                model.addAttribute("channelList", channelService.findList());
            }
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            } else {
                params.put("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            }
            String cIds = consumeService.findAdminChannel(aid);
            params.put("channelIds", cIds);
            page.setParams(params);
            Page<Consume> pc = consumeService.findPage(page);
            Double successRate = consumeService.getSuccessRate(page);
            if (cIds != null && cIds.trim() != "") {
                model.addAttribute("channelList", channelService.findByAdmin(cIds.trim()));
            } else {
                model.addAttribute("channelList", new ArrayList<Channel>());
            }
            if (merchantIds != null && merchantIds.trim() != "") {
                model.addAttribute("merchantList", merchantService.findByAdminMerchant(merchantIds));
            } else {
                model.addAttribute("merchantList", new ArrayList<Merchant>());
            }
            model.addAttribute("routerList", channelService.selName());
            model.addAttribute("page", pc);
            model.addAttribute("successRate", successRate);
            pac = page;
        }
        Double aDouble = consumeService.sumMoney(page);
        Double accountMoney = consumeService.sumAccountMoney(page) == null ? 0D :
                consumeService.sumAccountMoney(page);
        Double requestMoney = consumeService.sumRequestMoney(page) == null ? 0D :
                consumeService.sumRequestMoney(page);
        Integer accountCount = consumeService.accountCount(page) == null ? 0 :
                consumeService.accountCount(page);
        String accountMoneyResult = "";
        if (aDouble != null) {
            String str = new DecimalFormat("0.00").format(aDouble);
            String accountMoneyStr = new DecimalFormat("0.00").format(accountMoney);
            String requestMoneyStr = new DecimalFormat("0.00").format(requestMoney);
            model.addAttribute("sumMoney", str);
            model.addAttribute("sumAccountMoney", accountMoneyStr);
            model.addAttribute("sumRequestMoney", requestMoneyStr);
            accountMoneyResult = accountMoneyStr;
            model.addAttribute("accountCount", accountCount);
        } else {
            Double d = consumeService.sumMoney(page);
            if (d == null) {
                d = 0D;
            }
            Double dd = consumeService.sumAccountMoney(page);
            if (dd == null) {
                dd = 0D;
            }
            Double ddd = consumeService.sumRequestMoney(page);
            if (ddd == null) {
                ddd = 0D;
            }
            model.addAttribute("sumMoney", Utils.getBigDecimal(d));
            model.addAttribute("sumAccountMoney", Utils.getBigDecimal(dd));
            model.addAttribute("sumRequestMoney", Utils.getBigDecimal(ddd));
            accountMoneyResult = Utils.getBigDecimal(dd).toPlainString();
            model.addAttribute("accountCount", accountCount);
        }

        if (page.getParams() != null &&
                page.getParams().containsKey("merchantId")) {
            Page<DetailDataCard> payrollPage = new Page<>();
            Params params = new Params();
            if (page.getParams().containsKey("endDate")) {
                params.put("endDate", page.getParams().getString("endDate"));
            }
            if (page.getParams().containsKey("startDate")) {
                params.put("startDate", page.getParams().getString("startDate"));
            }
            if (page.getParams().containsKey("merchantId")) {
                params.put("merchantId", page.getParams().getString("merchantId"));
            }
            payrollPage.setParams(params);
            Double yfbAccount = yfbPayrollService.sumAmountAndCost(payrollPage);
            if (yfbAccount == null) {
                yfbAccount = 0D;
            }
            model.addAttribute("balance", merchantService.findByBalance(page.getParams().getString("merchantId")));
        }

        return "merchant/consumeList";
    }

    @RequestMapping(value = "form")
    @RequiresPermissions("consume:form")
    public String form(Model model, String id) {
        model.addAttribute("consume", consumeService.get(id));
        return "merchant/consumeForm";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status, String amount) {

        try {

            if (amount.trim().equals("")) {
                addMessage(redirectAttributes, "数据有误，请重新输入");
                return "redirect:/consume/list";
            }
            consumeService.updateStatus(id, status, getBigDecimal(amount));

            Consume trade = consumeService.get(id);

            JSONObject returnData = new JSONObject();
            String merchantOrderNo = trade.getMerchantOrderNo();
            returnData.put("tradeNo", merchantOrderNo);
            returnData.put("amount", trade.getTotalAmount().toPlainString());
            returnData.put("tradeState", String.valueOf(trade.getTradeState()));
            returnData.put("merchantId", trade.getMerchant().getId());
            returnData.put("payTime", String.valueOf(trade.getUpdateTime().getTime()));
//        returnData.put("tradeId",id);

            Merchant m = merchantService.get(trade.getMerchant().getId());

            MerchantService ms = new MerchantService();
            //签名记得传
            String content = SignatureUtil.getSignatureContent(returnData, true);
            String sign = signature.sign(content, m.getSecretKey(), "UTF-8");
            returnData.put("signature", sign);
            consumeService.updateTrade(id, returnData.toString(), 0);

            JobTrade jobTrade = consumeService.findJobTradeById(trade.getId());

            //发起给客户回调任务
            JSONObject params = new JSONObject();
            params.put("mark", "admin发起的回调");
            new Thread(new NotitfTask(jobTrade, trade)).start();

            addMessage(redirectAttributes, "订单更改成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/consume/list";
    }

    /**
     * 给用户发回调任务
     */
    class NotitfTask implements Runnable {

        private String orderid;

        private JSONObject params;

        private JobTrade jobTrade;

        private Consume consume;

        public NotitfTask(String orderid, JSONObject params) {
            this.orderid = orderid;
            this.params = params;
        }

        public NotitfTask(JobTrade jobTrade, Consume consume) {
            this.consume = consume;
            this.jobTrade = jobTrade;
        }

        @Override
        public void run() {

            for (int i = 0; i < 5; i++) {
                int status = notify(jobTrade, consume);
                logger.info("第" + (i + 1) + "次给" + consume.getMerchantOrderNo() + "发回调" + "status为:" + status);
                System.out.println("回调进来了");
                if (status == 1) {
                    Thread.currentThread().stop();
                }
                try {
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private int notify(JobTrade jobTrade, Consume consume) {
            JSONObject contentJson = JSONObject.parseObject(jobTrade.getContent());
            JSONObject notifyJson = new JSONObject();
            notifyJson.put("trade_status", "TRADE_SUCCESS");
            notifyJson.put("total_amount", consume.getTotalAmount().toString());
            notifyJson.put("out_trade_no", consume.getMerchantOrderNo());
            notifyJson.put("trade_no", consume.getId());
            notifyJson.put("notify_id", jobTrade.getId() == null ? "" : jobTrade.getId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            notifyJson.put("payment_time", jobTrade.getExecTime() == null ? sdf.format(new Date()) : sdf.format(jobTrade.getExecTime()));

            logger.info(String.format("回调方法中:trade_status[%s]", notifyJson.getString("trade_status")));
            logger.info(String.format("回调方法中:total_amount[%s]", notifyJson.getString("total_amount")));
            logger.info(String.format("回调方法中:out_trade_no[%s]", notifyJson.getString("out_trade_no")));
            logger.info(String.format("回调方法中:trade_no[%s]", notifyJson.getString("trade_no")));
            logger.info(String.format("回调方法中:notify_id[%s]", jobTrade.getId() == null ? "" : jobTrade.getId()));
            logger.info(String.format("回调方法中:payment_time[%s]", notifyJson.getString("payment_time")));

            Merchant merchant = consume.getMerchant();
            if (merchant == null) {
                logger.error("Merchant为空！！！");
                throw new RuntimeException("Merchant为空");
            }

            String content = null;
            String sign = null;
            try {
                content = SignatureUtil.getSignatureContent(notifyJson, true);
                sign = signature.sign(content, merchant.getSecretKey(), Constants.CHARSET_UTF8);
            } catch (UnsupportedEncodingException | SignatureException e) {
                e.printStackTrace();
            }

            notifyJson.put("signature", sign);

            int status = 0;
            logger.warn("商户支付结果调度器执行：" + JSONObject.toJSONString(jobTrade));
            logger.warn("给用户发回调的地址：" + jobTrade.getNotifyUrl());
            logger.warn("给用户发回调的内容：" + notifyJson.toJSONString());
            String result = null;
            try {
                result = HttpsUtils.post(jobTrade.getNotifyUrl(), null, notifyJson.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            status = Constants.SUCCESS.equals(result) ? 1 : 0;
            logger.warn("用户发完回调以后-->执行结果：" + (status == 1 ? "成功" : "失败"));
            jobTrade.setStatus(status);
            jobTrade.setExecTime(DateUtil.getTime());
            jobTrade.setUpdateTime(DateUtil.getTime());
            if (status == 1) {
                return 1;
            }
            return 0;
        }

        private int notify(String orderid, JSONObject params) {
            Order dbOrder = orderService.get(orderid);
            if (dbOrder == null) {
                logger.warn("dbOrder:" + orderid + "为null");
                return 0;
            }
            String notifyUrl = dbOrder.getNotifyUrl();

            Map<String, String> rspMap = new HashMap<>();
            rspMap.put("orderid", orderid);
            //如果是amdin这边手动确认，那么payedMoney是空，只能去order_money取金额
            rspMap.put("money", dbOrder.getOrderMoney().toString());
            rspMap.put("mark", params.getString("mark"));
            rspMap.put("account", dbOrder.getAccount());

            //这个是admin通知api订单成功支付，与apk收到款以后通知api一致
            logger.info("给请求方发回调的地址：" + notifyUrl);
            logger.info("给请求方发回调的内容：" + rspMap);
            String result = null;
            try {
                result = HttpsUtils.get(notifyUrl, null, rspMap);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("给api发送回调失败:" + e.getMessage());
            }
            int status = 0;
            if (!org.springframework.util.StringUtils.isEmpty(result)) {
                logger.info("回调之后的响应信息:" + result.trim());
                if ("success".contains(result.trim())) {
                    logger.info("改变状态成功");
                    status = 1;
                }
            }
            logger.warn("用户发完回调以后-->执行结果:" + (status == 1 ? "成功" : "失败"));
            return status;
        }
    }

    //@PageAttribute Page<Consume> page
    @RequestMapping(value = "derived", method = RequestMethod.POST)
    public void derived(HttpServletRequest request,
                        HttpServletResponse response, @PageAttribute Page<Consume> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();
        Params params = Params.create();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramertName = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(paramertName);
            params.add(paramertName, parameterValues[0]);
        }

        if (roleIds.contains("1") || roleIds.contains("2")) {

        } else if (request.getParameter("merchantId").toString().trim().isEmpty()) {
            params.add("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
        }

        String templatePath = this.getClass().getClassLoader().getResource("/ExportInfo.xml").getPath();

        pac.setPageSize(9999999);
        pac.setPageFirst(0);
        List<Object> consumeDTOS = toConsumeDTO(consumeService.findPage(pac).getList());


        try {
            ExcelTemplateUtil.exportExcel(templatePath, "xlsx", consumeDTOS, request, response);
        } catch (Exception e) {
            logger.error("文件导出异常：>" + e.getMessage());
            e.printStackTrace();
        }

    }

    public static List<Object> toConsumeDTO(List<Consume> consumes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> consumeDTOS = new ArrayList<>();
        for (int i = 0; i < consumes.size(); i++) {
            ConsumeDTO consumeDTO = new ConsumeDTO();
            Consume consume = consumes.get(i);
            consumeDTO.setMerchantOrderNo(consume.getMerchantOrderNo());
            try {
                String name = consume.getRouter().getName();
                consumeDTO.setName(name);
            } catch (NullPointerException e) {
                consumeDTO.setName("无");
            }

            consumeDTO.setAccountAmount(consume.getAccountAmount());
            consumeDTO.setTotalAmount(consume.getTotalAmount());
            Date createTime = consume.getCreateTime();
            String time = sdf.format(createTime);
            consumeDTO.setCreateTime(time);
            Integer status = consume.getStatus();
            if (status == 0) {
                consumeDTO.setStatus("调用失败");
            } else {
                consumeDTO.setStatus("调用成功");
            }
            String trade = null;
            switch (consume.getTradeState()) {
                case -2:
                    trade = "交易关闭";
                    break;
                case -1:
                    trade = "支付失败";
                    break;
                case 0:
                    trade = "未支付";
                    break;
                case 1:
                    trade = "支付成功";
                    break;
                case 2:
                    trade = "人工确认支付";
                    break;
            }
            consumeDTO.setTradeState(trade);
            consumeDTOS.add(consumeDTO);
        }

        return consumeDTOS;
    }

    public String sub(String d1, String d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal subtract = b1.subtract(b2);
        return subtract.toPlainString();
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
}
