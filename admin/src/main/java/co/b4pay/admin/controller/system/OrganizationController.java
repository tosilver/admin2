package co.b4pay.admin.controller.system;

import co.b4pay.admin.common.system.entity.Organization;
import co.b4pay.admin.common.system.service.OrganizationService;
import co.b4pay.admin.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 组织机构控制器
 *
 * @author cc
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationService organizationService;

    @ModelAttribute
    public Organization get(@RequestParam(required = false) String id) {
        Organization entity = null;
        if (null != id) {
            entity = organizationService.get(id);
        }
        if (entity == null) {
            entity = new Organization();
        }
        return entity;
    }

    @RequiresPermissions("organization:view")
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "system/organization/index";
    }

    @RequiresPermissions("organization:view")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String showTree(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
        return "system/organization/tree";
    }

    @RequiresPermissions("organization:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.GET)
    public String showAppendChildForm(@PathVariable("parentId") String parentId, Model model) {
        Organization parent = organizationService.findOne(parentId);
        model.addAttribute("parent", parent);
        Organization child = new Organization();
        child.setParentId(parentId);
        child.setParentIds(parent.makeSelfAsParentIds());
        model.addAttribute("child", child);
        model.addAttribute("op", "新增");
        return "system/organization/appendChild";
    }

    @RequiresPermissions("organization:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.POST)
    public String create(Organization organization) {
        organizationService.createOrganization(organization);
        return "redirect:/organization/success";
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{id}/maintain", method = RequestMethod.GET)
    public String showMaintainForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("organization", organizationService.findOne(id));
        return "system/organization/maintain";
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Organization organization, RedirectAttributes redirectAttributes) {
        organizationService.updateOrganization(organization);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/organization/success";
    }

    @RequiresPermissions("organization:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        organizationService.deleteOrganization(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/organization/success";
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{sourceId}/move", method = RequestMethod.GET)
    public String showMoveForm(@PathVariable("sourceId") String sourceId, Model model) {
        Organization source = organizationService.findOne(sourceId);
        model.addAttribute("source", source);
        model.addAttribute("targetList", organizationService.findAllWithExclude(source));
        return "system/organization/move";
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{sourceId}/move", method = RequestMethod.POST)
    public String move(@PathVariable("sourceId") String sourceId, @RequestParam("targetId") String targetId) {
        Organization source = organizationService.findOne(sourceId);
        Organization target = organizationService.findOne(targetId);
        organizationService.move(source, target);
        return "redirect:/organization/success";
    }

    @RequiresPermissions("organization:view")
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "system/organization/success";
    }

}
