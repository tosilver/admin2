package co.b4pay.admin.controller.partner;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.Order;
import co.b4pay.admin.entity.TUser;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.service.TUserService;
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
@RequestMapping("tuser")
public class TUserController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TUserController.class);

    @Autowired
    private TUserService tUserService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @RequiresPermissions("tuser:list")
    public String list(Model model, @PageAttribute Page<TUser> page) {
        Page<TUser> userPage = tUserService.findPage(page);
        List<TUser> consumeList = userPage.getList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (TUser user : consumeList) {
            Map<String, Object> map = new HashMap<>();
            map.put("tuser", user);
            mapList.add(map);
        }
        model.addAttribute("page", userPage);

        return "tuser/tuserList";
    }

    @RequestMapping("form")
    @RequiresPermissions("transin:form")
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("transin", tUserService.get(id));
        }
        return "tuser/tuserForm";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = "transin:save")
    public String save(RedirectAttributes redirectAttributes, TUser tUser) {
        int count = 0;
        tUser.setStatus(0);
        if (StringUtil.isNoneBlank(tUser.getAccount())) {
            count = tUserService.update(tUser);
        } else {
            count = tUserService.save(tUser);
        }
        if (count > 0) {
            redirectAttributes.addAttribute("msg", "操作成功");
        } else {
            redirectAttributes.addFlashAttribute("msg", "操作失败");
        }
        return "redirect:/tuser/list";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(RedirectAttributes redirectAttributes, String id) {
        if (tUserService.delete(id) > 0) {
            addMessage(redirectAttributes, "收款个码禁用成功");
        } else {
            addMessage(redirectAttributes, "收款个码禁用失败");
        }
        return "redirect:/tuser/list";
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
    public String updateStatus(RedirectAttributes redirectAttributes, String id, int status) {
        if (tUserService.updateStatus(id, status) > 0) {
            addMessage(redirectAttributes, "更改状态成功");
        } else {
            addMessage(redirectAttributes, "状态更改失败");
        }
        return "redirect:/tuser/list";
    }
}
