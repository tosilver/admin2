package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.MerchantRate;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 商户费率
 * Created by john on 2018/6/7.
 */
@Controller
@RequestMapping("merchantRate")
public class MerchantRateController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(MerchantRateController.class);

    @Autowired
    private MerchantRateService merchantRateService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private RouterService routerService;

    @RequiresPermissions("merchantRate:list")
    @RequestMapping("list")
    public String list(Model model, @PageAttribute Page<MerchantRate> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();
        if (!(roleIds.contains("1")) && StringUtil.isNoneBlank(merchantIds)) {
            Params params = page.getParams();
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.split(","));
            } else {
                params.put("merchantIds", merchantIds.split(","));
            }
            page.setParams(params);
        }
        model.addAttribute("page", merchantRateService.findPage(page));
        model.addAttribute("merchantList", merchantService.findList());
        model.addAttribute("routerList", routerService.findList());
        return "merchantRate/merchantRateList";
    }

    @RequiresPermissions("merchantRate:form")
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("merchantRate", merchantRateService.get(id));
        }
        model.addAttribute("merchantList", merchantService.findList());
        model.addAttribute("routerList", routerService.findList());
        return "merchantRate/merchantRateForm";
    }

    @RequiresPermissions("merchantRate:save")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(RedirectAttributes redirectAttributes, MerchantRate merchantRate) {
        if (StringUtil.isNoneBlank(merchantRate.getId())) {
            merchantRateService.update(merchantRate);
        } else {
            String merchantId = merchantRate.getMerchant().getId();
            String routerId = merchantRate.getRouter().getId();
            int isExists = merchantRateService.ifExists(merchantId, routerId);
            if (isExists > 0) {
                addErrorMessage(redirectAttributes, "商户产品费率信息已存在，请勿重复添加");
                return "redirect:/merchantRate/list";
            }
            Router router = routerService.get(merchantRate.getRouter().getId());
            merchantRateService.save(merchantRate);
        }
        return "redirect:/merchantRate/list";
    }
}
