package co.b4pay.admin.controller.withdraw;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.controller.Utils.HmacSHA1Signature;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.entity.*;
import co.b4pay.admin.entity.base.DtoException;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/****
 * 提现申请接口
 */
@Controller
@RequestMapping("/withdraw")
public class WithdrawController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(WithdrawController.class);

    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RouterService routerService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MerchantRateService merchantRateService;
    @Autowired
    private YfbPayrollService yfbPayrollService;


    private Page<Withdraw> pac = new Page<>();

    @RequiresPermissions("withdraw:list")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model, @PageAttribute Page<Withdraw> page, HttpServletRequest request) {
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
        if (org.apache.commons.lang3.StringUtils.isBlank(startDate)) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 24);
            startDate = sdf.format(c.getTime());
            p.put("startDate", startDate);
        }
        page.setParams(p);

        if (roleIds.contains("1") || roleIds.contains("2")) {   //如果拥有超级管理员权限
            Params paramsc = page.getParams();
            List<Router> routerList = routerService.findList();
            model.addAttribute("routerList", routerList);
            model.addAttribute("page", withdrawService.findPage(page));
            Double successRate = withdrawService.getSuccessRate(page);
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
            String cIds = withdrawService.findAdminChannel(aid);
            params.put("channelIds", cIds);
            page.setParams(params);
            Page<Withdraw> pc = withdrawService.findPage(page);
            Double successRate = withdrawService.getSuccessRate(page);
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
        Double aDouble = withdrawService.sumMoney(page);
        /*Double accountMoney = withdrawService.sumAccountMoney(page) == null ? 0D :
                withdrawService.sumAccountMoney(page);*/
        Integer accountCount = withdrawService.accountCount(page) == null ? 0 :
                withdrawService.accountCount(page);
        String accountMoneyResult = "";
        if (aDouble != null) {
            String str = new DecimalFormat("0.00").format(aDouble);
            //String accountMoneyStr = new DecimalFormat("0.00").format(accountMoney);
            model.addAttribute("sumMoney", str);
           /* model.addAttribute("sumAccountMoney", accountMoneyStr);
            accountMoneyResult = accountMoneyStr;*/
            model.addAttribute("accountCount", accountCount);
        } else {
            Double d = withdrawService.sumMoney(page);
            if (d == null) {
                d = 0D;
            }
            /*Double dd = withdrawService.sumAccountMoney(page);
            if (dd == null) {
                dd = 0D;
            }*/
            model.addAttribute("sumMoney", Utils.getBigDecimal(d));
            /*model.addAttribute("sumAccountMoney", Utils.getBigDecimal(dd));
            accountMoneyResult = Utils.getBigDecimal(dd).toPlainString();*/
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

        return "withdraw/withdrawList";
    }

    @RequiresPermissions("withdraw:form")
    @RequestMapping(value = "form")
    public String form(Model model, String id) {
        model.addAttribute("withdraw", withdrawService.get(id));
        return "withdraw/withdrawForm";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status, String moneys) {

        try {

            withdrawService.updateStatus(id, status);
            Withdraw withdraw = withdrawService.get(id);
            String merchantId = withdraw.getMerchantId();
            System.out.println("商户id为" + merchantId);
            Merchant m = merchantService.get(merchantId);
            if (status == 1) {

                System.out.println("通过审批!");
                //添加提现成本
                BigDecimal withdrawCost = null;
                List<MerchantRate> merchantRates = merchantRateService.findByMerchantId(merchantId);
                for (MerchantRate merchantRate : merchantRates) {
                    Router router = merchantRate.getRouter();
                    String routerId = router.getId();
                    if ("mallPay".equals(routerId)) {
                        withdrawCost = merchantRate.getWithdrawCost();
                    }
                }

                //审批金额
                BigDecimal money = new BigDecimal(moneys);
                BigDecimal addAmount = money.add(withdrawCost);
                //从提现冻结金额减去审批金额
                BigDecimal withdrawFrozen = m.getWithdrawFrozen();
                BigDecimal subtract = withdrawFrozen.subtract(addAmount);
                m.setWithdrawFrozen(subtract);
                merchantService.update(m);
                addMessage(redirectAttributes, "申请审批成功");
            } else {
                System.out.println("申请被驳回!!!");
                addMessage(redirectAttributes, "申请已驳回");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            addErrorMessage(redirectAttributes, "申请审批失败!!!");
        }
        return "redirect:/withdraw/list";
    }

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

    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public String save(RedirectAttributes redirectAttributes, Withdraw withdraw) {
        String merchantIds = LoginHelper.getMerchantIds();
        String substring = merchantIds.substring(0, merchantIds.length() - 1);
        Merchant merchant = merchantService.get(merchantIds);
        String company = merchant.getCompany();
        //申请的金额
        BigDecimal amount = withdraw.getAmount();
        //添加提现成本
        BigDecimal withdrawCost = null;
        List<MerchantRate> merchantRates = merchantRateService.findByMerchantId(substring);
        for (MerchantRate merchantRate : merchantRates) {
            Router router = merchantRate.getRouter();
            String routerId = router.getId();
            if ("mallPay".equals(routerId)) {
                withdrawCost = merchantRate.getWithdrawCost();
            }
        }
        BigDecimal addAmount = amount.add(withdrawCost);
        System.out.println("申请的金额:" + addAmount);
        //商户可提现的余额
        BigDecimal withdrawBalance = merchant.getWithdrawBalance();
        BigDecimal balance = merchant.getBalance();
        System.out.println("商户可提现的余额" + withdrawBalance + "总金额:" + balance);
        //比较申请金额与可以提现的余额
        //int类型，-1表示小于，0是等于，1是大于
        int i = addAmount.compareTo(withdrawBalance);
        int j = addAmount.compareTo(balance);
        if (i < 1 && j < 1) {
            if (StringUtil.isBlank(withdraw.getTradeNo())) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    String tradeNo = String.format("%s%s", DateUtil.dateToStr(calendar.getTime(), DateUtil.YMdhmsS_noSpli), RandomStringUtils.randomNumeric(15));
                    withdraw.setTradeNo(tradeNo);
                    withdraw.setMerchantId(substring);
                    withdraw.setMerchantCompany(company);
                    withdraw.setStatus(0);
                    withdrawService.save(withdraw);
                    //把申请的金额加到提现冻结金额
                    //冻结的金额
                    BigDecimal withdrawFrozen = merchant.getWithdrawFrozen();
                    System.out.println("提现冻结金额:" + withdrawFrozen);
                    //加
                    BigDecimal add = withdrawFrozen.add(addAmount);
                    System.out.println("把申请的金额加到提现冻结金额");
                    //把申请的金额从提现余额中减去
                    BigDecimal subtract = withdrawBalance.subtract(addAmount);
                    BigDecimal subalance = balance.subtract(addAmount);
                    System.out.println("把申请的金额从提现余额/总额中减去");
                    merchant.setWithdrawBalance(subtract);
                    merchant.setWithdrawFrozen(add);
                    merchant.setBalance(subalance);
                    merchantService.update(merchant);
                    System.out.println("更新金额成功");
                    addMessage(redirectAttributes, "申请已提交!");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                addErrorMessage(redirectAttributes, "申请单已经存在，请勿重复添加");
            }
        } else {
            addErrorMessage(redirectAttributes, "余额不足!");
        }
        return "redirect:/withdraw/list";
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
