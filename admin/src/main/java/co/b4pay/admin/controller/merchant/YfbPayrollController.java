package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;

import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.entity.DetailDataCard;

import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.entity.MerchantRate;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.YfbPayrollService;
import org.apache.commons.lang3.StringUtils;
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

@Controller
@RequestMapping("orderYfbPayroll")
public class YfbPayrollController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(YfbPayrollController.class);

    @Autowired
    YfbPayrollService yfbPayrollService;
    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("orderYfbPayroll:list")
    public String list(Model model, @PageAttribute Page<DetailDataCard> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();

        if (roleIds.contains("1")) {   //如果拥有超级管理员权限
            model.addAttribute("page", yfbPayrollService.findPage(page));
            model.addAttribute("merchantList", merchantService.findList());
        } else if (StringUtil.isNoneBlank(merchantIds)) {    //如果不是超级管理员则只查询个人交易记录信息
            Params params = page.getParams();
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            } else {
                params.put("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            }

            page.setParams(params);
            model.addAttribute("page", yfbPayrollService.findPage(page));
        }
        model.addAttribute("sumAmount", Utils.getBigDecimal(yfbPayrollService.sumAmount(page)));
        model.addAttribute("sumCost", Utils.getBigDecimal(yfbPayrollService.sumCost(page)));
        model.addAttribute("sumTotalAmount", Utils.getBigDecimal(yfbPayrollService.sumAmountAndCost(page)));
        return "orderYfbPayroll/yfbPayrollList";

    }

    @RequestMapping(value = "form")
    @RequiresPermissions("orderYfbPayroll:form")
    public String form(Model model, String id) {
        model.addAttribute("merchantList", merchantService.findList());
        model.addAttribute("yfbPayroll", yfbPayrollService.get(id));
        return "orderYfbPayroll/yfbPayrollForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(RedirectAttributes redirectAttributes, DetailDataCard detailDataCard) {
        if (StringUtil.isNoneBlank(detailDataCard.getId())) {
            yfbPayrollService.update(detailDataCard);
        } else {
            if (StringUtils.isNotBlank(detailDataCard.getOrderName())) {
                detailDataCard.setSerialNo(detailDataCard.getOrderName());
                detailDataCard.setBatchNo(detailDataCard.getOrderName());
            }
            if (detailDataCard.getMerchantId() != null) {
                Merchant merchant = merchantService.get(detailDataCard.getMerchantId().toString());
                if (merchant != null) {
                    detailDataCard.setCompany(merchant.getCompany());
                }
            }
            detailDataCard.setStatus(2);
            if (detailDataCard.getOrderName() != null &&
                    detailDataCard.getMerchantId() > 0 &&
                    detailDataCard.getAmount() != null) {
                yfbPayrollService.save(detailDataCard);
            }
        }
        return "redirect:/orderYfbPayroll/list";
    }

}
