package co.b4pay.admin.controller.system;

import co.b4pay.admin.common.system.entity.Role;
import co.b4pay.admin.common.system.service.ResourceService;
import co.b4pay.admin.common.system.service.RoleService;
import co.b4pay.admin.common.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

/**
 * 权限控制器
 *
 * @author cc
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @RequiresPermissions("role:view")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(required = false) String id) {
        model.addAttribute("role", roleService.get(id));
        model.addAttribute("roleList", roleService.findAll());
        return "system/roleList";
    }

    @RequiresPermissions("role:show")
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form(Model model, String id) {
        model.addAttribute("role", roleService.get(id));
        model.addAttribute("resourceList", resourceService.findAll());
        return "system/roleForm";
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Role role, RedirectAttributes redirectAttributes) {
        role.setUpdateTime(new Date());
        if (null != role.getId()) {
            roleService.update(role);
        } else {
            roleService.save(role);
        }
        redirectAttributes.addFlashAttribute("msg", "保存成功");
        return "redirect:/role";
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        roleService.deleteRole(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/role";
    }

}
