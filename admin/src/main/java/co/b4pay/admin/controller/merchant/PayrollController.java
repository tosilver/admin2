package co.b4pay.admin.controller.merchant;


import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.controller.partner.PartnerController;
import co.b4pay.admin.entity.Payroll;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.PayrollService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("orderPayroll")
public class PayrollController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    private PayrollService payrollService;
    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("orderPayroll:list")
    public String list(Model model, @PageAttribute Page<Payroll> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();

        if (roleIds.contains("1")) {   //如果拥有超级管理员权限
            model.addAttribute("page", payrollService.findPage(page));
            model.addAttribute("merchantList", merchantService.findList());
        } else if (StringUtil.isNoneBlank(merchantIds)) {    //如果不是超级管理员则只查询个人交易记录信息
            Params params = page.getParams();
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            } else {
                params.put("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            }

            page.setParams(params);
            model.addAttribute("page", payrollService.findPage(page));
        }
        model.addAttribute("sumAmount", payrollService.sumAmount(page));
        return "orderPayroll/payrollList";

    }

    @RequestMapping(value = "form")
    @RequiresPermissions("orderPayroll:form")
    public String form(Model model, String id) {
        model.addAttribute("payroll", payrollService.get(id));
        return "orderPayroll/payrollForm";
    }
}
