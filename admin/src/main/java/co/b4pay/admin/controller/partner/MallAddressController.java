package co.b4pay.admin.controller.partner;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.MallAddress;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.service.MallAddressService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道 Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("malladdress")
public class MallAddressController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(MallAddressController.class);

    @Autowired
    private MallAddressService mallAddressService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("malladdress:list")
    public String list(Model model, @PageAttribute Page<MallAddress> page) {
        Page<MallAddress> addressPage = mallAddressService.findPage(page);
        model.addAttribute("page", addressPage);

        return "channel/malladdressList";
    }

    @RequestMapping("form")
    @RequiresPermissions("malladdress:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("malladdress", mallAddressService.get(id));
        }
        return "channel/malladdressForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = "malladdress:save")
    public String save(RedirectAttributes redirectAttributes, String name, String address,String mallAdmin,String turnover) {

        MallAddress mallAddress = new MallAddress();
        mallAddress.setMallName(name);
        mallAddress.setAddress(address);
        mallAddress.setMallAdmin(mallAdmin);
        mallAddress.setTurnover(new BigDecimal(turnover));
        mallAddress.setStatus(0);
        int count = mallAddressService.save(mallAddress);
        if (count > 0) {
            redirectAttributes.addAttribute("msg", "操作成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作失败");
        }
        return "redirect:/malladdress/list";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(RedirectAttributes redirectAttributes, String id) {
        if (mallAddressService.delete(id) > 0) {
            addMessage(redirectAttributes, "商城禁用成功");
        } else {
            addMessage(redirectAttributes, "商城禁用失败");
        }
        return "redirect:/malladdress/list";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status) {
        if (mallAddressService.updateStatus(id, status) > 0) {
            addMessage(redirectAttributes, "更改状态成功");
        } else {
            addMessage(redirectAttributes, "状态更改失败");
        }
        return "redirect:/malladdress/list";
    }
}
