package co.b4pay.admin.controller.partner;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.MallAccessControl;
import co.b4pay.admin.entity.MallAddress;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.service.MallAddressControlService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道 Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("malladdressControl")
public class MallAddressControlController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(MallAddressControlController.class);

    @Autowired
    private MallAddressControlService mallAddressControlService;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("malladdressControl:list")
    public String list(Model model, @PageAttribute Page<MallAccessControl> page) {
        Page<MallAccessControl> addressPage = mallAddressControlService.findPage(page);
        model.addAttribute("page", addressPage);
        return "channel/mallAddressControlList";
    }

    @RequestMapping("form")
    @RequiresPermissions("malladdress:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            //model.addAttribute("malladdress", mallAddressService.get(id));
        }
        return "channel/malladdressForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = "malladdress:save")
    public String save(RedirectAttributes redirectAttributes, String name, String address) {


        return "redirect:/malladdress/list";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(RedirectAttributes redirectAttributes, String id) {

        return "redirect:/malladdress/list";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status) {

        return "redirect:/malladdress/list";
    }
}
