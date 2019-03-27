package co.b4pay.admin.controller.system;

import co.b4pay.admin.common.constants.Constants;
import co.b4pay.admin.common.system.service.ResourceService;
import co.b4pay.admin.common.constants.WebConstants;
import co.b4pay.admin.common.system.entity.Resource;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.common.util.StringUtil;
import co.b4pay.admin.entity.base.AjaxResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 资源控制器
 *
 * @author cc
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @RequiresPermissions("resource:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
//        if (StringUtils.isNotBlank(resource.getId())) {
//            resource = resourceService.get(resource.getId());
//        }
//        model.addAttribute("resource", resource);

        List<Resource> resourceList = resourceService.findAll();
        model.addAttribute("resourceList", resourceList);
        return "system/resourceList";
    }

    @RequiresPermissions("resource:create")
    @RequestMapping(value = "/appendChild", method = RequestMethod.GET)
    public String appendChild(String id, Model model) {
        Resource parent = resourceService.get(id);
        model.addAttribute("parent", parent);

        Resource child = new Resource();
        child.setParentId(id);
        child.setParentIds(parent.makeSelfAsParentIds());

        List<Resource> childrens = resourceService.findByParentId(id);// 子节点
        if (CollectionUtils.isNotEmpty(childrens)) {
            Resource lastChildren = childrens.get(childrens.size() - 1);
            switch (StringUtil.split(lastChildren.getParentIds(), '/').length) {
                case 2:// 一级菜单
                    child.setSort(lastChildren.getSort() + 100);
                    break;
                case 3:
                    child.setSort(lastChildren.getSort() + 10);
                    break;
                case 4:
                    child.setSort(lastChildren.getSort() + 1);
                    break;
                default:
                    break;
            }
        } else {
            child.setSort(parent.getSort() + 1);
        }

        model.addAttribute("resource", child);
        model.addAttribute("op", "新增子节点");
        model.addAttribute("resourceTypes", Resource.ResourceType.values());
        return "system/resourceForm";
    }

    @RequiresPermissions("resource:create")
    @RequestMapping(value = "appendChild", method = RequestMethod.POST)
    public String create(Resource resource, RedirectAttributes redirectAttributes) {
        resourceService.createResource(resource);
        redirectAttributes.addFlashAttribute("msg", "新增子节点成功");
        return "redirect:/resource";
    }

    @RequiresPermissions("resource:update")
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form(String id, Model model) {
        Resource resource = resourceService.get(id);
        model.addAttribute("resource", resource);
        if (null != resource.getParentId()) {
            Resource parent = resourceService.get(resource.getParentId());
            model.addAttribute("parent", parent);
            if (parent != null) {
                List<Resource> rootResourceList = resourceService.findByParentId(parent.getParentId());//一级菜单
                model.addAttribute("rootResourceList", rootResourceList);
            }
        }

        model.addAttribute("op", "修改");
        model.addAttribute("resourceTypes", Resource.ResourceType.values());
        return "system/resourceForm";
    }

    @RequiresPermissions("resource:update")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String update(Resource resource, RedirectAttributes redirectAttributes) {
        String parentId = resource.getParentId();
        String[] split = parentId.split(",");
        resource.setParentId(split[0]);
        resourceService.update(resource);
        addMessage(redirectAttributes, "修改成功");
        return "redirect:/resource";
    }

    @RequiresPermissions("resource:update")
    @RequestMapping(value = "/{id}/move/{action}", method = RequestMethod.POST)
    public String move(RedirectAttributes redirectAttributes, @PathVariable String id, @PathVariable String action) {
        try {
            resourceService.move(id, action);
            redirectAttributes.addFlashAttribute("msg", "修改成功");
        } catch (BizException e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", Constants.ERROR_MSG_BIZ);
            logger.error(e.getMessage(), e);
        }
        return "redirect:/resource";
    }

    @RequiresPermissions("resource:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public AjaxResponse delete(String id) {
        try {
            if ("1".equals(id)) {
                return AjaxResponse.failure("根节点不允许删除");
            }
            List<Resource> childrens = resourceService.findByParentId(id);
            if (CollectionUtils.isNotEmpty(childrens)) {
                return AjaxResponse.failure("该节点下有子节点，不允许删除");
            }
            resourceService.delete(id.toString());
            return AjaxResponse.success("删除成功");
        } catch (BizException e) {
            return AjaxResponse.failure(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResponse.failure(WebConstants.ERROR_MSG_50X);
        }
    }
}
