package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.system.service.AdminService;
import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.controller.partner.PartnerController;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.MerchantPay;

import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.MerchantPayService;
import co.b4pay.admin.service.MerchantService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("merchantPay")
public class MerchantPayController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    private MerchantPayService merchantPayService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("merchantPay:list")
    public String list(Model model, @PageAttribute Page<MerchantPay> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();

        if (roleIds.contains("1")) {   //如果拥有超级管理员权限
            model.addAttribute("page", merchantPayService.findPage(page));
            model.addAttribute("merchantList", merchantService.findList());
        } else if (StringUtil.isNoneBlank(merchantIds)) {    //如果不是超级管理员则只查询个人交易记录信息
            Params params = page.getParams();
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            } else {
                params.put("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            }

            page.setParams(params);
            model.addAttribute("page", merchantPayService.findPage(page));
        }
        Double aDouble = merchantPayService.sumAmount(page);
        if (aDouble != null) {
            String str = new DecimalFormat("0.00").format(aDouble);
            model.addAttribute("sumMoney", str);
        } else {
            model.addAttribute("sumMoney", merchantPayService.sumAmount(page));
        }

        return "merchantPay/merchantPayList";
    }

    @RequestMapping(value = "form")
    @RequiresPermissions("merchantPay:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("merchantPay", merchantPayService.get(id));
            return "merchantPay/merchantPayForm";
        }
        model.addAttribute("merchantList", merchantService.findList());
        return "merchantPay/merchantPaySave";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions("merchantPay:save")
    public String save(RedirectAttributes redirectAttributes, MerchantPay merchantPay) {
        int count = 0;
        //如果充值记录已经在数据库中存在，就只更新充值记录的内容，不加金额到商户里
        if (StringUtil.isNoneBlank(merchantPay.getId())) {
            count = merchantPayService.update(merchantPay);
        } else {
            //如果充值记录在数据库中不存在，就会更新商户余额,并且添加一条充值记录
            String username = LoginHelper.getUsername();
            StringBuffer sl = new StringBuffer();
            SimpleDateFormat yMd_noSpli = DateUtil.YMd_noSpli;
            long time = System.currentTimeMillis();
            sl.append(yMd_noSpli.format(time));
            sl.append(time);
            String orderId = sl.toString();
            Merchant merchant = merchantService.get(merchantPay.getMerchantId().toString());
            merchantPay.setOrderId(orderId);
            merchantPay.setOperator(username);
            merchantPay.setCompany(merchant.getCompany());
            merchantPay.setStatus(0);
            count = merchantPayService.save(merchantPay);
        }
        if (count > 0) {
            redirectAttributes.addAttribute("msg", "操作成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作失败");
        }
        return "redirect:/merchantPay/list";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status) {
        if (merchantPayService.updateStatus(id, status) > 0) {
            MerchantPay merchantPay = merchantPayService.get(id);
            Merchant merchant = merchantService.get(merchantPay.getMerchantId().toString());
            BigDecimal balance = merchant.getBalance();
            BigDecimal amount = merchantPay.getAmount();
            BigDecimal balances = balance.add(amount);
            merchant.setBalance(balances);
            int update = merchantService.update(merchant);
            if (update > 0) {
                addMessage(redirectAttributes, "确认成功");
            } else {
                merchantPay.setStatus(0);
                merchantPayService.update(merchantPay);
                addMessage(redirectAttributes, "更改失败");
            }
        } else {
            addMessage(redirectAttributes, "更改失败");
        }
        return "redirect:/merchantPay/list";
    }

}
