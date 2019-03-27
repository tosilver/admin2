package co.b4pay.admin.controller.misc;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.entity.IpWhitelist;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.service.IpWhitelistService;
import co.b4pay.admin.service.MerchantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IP白名单 Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("ipWhiteList")
public class IpWhiteListController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(IpWhiteListController.class);

    @Autowired
    private IpWhitelistService ipWhitelistService;

    @Autowired
    private MerchantService merchantService;

    @RequestMapping("list")
    @RequiresPermissions("ipWhiteList:list")
    public String list(Model model, Page<IpWhitelist> page) {
        model.addAttribute("page", ipWhitelistService.findPage(page));
        return "ipWhiteList/ipWhiteList";
    }

    @RequestMapping(value = "form", method = RequestMethod.GET)
    @RequiresPermissions("ipWhiteList:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("ipWhiteList", ipWhitelistService.get(id));
        }
        model.addAttribute("merchantList", merchantService.findList());
        return "ipWhiteList/ipWhiteListForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions("ipWhiteList:save")
    public String save(IpWhitelist ipWhitelist) {
        if (StringUtil.isBlank(ipWhitelist.getId())) {
            ipWhitelistService.save(ipWhitelist);
        }
        return "redirect:/ipWhiteList/list";
    }
}
