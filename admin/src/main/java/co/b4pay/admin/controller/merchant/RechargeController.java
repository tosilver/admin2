package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.Recharge;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.RechargeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 充值Controller
 * Created by john on 2018/4/26.
 */
@Controller
@RequestMapping("recharge")
public class RechargeController extends BaseController {
    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(RechargeController.class);

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "list")
    @RequiresPermissions("recharge:list")
    public String list(Model model, @PageAttribute Page<Recharge> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();
        if (roleIds.contains("1")) {   //如果拥有超级管理员权限
            model.addAttribute("page", rechargeService.findPage(page));
        } else if (StringUtil.isNoneBlank(merchantIds)) {    //如果不是超级管理员则只查询个人交易记录信息
            Params params = page.getParams();
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            } else {
                params.put("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            }
            page.setParams(params);
            model.addAttribute("page", rechargeService.findPage(page));
        }
        model.addAttribute("merchantList", merchantService.findList());
        return "merchant/rechargeList";
    }

    @RequestMapping(value = "form", method = RequestMethod.GET)
    @RequiresPermissions("recharge:form")
    public String form(Model model, String id) {
        model.addAttribute("recharge", rechargeService.get(id));
        model.addAttribute("merchantList", merchantService.findList());
        return "merchant/rechargeForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions("recharge:save")
    public String save(Recharge recharge) {
        rechargeService.save(recharge);
        return "redirect:/recharge/list";
    }
}
