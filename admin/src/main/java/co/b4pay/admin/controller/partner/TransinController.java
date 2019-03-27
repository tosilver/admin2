package co.b4pay.admin.controller.partner;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.Transin;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.service.TransinService;
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
 * 渠道 Controller
 * Created by john on 2018/4/27.
 */
@Controller
@RequestMapping("transin")
public class TransinController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TransinController.class);

    @Autowired
    private TransinService transinService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("transin:list")
    public String list(Model model, @PageAttribute Page<Transin> page) {

        model.addAttribute("page", transinService.findPage(page));
        return "transin/transinList";
    }

    @RequestMapping("form")
    @RequiresPermissions("transin:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("transin", transinService.get(id));
        }
        return "transin/transinForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = "transin:save")
    public String save(RedirectAttributes redirectAttributes, Transin transin) {
        int count = 0;
        transin.setStatus(1);
        if (StringUtil.isNoneBlank(transin.getId())) {
            count = transinService.update(transin);
        } else {
            count = transinService.save(transin);
        }
        if (count > 0) {
            redirectAttributes.addAttribute("msg", "操作成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作失败");
        }
        return "redirect:/transin/list";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(RedirectAttributes redirectAttributes, String id) {
        if (transinService.delete(id) > 0) {
            addMessage(redirectAttributes, "分账账号禁用成功");
        } else {
            addMessage(redirectAttributes, "分账账号禁用失败");
        }
        return "redirect:/transin/list";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status) {
        if (transinService.updateStatus(id, status) > 0) {
            addMessage(redirectAttributes, "更改状态成功");
        } else {
            addMessage(redirectAttributes, "状态更改失败");
        }
        return "redirect:/transin/list";
    }
}
