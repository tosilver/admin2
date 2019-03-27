package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.Settlement;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.entity.enums.AccountType;
import co.b4pay.admin.entity.enums.SettleType;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.SettlementService;
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
 * Created by john on 2018/6/7.
 */
@Controller
@RequestMapping("settlement")
public class SettlementController extends BaseController {
    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(SettlementController.class);

    @Autowired
    private SettlementService settlementService;
    @Autowired
    private MerchantService merchantService;

    @RequiresPermissions("settlement:list")
    @RequestMapping("list")
    public String list(Model model, @PageAttribute Page<Settlement> page) {
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
        model.addAttribute("page", settlementService.findPage(page));
        model.addAttribute("merchantList", merchantService.findList());
        model.addAttribute("accountTypes", AccountType.values());
        model.addAttribute("settleTypes", SettleType.values());
        return "settlement/settlementList";
    }

    @RequiresPermissions("settlement:form")
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("settlement", settlementService.get(id));
        }
        model.addAttribute("merchantList", merchantService.findList());
        model.addAttribute("accountTypes", AccountType.values());
        model.addAttribute("settleTypes", SettleType.values());
        return "settlement/settlementForm";
    }

    @RequiresPermissions("settlement:save")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(RedirectAttributes redirectAttributes, Settlement settlement) {
        if (StringUtil.isNoneBlank(settlement.getId())) {
            settlementService.update(settlement);
        } else {
            int isExists = settlementService.findByMerchantId(null == settlement.getMerchant() ? "" : settlement.getMerchant().getId());
            if (isExists > 0) {
                addErrorMessage(redirectAttributes, "商户结算信息已存在，请勿重复添加");
                return "redirect:/settlement/list";
            }
            settlementService.save(settlement);
        }
        addMessage(redirectAttributes, "操作成功");
        return "redirect:/settlement/list";
    }
}
