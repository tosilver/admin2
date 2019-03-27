package co.b4pay.admin.controller.misc;

import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.common.web.PageAttribute;
import co.b4pay.admin.entity.Router;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.*;
import co.b4pay.admin.common.web.BaseController;
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

import java.io.UnsupportedEncodingException;

/**
 * Created by john on 2018/6/13.
 */
@Controller
@RequestMapping("router")
public class RouterController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(RouterController.class);

    @Autowired
    private RouterService routerService;

    @RequiresPermissions("router:list")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, @PageAttribute Page<Router> page) {

        model.addAttribute("page", routerService.findPage(page));
        return "router/routerList";
    }

    @RequiresPermissions("router:form")
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form(Model model, String id) {
        if (StringUtil.isNoneBlank(id)) {
            model.addAttribute("router", routerService.get(id));
        }
        return "router/routerForm";
    }

    @RequiresPermissions("router:save")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(RedirectAttributes redirectAttributes, Router router) {
        if (null != routerService.get(router.getId())) {
            addMessage(redirectAttributes, "该路由名称已经存在，请勿重复添加！");
        } else {
            routerService.save(router);
            addMessage(redirectAttributes, "操作成功");
        }
        return "redirect:/router";
    }
}
