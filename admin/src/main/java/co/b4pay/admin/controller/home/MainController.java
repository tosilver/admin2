package co.b4pay.admin.controller.home;

import co.b4pay.admin.common.system.service.ResourceService;
import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.system.entity.Resource;
import co.b4pay.admin.common.system.service.AdminService;
import co.b4pay.admin.common.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

/**
 * 首页控制器
 *
 * @author cc
 */
@Controller
@RequestMapping({"/", "/main"})
public class MainController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        //根据用户名查看其权限
        Set<String> permissions = adminService.findPermissions(LoginHelper.getUsername());
        // logger.info("permissions:::" + Arrays.deepToString(permissions.toArray()));
        //根据权限返回其能访问到的菜单目录
        List<Resource> menus = resourceService.findMenus(permissions);
        // logger.info("menus:::" + Arrays.deepToString(menus.toArray()));
        model.addAttribute("menus", menus);
        return "index";
    }
}
