package co.b4pay.admin.controller.system;

import co.b4pay.admin.common.system.service.OrganizationService;
import co.b4pay.admin.common.constants.WebConstants;
import co.b4pay.admin.common.helper.LoginHelper;
import co.b4pay.admin.common.system.entity.Admin;
import co.b4pay.admin.common.system.service.AdminService;
import co.b4pay.admin.common.system.service.RoleService;
import co.b4pay.admin.common.web.BaseController;
import co.b4pay.admin.common.biz.exception.BizException;
import co.b4pay.admin.controller.Utils.Utils;
import co.b4pay.admin.entity.AdminChannel;
import co.b4pay.admin.entity.Channel;
import co.b4pay.admin.entity.Merchant;
import co.b4pay.admin.service.ChannelService;
import co.b4pay.admin.service.ConsumeService;
import co.b4pay.admin.service.MerchantService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService adminService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ConsumeService consumeService;

    @RequiresPermissions("admin:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("adminId", LoginHelper.getId());
        Admin admin = adminService.get(LoginHelper.getId());
        //不是超级管理员进来
        if (null != admin && !(admin.getRoleIds().contains("1"))) {
            List<String> merchantIdList = admin.getMerchantIds();
            //根据商户id查找管理员账号(终端客户管理账号)
            List<Admin> adminList = new ArrayList<Admin>();
            adminList.add(admin);
            for (int i = 0; i < merchantIdList.size(); i++) {
                Admin tempAdmin = adminService.findByMerchantId(merchantIdList.get(i) + ",");
                if (null != tempAdmin) {
                    adminList.add(tempAdmin);
                }
            }
            model.addAttribute("adminList", adminList);
            return "system/adminList";
        }

        model.addAttribute("adminList", adminService.findAll());
        return "system/adminList";
    }

    @RequiresPermissions("admin:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        String ifSuperAdmin = "yes";
        String roleIds = LoginHelper.getRoleIds();
        Admin admin = adminService.get(LoginHelper.getId());
        List<Channel> channelList = channelService.selectName();
        model.addAttribute("channelList", channelList);
        //不是超级管理员进来
        if (null != admin && !(admin.getRoleIds().contains("1"))) {
            List<String> merchantIdList = admin.getMerchantIds();
            List<Merchant> merchantList = new ArrayList<Merchant>();
            for (int i = 0; i < merchantIdList.size(); i++) {
                merchantList.add(merchantService.get(merchantIdList.get(i)));
            }
            ifSuperAdmin = "no";
            model.addAttribute("merchantList", merchantList);
        } else {
            setCommonData(model);
        }
        model.addAttribute("ifSuperAdmin", ifSuperAdmin);
        model.addAttribute("user", new Admin());
        model.addAttribute("op", "新增");
        return "system/adminForm";
    }

    @RequiresPermissions("admin:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Admin admin, RedirectAttributes redirectAttributes) {
        Admin loginAdmin = adminService.get(LoginHelper.getId());
        //不是超级管理员进来
        if (null != loginAdmin && !(loginAdmin.getRoleIds().contains("1"))) {
            admin.setOrganizationId(8L);
            List<String> roleList = new ArrayList<>();
            roleList.add("4");
            admin.setRoleIds(roleList);
        }
        System.out.println(admin.getChannelIds());
        String cids = "";
        if (admin.getChannelIds() != null && admin.getChannelIds().trim() != "") {
            cids = admin.getChannelIds();
        }
        adminService.createUser(admin);
        channelService.saveAdminChannel(new AdminChannel(Utils.getBigDecimal(adminService.getByUsername(admin.getUsername()).getId()), cids));
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/admin";
    }

    @RequiresPermissions("admin:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        String ifSuperAdmin = "yes";
        String roleIds = LoginHelper.getRoleIds();
        Admin admin = adminService.get(LoginHelper.getId());
        List<Channel> channelList = channelService.selectName();
        String a = consumeService.findAdminChannel(id);
        model.addAttribute("chIds", consumeService.findAdminChannel(id));

        List<Channel> lc = channelService.findByAdmin(a);
        if (lc.size() < 1) {
            model.addAttribute("channelList", channelList);
        } else {
            model.addAttribute("channelLists", lc);
            List<Channel> lcs = new ArrayList<>();
            for (int j = 0; j < lc.size(); j++) {
                for (int i = 0; i < channelList.size(); i++) {
                    if (channelList.get(i).getId().equals(lc.get(j).getId())) {
                        channelList.remove(i);
                    }
                }
            }
//            for (int i = 0;i<channelList.size())
//            lc.get(lc.size()-1).getId();
            model.addAttribute("channelList", channelList);
        }

        //不是超级管理员进来
        if (null != admin && !(admin.getRoleIds().contains("1"))) {
            List<String> merchantIdList = admin.getMerchantIds();
            List<Merchant> merchantList = new ArrayList<Merchant>();
            for (int i = 0; i < merchantIdList.size(); i++) {
                merchantList.add(merchantService.get(merchantIdList.get(i)));
            }
            ifSuperAdmin = "no";
            model.addAttribute("merchantList", merchantList);
        } else {
            setCommonData(model);
        }
        model.addAttribute("ifSuperAdmin", ifSuperAdmin);
        model.addAttribute("user", adminService.findOne(id));
        model.addAttribute("op", "修改");
        return "system/adminForm";
    }

    @RequiresPermissions("admin:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Admin admin, RedirectAttributes redirectAttributes, Model model) {
        //adminService.updateUser(admin);
//        channelService.saveAdminChannel(new AdminChannel(Utils.getBigDecimal(admin.getId()),admin.getChannelIds().toString()));
//        String cids = "";
//        if(admin.getChannelIds()!= null && admin.getChannelIds().trim() != ""){
//            cids = admin.getChannelIds();
//        }

        channelService.saveAdminChannel(new AdminChannel(Utils.getBigDecimal(admin.getId()), admin.getChannelIds()));
        adminService.update(admin);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/admin";
    }

    /*@RequiresPermissions("admin:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") String id, Model model) {
        setCommonData(model);
        model.addAttribute("user", adminService.findOne(id));
        model.addAttribute("op", "删除");
        return "system/adminForm";
    }*/

    @RequiresPermissions("admin:delete")
    //@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        adminService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "操作成功");
        return "redirect:/admin";
    }

    @RequiresPermissions("admin:delete")
    //@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    @RequestMapping(value = "/{id}/startUsing", method = RequestMethod.GET)
    public String startUsing(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        adminService.startUsing(id);
        redirectAttributes.addFlashAttribute("msg", "操作成功");
        return "redirect:/admin";
    }

    @RequiresPermissions("admin:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", adminService.findOne(id));
        model.addAttribute("op", "修改密码");
        return "system/adminPassword";
    }

    // 非系统管理员个人修改自己的密码
    /*@RequiresPermissions("password:update")
    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePasswrod (Model model) {
        model.addAttribute("user", adminService.findOne(LoginHelper.getId()));
        model.addAttribute("op", "修改密码");
        return "system/adminPassword";
    }*/
    // 非系统管理员个人修改自己的密码
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePasswrod(RedirectAttributes redirectAttributes, String newPassword) {
        if (newPassword.trim().equals("") || newPassword.trim() == null || newPassword.trim().length() < 6) {
            redirectAttributes.addFlashAttribute("msg", "修改失败，请重试");
            return "redirect:/main";
        }
        adminService.changePassword(LoginHelper.getId(), newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功,请重新登录");
        return "redirect:/logout";
    }

    @RequiresPermissions("admin:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") String id, String newPassword, RedirectAttributes redirectAttributes) {
        try {
            adminService.changePassword(id, newPassword);
            redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        } catch (BizException ex) {
            addErrorMessage(redirectAttributes, ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            addErrorMessage(redirectAttributes, WebConstants.ERROR_MSG_50X);
        }
        return "redirect:/admin";
    }

    private void setCommonData(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("merchantList", merchantService.findList());
    }
}