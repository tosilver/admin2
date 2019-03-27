package co.b4pay.admin.controller.partner;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.Partner;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.service.PartnerService;
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
 * 合作商 Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("partner")
public class PartnerController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    private PartnerService partnerService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("partner:list")
    public String list(Model model, @PageAttribute Page<Partner> page) {
        model.addAttribute("page", partnerService.findPage(page));
        return "partner/partnerList";
    }

    @RequestMapping(value = "form", method = RequestMethod.GET)
    @RequiresPermissions("partner:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("partner", partnerService.get(id));
        }
        return "partner/partnerForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions("partner:save")
    public String save(RedirectAttributes redirectAttributes, Partner partner) {
        int count;
        if (StringUtil.isBlank(partner.getId())) {
            count = partnerService.save(partner);
        } else {
            count = partnerService.update(partner);
        }
        if (count > 0) {
            redirectAttributes.addAttribute("msg", "操作成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作失败");
        }
        return "redirect:/partner/list";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @RequiresPermissions("partner:delete")
    public String delete(RedirectAttributes redirectAttributes, String id) {
        int count = partnerService.delete(id);
        if (count > 0) {
            redirectAttributes.addAttribute("msg", "操作成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作失败");
        }
        return "redirect:/partner/list";
    }
}
