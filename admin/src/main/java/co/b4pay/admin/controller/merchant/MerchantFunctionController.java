package co.b4pay.admin.controller.merchant;

import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.MerchantFunction;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.ChannelService;
import co.b4pay.admin.service.MerchantFunctionService;
import co.b4pay.admin.service.MerchantService;
import co.b4pay.admin.service.RouterService;
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
 * 商户接口 Controller
 * Created by john on 2018/5/3.
 */
@Controller
@RequestMapping("merchantFunction")
public class MerchantFunctionController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(MerchantFunctionController.class);

    @Autowired
    private MerchantFunctionService merchantFunctionService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RouterService routerService;

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = "list")
    @RequiresPermissions("merchantFunction:list")
    public String list(Model model, @PageAttribute Page<MerchantFunction> page) {
        String merchantIds = LoginHelper.getMerchantIds();
        String roleIds = LoginHelper.getRoleIds();
        if (roleIds.contains("1")) {   //如果拥有超级管理员权限
            model.addAttribute("page", merchantFunctionService.findPage(page));
            model.addAttribute("channelList", channelService.findList());
        } else if (StringUtil.isNoneBlank(merchantIds)) {
            Params params = page.getParams();
            if (null == params) {
                params = Params.create("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            } else {
                params.put("merchantIds", merchantIds.substring(0, merchantIds.length() - 1));
            }
            page.setParams(params);
            model.addAttribute("page", merchantFunctionService.findPage(page));
        }
        model.addAttribute("merchantList", merchantService.findList());
        model.addAttribute("routerList", routerService.findList());
        return "merchantFunction/merchantFunctionList";
    }

    @RequestMapping(value = "form", method = RequestMethod.GET)
    @RequiresPermissions("merchantFunction:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("merchantFunction", merchantFunctionService.get(id));
        }
        model.addAttribute("merchantList", merchantService.findList());
        model.addAttribute("routerList", routerService.findList());
        model.addAttribute("channelList", channelService.findList());
        return "merchantFunction/merchantFunctionForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions("merchantFunction:save")
    public String save(RedirectAttributes redirectAttributes, MerchantFunction merchantFunction) {
        if (StringUtil.isBlank(merchantFunction.getId())) {
            try {
                merchantFunctionService.save(merchantFunction);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                addErrorMessage(redirectAttributes, "商户接口已经存在，请勿重复添加");
                return "redirect:/merchantFunction/list";
            }
        } else {
            merchantFunctionService.update(merchantFunction);
        }
        addMessage(redirectAttributes, "操作成功");
        return "redirect:/merchantFunction/list";
    }
}
